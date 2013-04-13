package jbolt.android.wardrobe.models;

import android.graphics.Bitmap;
import java.io.Serializable;

/**
 * <p>Title: ArtifactItem</p>
 * <p>Description: ArtifactItem</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ArtifactItem implements Serializable {

    private String id;
    private int drawable;
    private transient Bitmap thumbnail;
    private transient Bitmap pic;
    private String ownerId;
    private String latitude1;
    private String latitude2;
    private String description;
    private String type;
    public static final long serialVersionUID = -1;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude1() {
        return latitude1;
    }

    public void setLatitude1(String latitude1) {
        this.latitude1 = latitude1;
    }

    public String getLatitude2() {
        return latitude2;
    }

    public void setLatitude2(String latitude2) {
        this.latitude2 = latitude2;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
