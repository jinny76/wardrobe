package jbolt.android.wardrobe.service.impl;

import java.util.Locale;
import jbolt.core.i18n.I18nManager;
import jbolt.core.ioc.MKernelIOCFactory;

/**
 * <p>Title: WebUtils</p>
 * <p>Description: WebUtils</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class WebUtils {

    public static String getI18nValue(String key) {
        I18nManager i18nManager = MKernelIOCFactory.getIocContainer().getService("i18nManager");
        return i18nManager.getI18nValue(Locale.CHINA, key);
    }
}
