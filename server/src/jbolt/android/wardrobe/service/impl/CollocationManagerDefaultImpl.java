package jbolt.android.wardrobe.service.impl;

import jbolt.android.wardrobe.service.CollocationManager;
import jbolt.android.wardrobe.service.po.Collocation;
import jbolt.android.wardrobe.service.po.CollocationComments;
import jbolt.core.dao.exception.PersistenceException;
import jbolt.core.numbering.NumberSystemManager;
import jbolt.core.numbering.exception.NumberGenerateException;
import jbolt.core.utilities.ObjectUtilities;
import jbolt.framework.crud.exception.CrudApplicationException;
import jbolt.framework.crud.exception.CrudRuntimeException;
import jbolt.framework.crud.impl.GenericCrudDefaultService;

/**
 * <p>Title: CollocationManagerDefaultImpl</p>
 * <p>Description: CollocationManagerDefaultImpl</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class CollocationManagerDefaultImpl extends GenericCrudDefaultService<Collocation> implements CollocationManager {

    private NumberSystemManager uuidManager;

    public String addComments(String collocationId, CollocationComments comments) throws CrudApplicationException, CrudRuntimeException {
        Collocation collocation = new Collocation();
        collocation.setId(collocationId);
        comments.setCollocation(collocation);
        try {
            String commentsId = (String) uuidManager.generateNumber(null, null, null, true);
            comments.setId(commentsId);
            persistenceManager.insert(comments);
            return commentsId;
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        } catch (NumberGenerateException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        }
    }

    public void setUuidManager(NumberSystemManager uuidManager) {
        this.uuidManager = uuidManager;
    }
}
