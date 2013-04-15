package jbolt.android.wardrobe.service.impl;

import jbolt.android.stub.BaseStub;
import android.os.*;

public class CollocationManagerDefaultImpl extends BaseStub {

    public static void createWithPics(jbolt.android.wardrobe.models.Collocation collocation0, java.io.File[] file1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = jbolt.android.wardrobe.models.Collocation.class;
        params[0] = collocation0;
        paramTypes[1] = java.io.File[].class;
        params[1] = file1;
        try{
            invokeUpload("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "createWithPics", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void modifyWithPics(jbolt.android.wardrobe.models.Collocation collocation0, java.io.File[] file1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = jbolt.android.wardrobe.models.Collocation.class;
        params[0] = collocation0;
        paramTypes[1] = java.io.File[].class;
        params[1] = file1;
        try{
            invokeUpload("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "modifyWithPics", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void addComments(java.lang.String string0, jbolt.android.wardrobe.models.CollocationComments collocationcomments1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        paramTypes[1] = jbolt.android.wardrobe.models.CollocationComments.class;
        params[1] = collocationcomments1;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "addComments", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void loadCollocations(java.lang.String string0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "loadCollocations", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void loadShows(java.lang.Integer integer0, java.lang.String string1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = java.lang.Integer.class;
        params[0] = integer0;
        paramTypes[1] = java.lang.String.class;
        params[1] = string1;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "loadShows", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void reportIllegalCollocation(java.lang.String string0, java.lang.String string1, java.lang.String string2, Handler handler) {
        Class[] paramTypes = new Class[3];
        Object[] params = new Object[3];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        paramTypes[1] = java.lang.String.class;
        params[1] = string1;
        paramTypes[2] = java.lang.String.class;
        params[2] = string2;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "reportIllegalCollocation", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void findById(java.lang.String string0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "findById", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void showCollocation(java.lang.String string0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "showCollocation", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void loadComments(java.lang.String string0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "loadComments", paramTypes, params, handler);
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
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "delete", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void delete(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.Collocation.class;
        params[0] = collocation0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "delete", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void find(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.Collocation.class;
        params[0] = collocation0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "find", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void save(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.Collocation.class;
        params[0] = collocation0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "save", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void create(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.Collocation.class;
        params[0] = collocation0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "create", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void merge(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.Collocation.class;
        params[0] = collocation0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "merge", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void update(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.Collocation.class;
        params[0] = collocation0;
        try{
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "update", paramTypes, params, handler);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}