package jbolt.android.wardrobe.service.impl;

import java.util.Date;
import java.util.List;
import jbolt.android.wardrobe.service.PersonManager;
import jbolt.android.wardrobe.service.po.Person;
import jbolt.android.wardrobe.service.po.PersonRelations;
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
        } catch (PersistenceException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        } catch (NumberGenerateException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        }
    }

    public List<Person> loadRelations(String masterPersonId, Integer type) throws CrudApplicationException, CrudRuntimeException {
        return null;
    }
}
