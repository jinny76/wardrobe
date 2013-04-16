package jbolt.android.wardrobe.service.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import jbolt.core.datadict.annotation.AutoGenerator;
import jbolt.core.utilities.bean.SuperPojo;

/**
 * <p>Title: Collocation</p>
 * <p>Description: Collocation</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
@Entity
@Table(name = "collocation")
public class Collocation extends SuperPojo {

    private Date createDate;
    private String id;
    private String templateId;
    private String ownerId;
    private Boolean illegal;
    private String reportMsg;
    private String reportBy;
    private Boolean show;
    private String artifactItemIds;
    private String description;
    private Long adoreCounter;
    private Long commentsCounter;
    private List<ArtifactItem> items = new ArrayList<ArtifactItem>();

    @Column(name = "adore_counter", precision = 18, scale = 0)
    public Long getAdoreCounter() {
        return adoreCounter;
    }

    public void setAdoreCounter(Long adoreCounter) {
        this.adoreCounter = adoreCounter;
    }

    @Column(name = "report_by", length = 32)
    public String getReportBy() {
        return reportBy;
    }

    public void setReportBy(String reportBy) {
        this.reportBy = reportBy;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Id
    @Column(name = "id", length = 32)
    @AutoGenerator(generator = "uuidManager")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "template_id", length = 32)
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Column(name = "owner_id", length = 32)
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name = "comments_counter", precision = 18, scale = 0)
    public Long getCommentsCounter() {
        return commentsCounter;
    }

    public void setCommentsCounter(Long commentsCounter) {
        this.commentsCounter = commentsCounter;
    }

    @Column(name = "illegal")
    public Boolean getIllegal() {
        return illegal;
    }

    public void setIllegal(Boolean illegal) {
        this.illegal = illegal;
    }

    @Column(name = "report_msg", length = 500)
    public String getReportMsg() {
        return reportMsg;
    }

    public void setReportMsg(String reportMsg) {
        this.reportMsg = reportMsg;
    }

    @Column(name = "show")
    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    @Column(name = "artifact_item_ids", length = 500)
    public String getArtifactItemIds() {
        return artifactItemIds;
    }

    public void setArtifactItemIds(String artifactItemIds) {
        this.artifactItemIds = artifactItemIds;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    public List<ArtifactItem> getItems() {
        return items;
    }

    public void setItems(List<ArtifactItem> items) {
        this.items = items;
    }
}
