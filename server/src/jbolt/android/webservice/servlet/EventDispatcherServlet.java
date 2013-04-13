package jbolt.android.webservice.servlet;

import com.google.gson.Gson;
import jbolt.android.webservice.Filterable;
import jbolt.android.webservice.dto.ServiceRequest;
import jbolt.android.webservice.dto.ServiceResponse;
import jbolt.core.ioc.MKernelIOCFactory;
import jbolt.core.utilities.ClassUtilities;
import jbolt.core.utilities.ObjectUtilities;
import jbolt.core.utilities.StringUtilities;
import jbolt.framework.crud.GenericCrudService;
import jbolt.framework.crud.impl.GenericCrudDefaultService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Component:
 * Description:
 * Person: feng.xie
 * Date: 18/06/11
 */
public class EventDispatcherServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(EventDispatcherServlet.class);
    protected static Gson gson = new Gson();
    public static final String ENCODING = "UTF-8";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceResponse response = new ServiceResponse();
        resp.setCharacterEncoding(ENCODING);
        req.setCharacterEncoding(ENCODING);
        try {
            String methodName = req.getParameter(ServiceRequest.METHOD_NAME);
            String beanName = req.getParameter(ServiceRequest.SERVICE_CLASS_NAME);
            String paramTypesStr = req.getParameter(ServiceRequest.PARAM_TYPES);
            paramTypesStr = convertToServerPO(paramTypesStr);
            Object[] params = null;
            if (!StringUtilities.isEmpty(paramTypesStr)) {
                String[] paramTypeStrs = gson.fromJson(paramTypesStr, String[].class);
                if (paramTypeStrs != null && paramTypeStrs.length > 0) {
                    params = new Object[paramTypeStrs.length];
                    for (int i = 0; i < paramTypeStrs.length; i++) {
                        String typeStr = paramTypeStrs[i];
                        Class paramType = Class.forName(typeStr);
                        params[i] = gson.fromJson(req.getParameter(ServiceRequest.PARAM + i), paramType);
                    }
                }
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

    @SuppressWarnings("unchecked")
    protected void handleInvoker(Object bean, Object[] params, Method callMethod, ServiceResponse response)
        throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String genericClass = null;
        String genericInfo = StringUtilities.replaceNull(bean.getClass().getGenericSuperclass());
        if (!StringUtils.isEmpty(genericInfo)
            && genericInfo.contains(GenericCrudDefaultService.class.getCanonicalName())
            && genericInfo.contains("<")) {
            genericInfo = StringUtilities.subString(genericInfo, "<", ">");
            genericInfo = StringUtils.replace(genericInfo, "<", "");
            genericInfo = StringUtils.replace(genericInfo, ">", "");
            genericClass = genericInfo.trim();
        }
        Object res = callMethod.invoke(bean, params);
        if (res != null) {
            Class clazz = res.getClass();
            if (clazz.getName().contains("$")) {
                Object _res = ClassUtilities.getCanonicalClazz(clazz).newInstance();
                ObjectUtilities.deepCloneProperties(res, _res);
                res = _res;
            } else {
                boolean isArray = clazz.isArray();
                if (ClassUtilities.isRelationClass(clazz, Collection.class)) {
                    List _res = new ArrayList();
                    boolean enhanced = true;
                    for (Object obj : (Collection) res) {
                        if (obj.getClass().getName().contains("$")) {
                            Object _obj = ClassUtilities.getCanonicalClazz(obj.getClass()).newInstance();
                            ObjectUtilities.deepCloneProperties(obj, _obj);
                            _res.add(_obj);
                        } else {
                            enhanced = false;
                            break;
                        }
                    }
                    if (enhanced) {
                        res = _res;
                    }
                } else if (isArray) {
                    int size = Array.getLength(res);
                    Object newArray = Array.newInstance(res.getClass().getComponentType(), size);
                    for (int j = 0; j < size; j++) {
                        Object _childPropertyValue = Array.get(res, j);
                        if (_childPropertyValue.getClass().getName().contains("$")) {
                            Object _obj =
                                ClassUtilities.getCanonicalClazz(_childPropertyValue.getClass()).newInstance();
                            ObjectUtilities.deepCloneProperties(_childPropertyValue, _obj);
                            Array.set(newArray, j, _obj);
                        }
                    }
                    res = newArray;
                }
            }
            filter(res);
            String objStr = gson.toJson(res);
            response.setResultJson(objStr);
            if (bean instanceof GenericCrudService
                && (
                callMethod.getName().equals("create")
                    || callMethod.getName().equals("find")
                    || callMethod.getName().equals("update")
                    || callMethod.getName().equals("delete")
                    || callMethod.getName().equals("merge"))) {
                Class genericType = ClassUtilities.getClazz(genericClass);
                response.setResultType(genericType.getName());
            } else {
                response.setResultType(res.getClass().getName());
                if (ClassUtilities.isRelationClass(callMethod.getReturnType(), Collection.class)) {
                    String returnGenericInfo = callMethod.getGenericReturnType().toString();
                    returnGenericInfo = StringUtilities.subString(returnGenericInfo, "<", ">");
                    returnGenericInfo = StringUtils.replace(returnGenericInfo, "<", "");
                    returnGenericInfo = StringUtils.replace(returnGenericInfo, ">", "");
                    returnGenericInfo = returnGenericInfo.trim();
                    response.setResultGenericType(returnGenericInfo);
                }
            }
        }
    }

    private void filter(Object res) {
        if (res instanceof Collection) {
            Collection lstResult = (Collection) res;
            for (Object item : lstResult) {
                if (item instanceof Filterable) {
                    ((Filterable) item).filter();
                }
            }
        } else if (res != null && res.getClass().isArray()) {
            Object[] arrResult = (Object[]) res;
            for (Object item : arrResult) {
                if (item instanceof Filterable) {
                    ((Filterable) item).filter();
                }
            }
        } else if (res instanceof Filterable) {
            ((Filterable) res).filter();
        }
    }

    private String convertToServerPO(String paramClassName) {
        if (paramClassName.contains(".models.")) {
            return StringUtils.replace(paramClassName, ".models.", ".service.po.");
        } else {
            return paramClassName;
        }
    }


}
