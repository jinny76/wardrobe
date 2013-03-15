package jbolt.android.wardrobe.models;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: ArtifactTypeModel</p>
 * <p>Description: ArtifactTypeModel</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ArtifactTypeModel {

    private String id;
    private int drawableId;
    private List<ArtifactItemModel> items = new ArrayList<ArtifactItemModel>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public List<ArtifactItemModel> getItems() {
        return items;
    }

    public void setItems(List<ArtifactItemModel> items) {
        this.items = items;
    }
}
