package jbolt.android.wardrobe.models;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: CollocationComments</p>
 * <p>Description: CollocationComments</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class CollocationComments implements Serializable {

    private String id;
    private String comments;
    private Date createDate;
    private String ownerId;
    private String nick;
    private Collocation collocation;
    public static final long serialVersionUID = -1;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Collocation getCollocation() {
        return collocation;
    }

    public void setCollocation(Collocation collocation) {
        this.collocation = collocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
