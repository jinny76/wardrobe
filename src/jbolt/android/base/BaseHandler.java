package jbolt.android.base;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import jbolt.android.utils.MessageHandler;


/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public abstract class BaseHandler extends Handler {

    @Override
    @Deprecated
    public void handleMessage(Message msg) {
        try {
            handleMsg(msg);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    protected abstract void handleMsg(Message msg) throws Exception;
}
