package jbolt.android.wardrobe.service.impl;

import android.os.Handler;
import jbolt.android.stub.BaseStub;

public class CollocationManagerDefaultImplClient extends BaseStub {

    public void addComments(java.lang.String string0, jbolt.android.wardrobe.models.CollocationComments collocationcomments1, Handler handler) {
        Class[] paramTypes = new Class[2];
        Object[] params = new Object[2];
        paramTypes[0] = java.lang.String.class;
        params[0] = string0;
        paramTypes[1] = jbolt.android.wardrobe.models.CollocationComments.class;
        params[1] = collocationcomments1;
        try {
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "addComments", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void find(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = collocation0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "find", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = collocation0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "save", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = collocation0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "delete", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = collocation0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "create", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void merge(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = collocation0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "merge", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(jbolt.android.wardrobe.models.Collocation collocation0, Handler handler) {
        Class[] paramTypes = new Class[1];
        Object[] params = new Object[1];
        paramTypes[0] = java.lang.Object.class;
        params[0] = collocation0;
        try {
            invoke("jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl", "update", paramTypes, params, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}