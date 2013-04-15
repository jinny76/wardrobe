package jbolt.android.wardrobe.service.impl;

import jbolt.android.stub.BaseStub;
import android.os.*;

public class ArtifactItemManagerDefaultImpl extends BaseStub {

    public static void createWithPics(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, java.io.File[] file1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = jbolt.android.wardrobe.models.ArtifactItem.class;
        params[0] = artifactitem0;
        paramTypes[1] = java.io.File[].class;
        params[1] = file1;
        try{
            invokeUpload("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "createWithPics", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void findItemsByType(java.lang.String string0, java.lang.String string1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        paramTypes[1] = java.lang.String.class;
        params[1] = string1;
        try{
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "findItemsByType", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void delete(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.ArtifactItem.class;
        params[0] = artifactitem0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "delete", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void delete(java.lang.Object object0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = object0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "delete", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void find(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.ArtifactItem.class;
        params[0] = artifactitem0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "find", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void save(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.ArtifactItem.class;
        params[0] = artifactitem0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "save", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void create(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.ArtifactItem.class;
        params[0] = artifactitem0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "create", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void merge(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.ArtifactItem.class;
        params[0] = artifactitem0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "merge", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void update(jbolt.android.wardrobe.models.ArtifactItem artifactitem0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.ArtifactItem.class;
        params[0] = artifactitem0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl", "update", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}