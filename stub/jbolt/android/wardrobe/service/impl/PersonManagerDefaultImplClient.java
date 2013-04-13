package jbolt.android.wardrobe.service.impl;

import android.os.Handler;
import jbolt.android.stub.BaseStub;

public class PersonManagerDefaultImplClient extends BaseStub {

    public void addRelations(java.lang.String string0, java.lang.String string1, java.lang.Integer integer2, Handler handler) {
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

    public void loadRelations(java.lang.String string0, java.lang.Integer integer1, Handler handler) {
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

    public void find(jbolt.android.wardrobe.models.Person person0, Handler handler) {
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

    public void save(jbolt.android.wardrobe.models.Person person0, Handler handler) {
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

    public void delete(jbolt.android.wardrobe.models.Person person0, Handler handler) {
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

    public void create(jbolt.android.wardrobe.models.Person person0, Handler handler) {
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

    public void merge(jbolt.android.wardrobe.models.Person person0, Handler handler) {
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

    public void update(jbolt.android.wardrobe.models.Person person0, Handler handler) {
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