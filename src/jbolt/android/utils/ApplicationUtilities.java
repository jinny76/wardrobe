package jbolt.android.utils;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import jbolt.android.base.AppContext;

import java.util.List;

/**
 * <p>Title: ApplicationUtilities</p>
 * <p>Description: ApplicationUtilities</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ApplicationUtilities {

    /**
     * Call applications with given uid
     *
     * @param uid Uid
     */
    public static void callApplication(String uid) {
        PackageManager packageManager = AppContext.context.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            if (packageInfo.versionName == null) {
                continue;
            }
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (applicationInfo.uid == Integer.valueOf(uid)) {
                Intent intent = packageManager.getLaunchIntentForPackage(applicationInfo.packageName);
                if (intent != null) {
                    AppContext.context.startActivity(intent);
                    break;
                }
            }
        }
    }

}
