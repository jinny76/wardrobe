package jbolt.android.wardrobe.service;

import java.io.File;
import java.util.List;
import jbolt.android.wardrobe.service.po.ArtifactItem;
import jbolt.framework.crud.GenericCrudService;
import jbolt.framework.crud.exception.CrudApplicationException;
import jbolt.framework.crud.exception.CrudRuntimeException;

/**
 * <p>Title: ArtifactItemManager</p>
 * <p>Description: ArtifactItemManager</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public interface ArtifactItemManager extends GenericCrudService<ArtifactItem> {


    /**
     * Create item with pictures
     *
     * @param item Item
     * @param pics Pictures
     * @return Artifact item with id
     * @throws CrudApplicationException #
     * @throws CrudRuntimeException     #
     */
    ArtifactItem createWithPics(ArtifactItem item, File[] pics) throws CrudApplicationException, CrudRuntimeException;

    /**
     * Find items by type
     *
     * @param ownerId Owner id
     * @param type    Type
     * @return Items
     * @throws jbolt.framework.crud.exception.CrudApplicationException
     *          #
     * @throws jbolt.framework.crud.exception.CrudRuntimeException
     *          #
     */
    List<ArtifactItem> findItemsByType(String ownerId, String type) throws CrudApplicationException, CrudRuntimeException;

}
