package jbolt.weibo.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.weibo.sdk.android.Oauth2AccessToken;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class AccessTokenKeeper {

    private static final String PREFERENCES_NAME = "wardrobe_android";

    public static void keepAccessToken(Context context, Oauth2AccessToken token) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token.getToken());
        editor.putLong("expiresTime", token.getExpiresTime());
        editor.commit();
    }

    public static void saveUserInfo(Context context, Long userId, String userName) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("userId", userId);
        editor.putString("userName", userName);
        editor.commit();
    }

    public static void clear(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public static Oauth2AccessToken readAccessToken(Context context) {
        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        token.setToken(pref.getString("token", ""));
        token.setExpiresTime(pref.getLong("expiresTime", 0));
        return token;
    }

    public static String readUserName(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        return pref.getString("userName", "");
    }

    public static Long readUserId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        return pref.getLong("userId", -1L);
    }
}
