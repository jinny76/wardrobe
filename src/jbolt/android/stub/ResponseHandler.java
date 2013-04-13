package jbolt.android.stub;

import android.os.Handler;
import android.os.Message;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import jbolt.android.webservice.dto.ServiceResponse;
import jbolt.android.webservice.ex.ClientAppException;
import jbolt.android.webservice.ex.ClientRuntimeException;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ResponseHandler extends Handler {

    private Handler handler;

    public ResponseHandler(Handler handler) {
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