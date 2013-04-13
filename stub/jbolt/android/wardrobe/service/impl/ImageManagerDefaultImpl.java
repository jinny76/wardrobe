package jbolt.android.wardrobe.service.impl;

import android.os.Handler;
import jbolt.android.stub.BaseStub;

public class ImageManagerDefaultImpl extends BaseStub {

    public static void savePic(java.lang.String string0, java.io.File file1, boolean boolean2, Handler handler) {
        Class[] paramTypes = new Class[3];
        Object[] params = new Object[3];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        paramTypes[1] = java.io.File.class;
        params[1] = file1;
        paramTypes[2] = java.lang.Boolean.class;
        params[2] = boolean2;
        try {
            invoke("jbolt.android.wardrobe.service.impl.ImageManagerDefaultImpl", "savePic", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadPic(java.lang.String string0, boolean boolean1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        paramTypes[1] = java.lang.Boolean.class;
        params[1] = boolean1;
        try {
            invoke("jbolt.android.wardrobe.service.impl.ImageManagerDefaultImpl", "loadPic", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setImgRepoPath(java.lang.String string0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.ImageManagerDefaultImpl", "setImgRepoPath", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}