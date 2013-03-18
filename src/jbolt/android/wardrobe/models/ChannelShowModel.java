package jbolt.android.wardrobe.models;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: ChannelShowModel</p>
 * <p>Description: ChannelShowModel</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ChannelShowModel implements Serializable {

    /**
     * 热度，最新；关注
     */
    private String type;
    private List<CollocationModel> collocationModels;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CollocationModel> getCollocationModels() {
        return collocationModels;
    }

    public void setCollocationModels(List<CollocationModel> collocationModels) {
        this.collocationModels = collocationModels;
    }
}
