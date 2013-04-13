package jbolt.android.wardrobe.service.po;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import jbolt.core.datadict.annotation.AutoGenerator;
import jbolt.core.utilities.bean.SuperPojo;

/**
 * <p>Title: ArtifactItem</p>
 * <p>Description: ArtifactItem</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
@Entity
@Table(name = "artifact_item")
public class ArtifactItem extends SuperPojo {

    private String id;
    private String picId;
    private String ownerId;
    private String latitude1;
    private String latitude2;
    private String description;
    private String type;
    private Date createDate;
    public static final long serialVersionUID = -1;

    @Id
    @Column(name = "id", length = 32)
    @AutoGenerator(generator = "uuidManager")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "pic_id", length = 32)
    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    @Column(name = "owner_id", length = 32)
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name = "latitude1", length = 100)
    public String getLatitude1() {
        return latitude1;
    }

    public void setLatitude1(String latitude1) {
        this.latitude1 = latitude1;
    }

    @Column(name = "latitude2", length = 100)
    public String getLatitude2() {
        return latitude2;
    }

    public void setLatitude2(String latitude2) {
        this.latitude2 = latitude2;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "type", length = 100)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
