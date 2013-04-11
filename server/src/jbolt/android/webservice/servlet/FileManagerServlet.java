package jbolt.android.webservice.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jbolt.android.webservice.dto.ServiceRequest;
import jbolt.android.webservice.dto.ServiceResponse;
import jbolt.core.ioc.MKernelIOCFactory;
import jbolt.core.utilities.ClassUtilities;
import jbolt.core.utilities.ObjectUtilities;
import jbolt.core.utilities.StringUtilities;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class FileManagerServlet extends EventDispatcherServlet {

    private final static int MAX_MEM_SIZE = 4 * 1024;
    private final static String REPO_DIR = System.getProperty("user.home") + File.separator + "jbolt" + File.separator + "android";
    public static final String ENCODING = "UTF-8";

    private static Logger logger = Logger.getLogger(FileManagerServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceResponse response = new ServiceResponse();
        resp.setCharacterEncoding(ENCODING);
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(MAX_MEM_SIZE);
            File repo = new File(REPO_DIR);
            repo.mkdirs();
            factory.setRepository(repo);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(MAX_MEM_SIZE * MAX_MEM_SIZE);
            upload.setHeaderEncoding(ENCODING);
            List<FileItem> fileItem = upload.parseRequest(req);
            Iterator<FileItem> it = fileItem.iterator();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
            File tempDir = new File(
                    REPO_DIR + File.separator + sdf.format(new Date()) + File.separator + UUID.randomUUID().toString()
                            + File.separator);
            tempDir.mkdirs();
            ArrayList<File> uploadedFiles = new ArrayList<File>();

            while (it.hasNext()) {
                FileItem fi = it.next();
                String oriFileName = fi.getName();
                if (fi.isFormField()) {
                    String formName = fi.getFieldName();
                    String formContent = fi.getString();
                    paramMap.put(formName, formContent);
                } else {
                    if (!fi.isFormField() && (oriFileName != null)) {
                        try {
                            File uploadedFile = new File(tempDir, oriFileName);
                            fi.write(uploadedFile);
                            uploadedFiles.add(uploadedFile);
                        } catch (Exception es) {
                            logger.error(ObjectUtilities.printExceptionStack(es));
                        }
                    }
                }
            }

            String methodName = paramMap.get(ServiceRequest.METHOD_NAME);
            String beanName = paramMap.get(ServiceRequest.SERVICE_CLASS_NAME);
            /*String userUuid = paramMap.get(ServiceRequest.USER_UUID);
            if (!StringUtils.isEmpty(userUuid)) {
                ZhuiInUser user = new ZhuiInUser();
                user.setUuid(new Long(userUuid));
                DwrSessionThreadLocal.getServletContext().getSession().put(SessionConstant.USER, user);
            }*/

            String paramTypesStr = paramMap.get(ServiceRequest.PARAM_TYPES);
            Object[] params = null;

            if (!StringUtilities.isEmpty(paramTypesStr)) {
                String[] paramTypeStrs = gson.fromJson(paramTypesStr, String[].class);
                if (paramTypeStrs != null && paramTypeStrs.length > 0) {
                    params = new Object[paramTypeStrs.length + 1];
                    for (int i = 0; i < paramTypeStrs.length; i++) {
                        String typeStr = paramTypeStrs[i];
                        Class paramType = Class.forName(typeStr);
                        params[i] = gson.fromJson(paramMap.get(ServiceRequest.PARAM + i), paramType);
                    }
                    params[paramTypeStrs.length] = uploadedFiles.toArray(new File[]{});
                } else {
                    params = new Object[1];
                    params[0] = uploadedFiles.toArray(new File[]{});
                }
            } else {
                params = new Object[1];
                params[0] = uploadedFiles.toArray(new File[]{});
            }

            Object bean = MKernelIOCFactory.getIocContainer().getManagedBean(beanName, true);
            Method callMethod = ClassUtilities.getMethodByArbitraryName(bean.getClass(), methodName);

            resp.setContentType("application/x-json");

            try {
                handleInvoker(bean, params, callMethod, response);
            } catch (ClassNotFoundException e) {
                response.setErrorDesc(e.getMessage());
                logger.error(ObjectUtilities.printExceptionStack(e));
            } catch (InvocationTargetException e) {
                response.setErrorDesc(e.getTargetException().getMessage());
                logger.error(ObjectUtilities.printExceptionStack(e));
            } catch (IllegalAccessException e) {
                response.setErrorDesc(e.getMessage());
                logger.error(ObjectUtilities.printExceptionStack(e));
            }
        } catch (Exception e) {
            response.setErrorDesc(e.getMessage());
            logger.error(ObjectUtilities.printExceptionStack(e));
        } finally {
            resp.getWriter().write(gson.toJson(response));
        }
    }
}