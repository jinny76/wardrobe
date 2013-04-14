package jbolt.android.base;

import android.os.Handler;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public abstract class ClientHandler extends Handler {

    public abstract void handleMsg(Object obj);
}
