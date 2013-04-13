package jbolt.android.stub;

import android.os.Handler;
import com.google.gson.Gson;
import jbolt.android.base.AppConfig;
import jbolt.android.base.AppContext;
import jbolt.android.utils.HttpManager;
import jbolt.android.utils.Log;
import jbolt.android.utils.MessageHandler;
import jbolt.android.webservice.dto.ServiceRequest;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class BaseStub {

    public static final String TAG = BaseStub.class.getName();

    protected static void invoke(
        String serviceClassName, String methodName, Class[] paramTypes,
        Object[] params, Handler handler)
        throws Exception {
        try {
            Gson gson = new Gson();
            String[] paramTypeStrs = convertClassToString(paramTypes);
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put(ServiceRequest.SERVICE_CLASS_NAME, serviceClassName);
            paramsMap.put(ServiceRequest.METHOD_NAME, methodName);
            paramsMap.put(ServiceRequest.PARAM_TYPES, gson.toJson(paramTypeStrs));
            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                paramsMap.put(ServiceRequest.PARAM + i, gson.toJson(param));
            }

            HttpManager
                .getRequestAsync(AppConfig.getSysConfig(AppConfig.STUB_URL), paramsMap, new ResponseHandler(handler));
        } catch (Exception e) {
            Log.e(BaseStub.class.getName(), e.getMessage(), e);
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    protected static void invokeUpload(
        String serviceClassName, String methodName, Class[] paramTypes,
        Object[] params, Handler handler)
        throws Exception {
        try {
            Gson gson = new Gson();
            paramTypes = (Class[]) ArrayUtils.subarray(paramTypes, 0, paramTypes.length - 1);
            String[] paramTypeStrs = convertClassToString(paramTypes);
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put(ServiceRequest.SERVICE_CLASS_NAME, serviceClassName);
            paramsMap.put(ServiceRequest.METHOD_NAME, methodName);
            paramsMap.put(ServiceRequest.PARAM_TYPES, gson.toJson(paramTypeStrs));
            for (int i = 0; i < params.length - 1; i++) {
                Object param = params[i];
                paramsMap.put(ServiceRequest.PARAM + i, gson.toJson(param));
            }

            Map<String, File> fileMap = new HashMap<String, File>();
            File[] files = (File[]) params[params.length - 1];
            if (files != null) {
                for (File file : files) {
                    fileMap.put(file.getName(), file);
                }
            }
            HttpManager.uploadAsync(
                AppConfig.getSysConfig(AppConfig.FILE_STUB_URL), paramsMap, fileMap, new ResponseHandler(handler));
        } catch (Exception e) {
            Log.e(BaseStub.class.getClass().getName(), e.getMessage(), e);
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    protected static void invoke(
        String serviceClassName, String methodName, Object[] params, Handler handler)
        throws Exception {
        invoke(serviceClassName, methodName, null, params, handler);
    }

    private static String[] convertClassToString(Class[] classes) {
        String[] classStrs;

        if (classes != null) {
            classStrs = new String[classes.length];
            for (int i = 0; i < classes.length; i++) {
                Class aClass = classes[i];
                classStrs[i] = aClass.getName();
            }
        } else {
            classStrs = new String[0];
        }

        return classStrs;
    }


}
