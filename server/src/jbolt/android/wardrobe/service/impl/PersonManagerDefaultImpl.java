package jbolt.android.wardrobe.service.impl;

import java.util.Date;
import java.util.List;
import jbolt.android.wardrobe.RelationsType;
import jbolt.android.wardrobe.service.PersonManager;
import jbolt.android.wardrobe.service.po.Person;
import jbolt.android.wardrobe.service.po.PersonRelations;
import jbolt.core.dao.exception.DAOException;
import jbolt.core.dao.exception.PersistenceException;
import jbolt.core.numbering.NumberSystemManager;
import jbolt.core.numbering.exception.NumberGenerateException;
import jbolt.core.utilities.ObjectUtilities;
import jbolt.framework.crud.exception.CrudApplicationException;
import jbolt.framework.crud.exception.CrudRuntimeException;
import jbolt.framework.crud.impl.GenericCrudDefaultService;

/**
 * <p>Title: PersonManagerDefaultImpl</p>
 * <p>Description: PersonManagerDefaultImpl</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class PersonManagerDefaultImpl extends GenericCrudDefaultService<Person> implements PersonManager {

    private NumberSystemManager uuidManager;

    public void addRelations(String masterPersonId, String linkPersonId, Integer type) throws CrudApplicationException, CrudRuntimeException {
        PersonRelations relations = new PersonRelations();
        relations.setPersonMaster(masterPersonId);
        relations.setPersonLink(linkPersonId);
        relations.setCreateDate(new Date());
        relations.setType(type);
        try {
            String id = (String) uuidManager.generateNumber(null, null, null, true);
            relations.setId(id);
            persistenceManager.insert(relations);
            increaseRelationsCounter(masterPersonId, type);
            if (type == RelationsType.OBSERVERS) {
                relations = new PersonRelations();
                relations.setPersonMaster(linkPersonId);
                relations.setPersonLink(masterPersonId);
                relations.setCreateDate(new Date());
                relations.setType(RelationsType.FANS);
                id = (String) uuidManager.generateNumber(null, null, null, true);
                relations.setId(id);
                persistenceManager.insert(relations);
                increaseRelationsCounter(linkPersonId, type);
            }
            if (type == RelationsType.FRIENDS) {
                relations = new PersonRelations();
                relations.setPersonMaster(linkPersonId);
                relations.setPersonLink(masterPersonId);
                relations.setCreateDate(new Date());
                relations.setType(RelationsType.FRIENDS);
                id = (String) uuidManager.generateNumber(null, null, null, true);
                relations.setId(id);
                persistenceManager.insert(relations);
                increaseRelationsCounter(linkPersonId, type);
            }
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        } catch (NumberGenerateException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        }
    }

    private void increaseRelationsCounter(String id, Integer type) throws CrudRuntimeException {
        Person pk = new Person();
        pk.setId(id);
        try {
            Person person = (Person) queryManager.find(pk);
            if (RelationsType.FANS == type) {
                Long fansCounter = person.getFansCounter();
                if (fansCounter == null) {
                    fansCounter = (long) 0;
                }
                fansCounter += 1;
                person.setFansCounter(fansCounter);
            }
            if (RelationsType.OBSERVERS == type) {
                Long observersCounter = person.getObserversCounter();
                if (observersCounter == null) {
                    observersCounter = (long) 0;
                }
                observersCounter += 1;
                person.setObserversCounter(observersCounter);
            }
            if (RelationsType.FRIENDS == type) {
                Long friendsCounter = person.getFriendsCounter();
                if (friendsCounter == null) {
                    friendsCounter = (long) 0;
                }
                friendsCounter += 1;
                person.setFriendsCounter(friendsCounter);
            }
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        }
    }

    public List<Person> loadRelations(String masterPersonId, Integer type) throws CrudApplicationException, CrudRuntimeException {
        return null;
    }

    public void setUuidManager(NumberSystemManager uuidManager) {
        this.uuidManager = uuidManager;
    }
}
