package jbolt.android.wardrobe.service;

import java.io.File;
import java.util.List;
import jbolt.android.wardrobe.service.po.Collocation;
import jbolt.android.wardrobe.service.po.CollocationComments;
import jbolt.framework.crud.GenericCrudService;
import jbolt.framework.crud.exception.CrudApplicationException;
import jbolt.framework.crud.exception.CrudRuntimeException;
import jbolt.platform.common.biz.exception.BizAppException;
import jbolt.platform.common.biz.exception.BizRuntimeException;

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
     * Create item with pictures
     *
     * @param collocation Item
     * @param pics        Pictures
     * @return Collocation with id
     * @throws CrudApplicationException #
     * @throws CrudRuntimeException     #
     */
    Collocation createWithPics(Collocation collocation, File[] pics) throws CrudApplicationException, CrudRuntimeException;

    /**
     * Create item with pictures
     *
     * @param collocation Item
     * @param pics        Pictures
     * @throws CrudApplicationException #
     * @throws CrudRuntimeException     #
     */
    void modifyWithPics(Collocation collocation, File[] pics) throws CrudApplicationException, CrudRuntimeException;

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

    /**
     * Load my shows
     *
     * @param personId Person id
     * @return My shows
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    List<Collocation> loadMyShows(String personId) throws BizAppException, BizRuntimeException;

    /**
     * Load collocation
     *
     * @param personId Person id
     * @return Collocation items
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    List<Collocation> loadCollocations(String personId) throws BizAppException, BizRuntimeException;

    /**
     * Load shows
     *
     * @param type     Type of show
     * @param personId Person id
     * @return Collocation list
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    List<Collocation> loadShows(Integer type, String personId) throws BizAppException, BizRuntimeException;

    /**
     * Report illegal collocation
     *
     * @param collocationId Collocation id
     * @param msg           Message
     * @param reportBy      Report by
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    void reportIllegalCollocation(String collocationId, String msg, String reportBy) throws BizAppException, BizRuntimeException;

    /**
     * Find by id
     *
     * @param id Id
     * @return Collocation
     * @throws CrudApplicationException #
     * @throws CrudRuntimeException     #
     */
    Collocation findById(String id) throws CrudApplicationException, CrudRuntimeException;

    /**
     * Show collocation
     *
     * @param collocationId Id
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    void showCollocation(String collocationId) throws BizAppException, BizRuntimeException;

    /**
     * Load comments
     *
     * @param collocationId Id
     * @return Comments models
     * @throws BizAppException     #
     * @throws BizRuntimeException #
     */
    List<CollocationComments> loadComments(String collocationId) throws BizAppException, BizRuntimeException;

}
