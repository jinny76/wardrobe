package jbolt.weibo.impl;

import android.os.Message;
import com.google.gson.Gson;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.AccountAPI;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.net.RequestListener;
import jbolt.android.base.AppConfig;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.utils.JsonUtilities;
import jbolt.android.utils.MessageHandler;
import jbolt.weibo.interfaces.WeiboManager;

import java.io.IOException;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class WeiboManagerSinaImpl implements WeiboManager {

    private Weibo weibo;
    private Oauth2AccessToken accessToken;
    private Long uid;
    private String userName;
    private BaseHandler afterAuthHandler = null;

    public static final String TAG = WeiboManagerSinaImpl.class.getName();

    private static WeiboManager instance;

    public static WeiboManager getInstance() {
        if (instance == null) {
            instance = new WeiboManagerSinaImpl();
        }

        return instance;
    }

    private WeiboManagerSinaImpl() {
        weibo = Weibo.getInstance(AppConfig.getSysConfig("sina_appkey"), AppConfig.getSysConfig("sina_url"));
    }

    @Override
    public void doAuthen(BaseHandler handler) {
        if (accessToken == null) {
            accessToken = AccessTokenKeeper.readAccessToken(AppContext.context);
            if (accessToken.isSessionValid()) {
                uid = AccessTokenKeeper.readUserId(AppContext.context);
                userName = AccessTokenKeeper.readUserName(AppContext.context);
                handler.handleMessage(null);
            } else {
                afterAuthHandler = handler;
                weibo.authorize(AppContext.context, new AuthDialogListener());
            }
        } else {
            handler.handleMessage(null);
        }
    }

    @Override
    public void afterAuthen() {
        if (afterAuthHandler != null) {
            afterAuthHandler.handleMessage(null);
        }
    }

    @Override
    public void importToken(Object token) {
        accessToken = (Oauth2AccessToken) token;
        AccessTokenKeeper.keepAccessToken(AppContext.context, accessToken);
    }

    @Override
    public void fetchUserInfo() {
        AccountAPI api = new AccountAPI(accessToken);
        api.getUid(
                new RequestListener() {
                    @Override
                    public void onComplete(String uid) {
                        WeiboManagerSinaImpl.this.uid = JsonUtilities.getLongValue(uid);
                        UsersAPI api = new UsersAPI(accessToken);
                        api.show(
                                WeiboManagerSinaImpl.this.uid, new RequestListener() {
                            @Override
                            public void onComplete(String jsonString) {
                                Gson gson = new Gson();
                                SinaUser user = gson.fromJson(jsonString, SinaUser.class);
                                WeiboManagerSinaImpl.this.userName = user.getName();
                                AccessTokenKeeper.saveUserInfo(AppContext.context, WeiboManagerSinaImpl.this.uid, WeiboManagerSinaImpl.this.userName);
                                if (afterAuthHandler != null) {
                                    Message message = new Message();
                                    message.obj = user;
                                    afterAuthHandler.handleMessage(message);
                                }
                            }

                            @Override
                            public void onIOException(IOException e) {
                                System.out.println("e = " + e);
                            }

                            @Override
                            public void onError(WeiboException e) {
                                System.out.println("e = " + e);
                            }
                        });

                    }

                    @Override
                    public void onIOException(IOException e) {
                    }

                    @Override
                    public void onError(WeiboException e) {
                    }
                });
    }

    @Override
    public void postWeibo(String content, String attachement, Long lat, Long lon, final BaseHandler handler) {
        StatusesAPI api = new StatusesAPI(accessToken);
        api.upload(
                content, attachement, lat.toString(), lon.toString(), new RequestListener() {
            @Override
            public void onComplete(String s) {
                Message message = new Message();
                message.obj = s;
                handler.handleMessage(message);
            }

            @Override
            public void onIOException(IOException e) {
                MessageHandler.showWarningMessage(AppContext.context, e.getMessage());
            }

            @Override
            public void onError(WeiboException e) {
                MessageHandler.showWarningMessage(AppContext.context, e.getMessage());
            }
        });

    }
}
