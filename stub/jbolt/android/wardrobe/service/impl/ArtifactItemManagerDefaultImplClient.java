package jbolt.android.wardrobe.service.impl;

import android.os.Handler;
import jbolt.android.stub.BaseStub;

public class ArtifactItemManagerDefaultImplClient extends BaseStub {

    public void createWithPics(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, java.io.File[] file1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = jbolt.android.wardrobe.models.ArtifactItem.class;
        params[0] = artifactitem0;
        paramTypes[1] = java.io.File[].class;
        params[1] = file1;
        try {
            invokeUpload("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "createWithPics", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void find(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = artifactitem0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "find", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = artifactitem0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "save", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = artifactitem0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "delete", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = artifactitem0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "create", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void merge(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = artifactitem0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "merge", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = artifactitem0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "update", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}