package jbolt.android.wardrobe.service.impl;

import android.os.Handler;
import jbolt.android.stub.BaseStub;

public class PersonManagerDefaultImpl extends BaseStub {

    public static void createWithPics(jbolt.android.wardrobe.models.Person person0, java.io.File[] file1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = jbolt.android.wardrobe.models.Person.class;
        params[0] = person0;
        paramTypes[1] = java.io.File[].class;
        params[1] = file1;
        try {
            invokeUpload("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "createWithPics", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addRelations(java.lang.String string0, java.lang.String string1, java.lang.Integer integer2, Handler handler) {
        Class[] paramTypes = new Class[3];
        Object[] params = new Object[3];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        paramTypes[1] = java.lang.String.class;
        params[1] = string1;
        paramTypes[2] = java.lang.Integer.class;
        params[2] = integer2;
        try {
            invoke("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "addRelations", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadRelations(java.lang.String string0, java.lang.Integer integer1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        paramTypes[1] = java.lang.Integer.class;
        params[1] = integer1;
        try {
            invoke("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "loadRelations", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadUnreadMessages(java.lang.String string0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "loadUnreadMessages", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void changePassword(java.lang.String string0, java.lang.String string1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        paramTypes[1] = java.lang.String.class;
        params[1] = string1;
        try {
            invoke("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "changePassword", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMessage(jbolt.android.wardrobe.models.PersonMessages personmessages0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = jbolt.android.wardrobe.models.PersonMessages.class;
        params[0] = personmessages0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "sendMessage", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void find(jbolt.android.wardrobe.models.Person person0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = person0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "find", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(jbolt.android.wardrobe.models.Person person0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = person0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "save", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(jbolt.android.wardrobe.models.Person person0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = person0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "delete", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void create(jbolt.android.wardrobe.models.Person person0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = person0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "create", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void merge(jbolt.android.wardrobe.models.Person person0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = person0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "merge", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(jbolt.android.wardrobe.models.Person person0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = person0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl", "update", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}