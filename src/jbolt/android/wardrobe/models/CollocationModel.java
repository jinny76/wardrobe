package jbolt.android.wardrobe.models;

import android.graphics.Bitmap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: CollocationModel</p>
 * <p>Description: CollocationModel</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class CollocationModel implements Serializable {

    private String createDate;
    private String id;
    private String templateId;
    private String ownerId;
    private List<Comments> comments = new ArrayList<Comments>();
    private List<ArtifactItemModel> items = new ArrayList<ArtifactItemModel>();
    private transient Bitmap thumbnail;
    private transient Bitmap pic;

    public static final long serialVersionUID = -1;

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ArtifactItemModel> getItems() {
        return items;
    }

    public void setItems(List<ArtifactItemModel> items) {
        this.items = items;
    }
}
