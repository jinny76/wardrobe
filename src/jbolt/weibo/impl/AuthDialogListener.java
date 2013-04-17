package jbolt.weibo.impl;

import android.os.Bundle;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import jbolt.android.base.AppContext;
import jbolt.android.utils.MessageHandler;
import jbolt.weibo.interfaces.WeiboManager;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class AuthDialogListener implements WeiboAuthListener {

    @Override
    public void onComplete(Bundle values) {
        String token = values.getString("access_token");
        String expires_in = values.getString("expires_in");
        Oauth2AccessToken accessToken = new Oauth2AccessToken(token, expires_in);
        if (accessToken.isSessionValid()) {
            final WeiboManager weiboManager = WeiboManagerSinaImpl.getInstance();
            weiboManager.importToken(accessToken);
            weiboManager.fetchUserInfo();
        }
    }

    @Override
    public void onError(WeiboDialogError e) {
        MessageHandler.showWarningMessage(AppContext.context, e.getMessage());
    }

    @Override
    public void onCancel() {
        MessageHandler.showWarningMessage(AppContext.context, "Authen Cancel");
    }

    @Override
    public void onWeiboException(WeiboException e) {
        MessageHandler.showWarningMessage(AppContext.context, e.getMessage());
    }

}