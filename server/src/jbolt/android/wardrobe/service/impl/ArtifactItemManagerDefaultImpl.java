package jbolt.android.wardrobe.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import jbolt.android.wardrobe.service.ArtifactItemManager;
import jbolt.android.wardrobe.service.ImageManager;
import jbolt.android.wardrobe.service.po.ArtifactItem;
import jbolt.core.dao.exception.DAOException;
import jbolt.core.utilities.ObjectUtilities;
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

    private ImageManager imageManager;

    public ArtifactItem createWithPics(ArtifactItem item, File[] pics) throws CrudApplicationException, CrudRuntimeException {
        item.setCreateDate(new Date());
        ArtifactItem artifactItem = create(item);
        imageManager.savePic(artifactItem.getId(), pics[0], true);
        imageManager.savePic(artifactItem.getId(), pics[1], false);
        return artifactItem;
    }

    @SuppressWarnings("unchecked")
    public List<ArtifactItem> findItemsByType(String ownerId, String type) throws CrudApplicationException, CrudRuntimeException {
        ArtifactItem criteria = new ArtifactItem();
        criteria.setOwnerId(ownerId);
        criteria.setType(type);
        try {
            return (List<ArtifactItem>) queryManager.findByAnyCriteria(criteria);
        } catch (DAOException e) {
            tracer.logError(ObjectUtilities.printExceptionStack(e));
            throw new CrudRuntimeException(e);
        }
    }

    @Override
    public void delete(ArtifactItem domain) throws CrudApplicationException, CrudRuntimeException {
        super.delete(domain);
        imageManager.deletePic(domain.getId());
    }

    public void setImageManager(ImageManager imageManager) {
        this.imageManager = imageManager;
    }
}
