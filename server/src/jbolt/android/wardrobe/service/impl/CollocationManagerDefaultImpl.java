package jbolt.android.wardrobe.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import jbolt.android.wardrobe.PersonMessageType;
import jbolt.android.wardrobe.service.CollocationManager;
import jbolt.android.wardrobe.service.ImageManager;
import jbolt.android.wardrobe.service.PersonManager;
import jbolt.android.wardrobe.service.po.Collocation;
import jbolt.android.wardrobe.service.po.CollocationComments;
import jbolt.android.wardrobe.service.po.PersonMessages;
import jbolt.android.webservice.servlet.LocalMethod;
import jbolt.core.dao.exception.DAOException;
import jbolt.core.dao.exception.PersistenceException;
import jbolt.core.numbering.NumberSystemManager;
import jbolt.core.numbering.exception.NumberGenerateException;
import jbolt.core.utilities.ObjectUtilities;
import jbolt.framework.crud.exception.CrudApplicationException;
import jbolt.framework.crud.exception.CrudRuntimeException;
import jbolt.framework.crud.impl.GenericCrudDefaultService;
import jbolt.platform.common.biz.exception.BizAppException;
import jbolt.platform.common.biz.exception.BizRuntimeException;

/**
 * <p>Title: CollocationManagerDefaultImpl</p>
 * <p>Description: CollocationManagerDefaultImpl</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class CollocationManagerDefaultImpl extends GenericCrudDefaultService<Collocation>
        implements CollocationManager {

    private NumberSystemManager uuidManager;
    private PersonManager personManager;
    private ImageManager imageManager;

    public Collocation createWithPics(Collocation collocation, File[] pics) throws CrudApplicationException, CrudRuntimeException {
        collocation.setCreateDate(new Date());
        Collocation _collocation = create(collocation);
        imageManager.savePic(collocation.getId(), pics[0], true);
        imageManager.savePic(collocation.getId(), pics[1], false);
        return _collocation;
    }

    public void modifyWithPics(Collocation collocation, File[] pics) throws CrudApplicationException, CrudRuntimeException {
        collocation.getModifiedFields().add("*");
        update(collocation);
        imageManager.savePic(collocation.getId(), pics[0], true);
        imageManager.savePic(collocation.getId(), pics[1], false);
    }

    @Override
    public void delete(Collocation domain) throws CrudApplicationException, CrudRuntimeException {
        super.delete(domain);
        imageManager.deletePic(domain.getId());
    }

    public String addComments(String collocationId, CollocationComments comments)
            throws CrudApplicationException, CrudRuntimeException {
        Collocation collocation = new Collocation();
        collocation.setId(collocationId);
        comments.setCollocation(collocation);
        try {
            String commentsId = (String) uuidManager.generateNumber(null, null, null, true);
            comments.setId(commentsId);
            persistenceManager.insert(comments);

            Collocation toUpdate = (Collocation) queryManager.find(collocation);
            Long commentsCounter = toUpdate.getCommentsCounter();
            if (commentsCounter == null) {
                commentsCounter = (long) 0;
            }
            commentsCounter += 1;
            toUpdate.setCommentsCounter(commentsCounter);
            toUpdate.getModifiedFields().add("commentsCounter");
            persistenceManager.update(toUpdate);
            PersonMessages personMessages = new PersonMessages();
            personMessages.setType(PersonMessageType.COMMENTS);
            personMessages.setSendFrom(comments.getOwnerId());
            personMessages.setSendTo(collocation.getOwnerId());
            personManager.sendMessage(personMessages);
            return commentsId;
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        } catch (NumberGenerateException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Collocation> loadCollocations(String personId) throws BizAppException, BizRuntimeException {
        Collocation criteria = new Collocation();
        criteria.setOwnerId(personId);
        try {
            return (List<Collocation>) queryManager.findByAnyCriteria(criteria);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    public void reportIllegalCollocation(String collocationId, String msg, String reportBy) throws BizAppException, BizRuntimeException {
        Collocation pk = new Collocation();
        pk.setId(collocationId);
        try {
            Collocation toUpdate = (Collocation) queryManager.find(pk);
            toUpdate.setReportMsg(msg);
            toUpdate.setIllegal(true);
            toUpdate.setReportBy(reportBy);
            persistenceManager.update(toUpdate);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    public void showCollocation(String collocationId) throws BizAppException, BizRuntimeException {
        Collocation pk = new Collocation();
        pk.setId(collocationId);
        try {
            Collocation toUpdate = (Collocation) queryManager.find(pk);
            toUpdate.setShow(true);
            persistenceManager.update(toUpdate);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    public void setImageManager(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

    @LocalMethod
    public void setUuidManager(NumberSystemManager uuidManager) {
        this.uuidManager = uuidManager;
    }
}
