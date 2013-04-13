package jbolt.android.stub;

import android.os.Handler;
import android.os.Message;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import jbolt.android.base.AppConfig;
import jbolt.android.base.AppContext;
import jbolt.android.utils.HttpManager;
import jbolt.android.utils.Log;
import jbolt.android.utils.MessageHandler;
import jbolt.android.webservice.dto.ServiceRequest;
import jbolt.android.webservice.dto.ServiceResponse;
import jbolt.android.webservice.ex.ClientAppException;
import jbolt.android.webservice.ex.ClientRuntimeException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Collection;
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

    protected void invoke(
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
            Log.e(this.getClass().getName(), e.getMessage(), e);
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    protected void invokeUpload(
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
            Log.e(this.getClass().getName(), e.getMessage(), e);
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    protected void invoke(
        String serviceClassName, String methodName, Object[] params, Handler handler)
        throws Exception {
        invoke(serviceClassName, methodName, null, params, handler);
    }

    private String[] convertClassToString(Class[] classes) {
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

    class ResponseHandler extends Handler {

        Handler handler;

        ResponseHandler(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void handleMessage(Message msg) {
            Message message = new Message();
            if (msg.obj instanceof String) {
                Gson gson = new Gson();
                ServiceResponse response = gson.fromJson((String) msg.obj, ServiceResponse.class);
                if (StringUtils.isNotEmpty(response.getErrorDesc())) {
                    message.obj = new ClientAppException(response.getErrorDesc());
                    handler.sendMessage(message);
                } else {
                    if (response.getResultType() == null) {
                        message.obj = null;
                        handler.sendMessage(message);
                    } else if (response.getResultGenericType() == null) {
                        try {
                            message.obj =
                                gson.fromJson(response.getResultJson(), Class.forName(response.getResultType()));
                            handler.sendMessage(message);
                        } catch (ClassNotFoundException e) {
                            message.obj = new ClientRuntimeException(e);
                            handler.sendMessage(message);
                        }
                    } else {
                        try {
                            JsonParser parser = new JsonParser();
                            JsonArray dataArray = parser.parse(response.getResultJson()).getAsJsonArray();
                            Collection result = (Collection) Class.forName(response.getResultType()).newInstance();
                            Class itemClass = Class.forName(response.getResultGenericType());
                            for (int i = 0; i < dataArray.size(); i++) {
                                result.add(gson.fromJson(dataArray.get(i), itemClass));
                            }
                            message.obj = result;
                            handler.sendMessage(message);
                        } catch (InstantiationException e) {
                            message.obj = new ClientRuntimeException(e);
                            handler.sendMessage(message);
                        } catch (IllegalAccessException e) {
                            message.obj = new ClientRuntimeException(e);
                            handler.sendMessage(message);
                        } catch (ClassNotFoundException e) {
                            message.obj = new ClientRuntimeException(e);
                            handler.sendMessage(message);
                        }
                    }
                }
            } else if (msg.obj instanceof Exception) {
                message.obj = msg.obj;
                handler.sendMessage(message);
            }
        }
    }
}
