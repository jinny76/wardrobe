package jbolt.android.utils.image;

import java.io.Serializable;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ImageTag implements Serializable {

    private String url;
    private String bigUrl;
    private ImageLoadHandler lazyLoadHandler;

    public ImageTag(String url, String bigUrl) {
        this.url = url;
        this.bigUrl = bigUrl;
    }

    public ImageLoadHandler getLazyLoadHandler() {
        return lazyLoadHandler;
    }

    public void setLazyLoadHandler(ImageLoadHandler lazyLoadHandler) {
        this.lazyLoadHandler = lazyLoadHandler;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBigUrl() {
        return bigUrl;
    }

    public void setBigUrl(String bigUrl) {
        this.bigUrl = bigUrl;
    }
}
