package jbolt.android.wardrobe.service;

import java.util.List;
import jbolt.android.wardrobe.service.po.Person;
import jbolt.framework.crud.GenericCrudService;
import jbolt.framework.crud.exception.CrudApplicationException;
import jbolt.framework.crud.exception.CrudRuntimeException;

/**
 * <p>Title: PersonManager</p>
 * <p>Description: PersonManager</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public interface PersonManager extends GenericCrudService<Person> {

    /**
     * Add relations
     *
     * @param masterPersonId Person id
     * @param linkPersonId   Link person id
     * @param type           Type
     * @throws jbolt.framework.crud.exception.CrudApplicationException
     *          #
     * @throws jbolt.framework.crud.exception.CrudRuntimeException
     *          #
     */
    void addRelations(String masterPersonId, String linkPersonId, Integer type) throws CrudApplicationException, CrudRuntimeException;

    /**
     * Load relations
     *
     * @param masterPersonId Master person id
     * @param type           Type
     * @return Person list
     * @throws CrudApplicationException #
     * @throws CrudRuntimeException     #
     */
    List<Person> loadRelations(String masterPersonId, Integer type) throws CrudApplicationException, CrudRuntimeException;
}
