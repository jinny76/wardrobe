package jbolt.android.listeners;

import android.view.View;
import jbolt.android.base.AppContext;
import jbolt.android.utils.Log;
import jbolt.android.utils.MessageHandler;

/**
 * <p>Title: jbolt.android.listeners</p>
 * <p>Description: jbolt.android.listeners</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: JBolt.com</p>
 *
 * @author Jinni
 */
public abstract class OnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        try {
            onClickAction(view);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    protected abstract void onClickAction(View view);
}
