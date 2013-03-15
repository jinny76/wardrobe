package jbolt.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import jbolt.android.base.AppContext;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: jbolt team</p>
 *
 * @author Jinni
 */
public class WIFIManager {

    private static WIFIManager instance = null;

    public static final String SUCCESS = "success";

    private WIFIManager() {
    }

    public static WIFIManager getInstance() {
        if (instance == null) {
            instance = new WIFIManager();
        }
        return instance;
    }

    public boolean isWIFIAvailable() {
        Context context = AppContext.context.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getTypeName().equals("WIFI") && anInfo.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isWIFIConnected() {
        if (isWIFIAvailable()) {
//            try {
//                String result = HttpManager.getRequestSync(AppContext.getSysConfig(AppContext.WEB_ROOT) + "/test.html", null);
//                if (SUCCESS.equals(result)) {
//                    return true;
//                }
//            } catch (DeviceRuntimeException e) {
//                Log.e(this.getClass().getName(), e.getMessage(), e);
//            }
            return true;
        }
        return false;
    }

    public void changeWIFIStatus(boolean enable) {
        WifiManager wifiManager = (WifiManager) AppContext.context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(enable);
    }
}

