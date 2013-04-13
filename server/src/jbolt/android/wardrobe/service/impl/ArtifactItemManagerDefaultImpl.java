package jbolt.android.wardrobe.service.impl;

import java.io.File;
import jbolt.android.wardrobe.service.ArtifactItemManager;
import jbolt.android.wardrobe.service.po.ArtifactItem;
import jbolt.framework.crud.exception.CrudApplicationException;
import jbolt.framework.crud.exception.CrudRuntimeException;
import jbolt.framework.crud.impl.GenericCrudDefaultService;

/**
 * <p>Title: ArtifactItemManagerDefaultImpl</p>
 * <p>Description: ArtifactItemManagerDefaultImpl</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ArtifactItemManagerDefaultImpl extends GenericCrudDefaultService<ArtifactItem> implements ArtifactItemManager {

    public ArtifactItem createWithPics(ArtifactItem item, File[] pics) throws CrudApplicationException, CrudRuntimeException {
        return create(item);
    }
}
