package jbolt.android.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class AppConfig {

    protected final static int DEBUG_SIGNATURE_HASH = -738532184;
    public static Boolean isDebugBuild;

    private static Properties sysConfig;
    private static String TAG = AppContext.class.getName();

    public static final String SYS_CONFIG = "/conf/sysconfig.properties";

    public static final String STUB_URL = "stub_url";
    public static final String FILE_STUB_URL = "file_stub_url";
    public static final String WEB_ROOT = "web_root";

    private static void init() {
        if (sysConfig == null) {
            InputStream _is = AppContext.class.getResourceAsStream(SYS_CONFIG);
            try {
                sysConfig = new Properties();
                sysConfig.load(_is);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    public static String getSysConfig(String configKey) {
        init();
        if (isDebugBuild(AppContext.context)) {
            return sysConfig.getProperty("debug_" + configKey);
        } else {
            return sysConfig.getProperty(configKey);
        }
    }

    public static void setProperty(String configKey, String value) {
        init();
        if (isDebugBuild(AppContext.context)) {
            sysConfig.setProperty("debug_" + configKey, value);
        } else {
            sysConfig.getProperty(configKey, value);
        }
    }


    public static Boolean isDebugBuild(Context context) {
        if (isDebugBuild == null) {
            try {
                isDebugBuild = false;
                Signature[] sigs = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures;
                for (int i = 0; i < sigs.length; i++) {
                    if (sigs[i].hashCode() == DEBUG_SIGNATURE_HASH) {
                        Log.d(TAG, "This is a debug build!");
                        isDebugBuild = true;
                        break;
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, e.toString(), e);
            }
        }
        return isDebugBuild;
    }
}
