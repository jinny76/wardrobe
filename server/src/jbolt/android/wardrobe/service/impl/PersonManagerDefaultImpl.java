package jbolt.android.wardrobe.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import jbolt.android.wardrobe.PersonMessageType;
import jbolt.android.wardrobe.RelationsType;
import jbolt.android.wardrobe.service.ImageManager;
import jbolt.android.wardrobe.service.PersonManager;
import jbolt.android.wardrobe.service.po.Person;
import jbolt.android.wardrobe.service.po.PersonMessages;
import jbolt.android.wardrobe.service.po.PersonRelations;
import jbolt.android.webservice.servlet.LocalMethod;
import jbolt.core.dao.DAOExecutor;
import jbolt.core.dao.exception.DAOException;
import jbolt.core.dao.exception.PersistenceException;
import jbolt.core.dao.meta.JDBCQueryMeta;
import jbolt.core.numbering.NumberSystemManager;
import jbolt.core.numbering.exception.NumberGenerateException;
import jbolt.core.utilities.ObjectUtilities;
import jbolt.framework.crud.exception.CrudApplicationException;
import jbolt.framework.crud.exception.CrudRuntimeException;
import jbolt.framework.crud.impl.GenericCrudDefaultService;
import jbolt.platform.common.biz.exception.BizAppException;
import jbolt.platform.common.biz.exception.BizRuntimeException;

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
    private DAOExecutor daoExecutor;
    private ImageManager imageManager;

    public Person createWithPics(Person person, File[] pics) throws CrudApplicationException, CrudRuntimeException {
        person.setCreateDate(new Date());
        Person _person = create(person);
        imageManager.savePic(_person.getId(), pics[0], true);
        imageManager.savePic(_person.getId(), pics[1], false);
        return _person;
    }

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
                PersonMessages personMessages = new PersonMessages();
                personMessages.setType(PersonMessageType.FANS);
                personMessages.setSendFrom(masterPersonId);
                personMessages.setSendTo(linkPersonId);
                personMessages.setMsg(getNickName(masterPersonId) + WebUtils.getI18nValue("messages.attention"));
                sendMessage(personMessages);
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
                PersonMessages personMessages = new PersonMessages();
                personMessages.setType(PersonMessageType.FRIENDS);
                personMessages.setSendFrom(masterPersonId);
                personMessages.setSendTo(linkPersonId);
                personMessages.setMsg(getNickName(masterPersonId) + WebUtils.getI18nValue("messages.friends"));
                sendMessage(personMessages);
            }
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        } catch (NumberGenerateException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        }
    }

    public String getNickName(String personId) throws CrudRuntimeException {
        Person pk = new Person();
        pk.setId(personId);
        try {
            Person res = (Person) queryManager.find(pk);
            return res.getNick();
        } catch (DAOException e) {
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

    @SuppressWarnings("unchecked")
    public List<Person> loadRelations(String masterPersonId, Integer type) throws BizAppException, BizRuntimeException {
        String sql = "select person.* from person inner join person_relations on person.id=person_relations.person_master where " +
                "person.id=? and person_master.type=? ";
        JDBCQueryMeta queryMeta = new JDBCQueryMeta();
        queryMeta.setSql(sql);
        queryMeta.setParameters(new Object[]{masterPersonId, type});
        try {
            return (List<Person>) daoExecutor.executeQuery(queryMeta);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    public void sendMessage(PersonMessages messages) throws CrudApplicationException, CrudRuntimeException {
        try {
            String id = (String) uuidManager.generateNumber(null, null, null, true);
            messages.setId(id);
            messages.setCreateDate(new Date());
            persistenceManager.insert(messages);
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        } catch (NumberGenerateException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<PersonMessages> loadUnreadMessages(String personId) throws BizAppException, BizRuntimeException {
        String sql = "select person.* from person_messages where send_to=?";
        JDBCQueryMeta queryMeta = new JDBCQueryMeta();
        queryMeta.setSql(sql);
        queryMeta.setParameters(new Object[]{personId});
        try {
            return (List<PersonMessages>) daoExecutor.executeQuery(queryMeta);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    public void changePassword(String personId, String newPwd) throws BizAppException, BizRuntimeException {
        Person pk = new Person();
        pk.setId(personId);
        try {
            Person person = (Person) queryManager.find(pk);
            person.setPassword(newPwd);
            person.getModifiedFields().add("password");
            persistenceManager.update(person);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    public void modifyWithPics(Person person, File[] pics) throws CrudApplicationException, CrudRuntimeException {
        person.getModifiedFields().add("*");
        try {
            persistenceManager.update(person);
            imageManager.savePic(person.getId(), pics[0], true);
            imageManager.savePic(person.getId(), pics[1], false);
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        }
    }

    public void offenceReport(String personId, String msg, String reportBy) throws BizAppException, BizRuntimeException {
        Person pk = new Person();
        pk.setId(personId);
        try {
            Person person = find(pk);
            person.setOffenceReport(msg);
            person.setOffenceReportBy(reportBy);
            person.setOffenceReportDate(new Date());
            persistenceManager.update(person);
        } catch (CrudApplicationException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizAppException(e);
        } catch (CrudRuntimeException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new BizRuntimeException(e);
        }
    }

    @LocalMethod
    public void setDaoExecutor(DAOExecutor daoExecutor) {
        this.daoExecutor = daoExecutor;
    }

    @LocalMethod
    public void setUuidManager(NumberSystemManager uuidManager) {
        this.uuidManager = uuidManager;
    }

    public void setImageManager(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

}
