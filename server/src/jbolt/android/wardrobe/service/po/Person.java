package jbolt.android.wardrobe.service.po;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import jbolt.core.datadict.annotation.AutoGenerator;
import jbolt.core.utilities.bean.SuperPojo;

/**
 * <p>Title: Person</p>
 * <p>Description: Person</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
@Entity
@Table(name = "person")
public class Person extends SuperPojo {
    private String id;
    private String portraitId;
    private String nick;
    private String signature;
    private String gender;
    private String mail;
    private String mobile;
    private String sina;
    private String tencent;
    private String renren;
    private String douban;
    private String password;
    private Long observersCounter;
    private Long fansCounter;
    private Long friendsCounter;
    private Date createDate;
    private Date loginDate;

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

    @Column(name = "portrait_id", length = 32)
    public String getPortraitId() {
        return portraitId;
    }

    public void setPortraitId(String portraitId) {
        this.portraitId = portraitId;
    }

    @Column(name = "pwd", length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "nick", length = 100)
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Column(name = "signature", length = 100)
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Column(name = "gender", length = 20)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "mail", length = 100)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Column(name = "mobile", length = 100)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "sina", length = 100)
    public String getSina() {
        return sina;
    }

    public void setSina(String sina) {
        this.sina = sina;
    }

    @Column(name = "tencent", length = 100)
    public String getTencent() {
        return tencent;
    }

    public void setTencent(String tencent) {
        this.tencent = tencent;
    }

    @Column(name = "renren", length = 100)
    public String getRenren() {
        return renren;
    }

    public void setRenren(String renren) {
        this.renren = renren;
    }

    @Column(name = "douban", length = 100)
    public String getDouban() {
        return douban;
    }

    public void setDouban(String douban) {
        this.douban = douban;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "login_date")
    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    @Column(name = "observer_counter", precision = 18, scale = 0)
    public Long getObserversCounter() {
        return observersCounter;
    }

    public void setObserversCounter(Long observersCounter) {
        this.observersCounter = observersCounter;
    }

    @Column(name = "fans_counter", precision = 18, scale = 0)
    public Long getFansCounter() {
        return fansCounter;
    }

    public void setFansCounter(Long fansCounter) {
        this.fansCounter = fansCounter;
    }

    @Column(name = "friends_counter", precision = 18, scale = 0)
    public Long getFriendsCounter() {
        return friendsCounter;
    }

    public void setFriendsCounter(Long friendsCounter) {
        this.friendsCounter = friendsCounter;
    }
}
