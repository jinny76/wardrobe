package jbolt.android.wardrobe.models;

import android.graphics.Bitmap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jbolt.android.base.AppConfig;
import jbolt.android.base.AppContext;
import jbolt.android.utils.StringUtilities;
import jbolt.android.wardrobe.data.DataFactory;

/**
 * <p>Title: Collocation</p>
 * <p>Description: Collocation</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class Collocation implements Serializable {

    private Date createDate;
    private String id;
    private String templateId;
    private String ownerId = AppContext.user.getId();
    private String description;
    private Boolean illegal;
    private String reportMsg;
    private String reportBy;
    private Boolean shown;
    private String artifactItemIds;
    private Long adoreCounter;
    private Long commentsCounter;
    private List<CollocationComments> comments = new ArrayList<CollocationComments>();
    private List<ArtifactItem> items = new ArrayList<ArtifactItem>();
    private transient Bitmap thumbnail;
    private transient Bitmap pic;

    public static final long serialVersionUID = -1;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<CollocationComments> getComments() {
        return comments;
    }

    public void setComments(List<CollocationComments> comments) {
        this.comments = comments;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ArtifactItem> getItems() {
        return items;
    }

    public void setItems(List<ArtifactItem> items) {
        this.items = items;
    }

    public Long getAdoreCounter() {
        return adoreCounter;
    }

    public void setAdoreCounter(Long adoreCounter) {
        this.adoreCounter = adoreCounter;
    }

    public String getArtifactItemIds() {
        return artifactItemIds;
    }

    public void setArtifactItemIds(String artifactItemIds) {
        this.artifactItemIds = artifactItemIds;
    }

    public Long getCommentsCounter() {
        return commentsCounter;
    }

    public void setCommentsCounter(Long commentsCounter) {
        this.commentsCounter = commentsCounter;
    }

    public Boolean getIllegal() {
        return illegal;
    }

    public void setIllegal(Boolean illegal) {
        this.illegal = illegal;
    }

    public String getReportBy() {
        return reportBy;
    }

    public void setReportBy(String reportBy) {
        this.reportBy = reportBy;
    }

    public String getReportMsg() {
        return reportMsg;
    }

    public void setReportMsg(String reportMsg) {
        this.reportMsg = reportMsg;
    }

    public Boolean getShown() {
        return shown;
    }

    public void setShown(Boolean shown) {
        this.shown = shown;
    }

    public void beforeSave() {
        ArrayList<String> ids = new ArrayList<String>();
        for (ArtifactItem item : items) {
            ids.add(item.getId());
        }
        artifactItemIds = StringUtilities.combineString(ids, "|");
        items.clear();
    }
}
