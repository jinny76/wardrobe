package jbolt.weibo.interfaces;

import jbolt.android.base.BaseHandler;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public interface WeiboManager {

    void doAuthen(BaseHandler handler);

    void afterAuthen();

    void importToken(Object token);

    void fetchUserInfo();

    void postWeibo(String content, String attachement, Long lat, Long lon, BaseHandler handler);

}
