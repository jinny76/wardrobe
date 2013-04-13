package jbolt.android.wardrobe.service;

import jbolt.android.wardrobe.service.po.Collocation;
import jbolt.android.wardrobe.service.po.CollocationComments;
import jbolt.framework.crud.GenericCrudService;
import jbolt.framework.crud.exception.CrudApplicationException;
import jbolt.framework.crud.exception.CrudRuntimeException;

/**
 * <p>Title: CollocationManager</p>
 * <p>Description: CollocationManager</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public interface CollocationManager extends GenericCrudService<Collocation> {

    /**
     * Add comments
     *
     * @param collocationId Collocation id
     * @param comments      Comments object
     * @return Comment id
     * @throws jbolt.framework.crud.exception.CrudApplicationException
     *          #
     * @throws jbolt.framework.crud.exception.CrudRuntimeException
     *          #
     */
    String addComments(String collocationId, CollocationComments comments) throws CrudApplicationException, CrudRuntimeException;
}
