package jbolt.android.meta;

import android.graphics.drawable.Drawable;
import java.io.Serializable;

/**
 * <p>Title: ApplicationMeta</p>
 * <p>Description: ApplicationMeta</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ApplicationMeta implements Serializable {
    private transient Drawable icon;
    private String desc;
    private long uid;
    private int iconResourceId;
    private String url;
    private String actionId;
    private String iconUrl;
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
        this.appId = String.valueOf(uid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationMeta that = (ApplicationMeta) o;

        if (appId != null ? !appId.equals(that.appId) : that.appId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return appId != null ? appId.hashCode() : 0;
    }
}
