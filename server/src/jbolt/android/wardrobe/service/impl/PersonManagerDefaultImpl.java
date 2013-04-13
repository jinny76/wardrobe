package jbolt.android.wardrobe.service.impl;

import java.util.List;
import jbolt.android.wardrobe.service.PersonManager;
import jbolt.android.wardrobe.service.po.Person;
import jbolt.android.wardrobe.service.po.PersonRelations;
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

    public void addRelations(String masterPersonId, String linkPersonId, Integer type) throws CrudApplicationException, CrudRuntimeException {
        PersonRelations relations = new PersonRelations();
        relations.setPersonMaster(masterPersonId);
        relations.setPersonLink(linkPersonId);
    }

    public List<Person> loadRelations(String masterPersonId, Integer type) throws CrudApplicationException, CrudRuntimeException {
        return null;
    }
}
