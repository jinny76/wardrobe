package jbolt.android.wardrobe.models;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: Person</p>
 * <p>Description: Person</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class Person implements Serializable {
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
    private String birthday;
    private Long observersCounter;
    private Long fansCounter;
    private Long friendsCounter;
    private Date createDate;
    private Date loginDate;

    public static final long serialVersionUID = -1;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPortraitId() {
        return portraitId;
    }

    public void setPortraitId(String portraitId) {
        this.portraitId = portraitId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSina() {
        return sina;
    }

    public void setSina(String sina) {
        this.sina = sina;
    }

    public String getTencent() {
        return tencent;
    }

    public void setTencent(String tencent) {
        this.tencent = tencent;
    }

    public String getRenren() {
        return renren;
    }

    public void setRenren(String renren) {
        this.renren = renren;
    }

    public String getDouban() {
        return douban;
    }

    public void setDouban(String douban) {
        this.douban = douban;
    }

    public Long getObserversCounter() {
        return observersCounter;
    }

    public void setObserversCounter(Long observersCounter) {
        this.observersCounter = observersCounter;
    }

    public Long getFansCounter() {
        return fansCounter;
    }

    public void setFansCounter(Long fansCounter) {
        this.fansCounter = fansCounter;
    }

    public Long getFriendsCounter() {
        return friendsCounter;
    }

    public void setFriendsCounter(Long friendsCounter) {
        this.friendsCounter = friendsCounter;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
