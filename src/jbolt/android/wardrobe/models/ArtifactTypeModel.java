package jbolt.android.wardrobe.models;

import java.io.Serializable;
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
public class ArtifactTypeModel implements Serializable {

    private String id;
    private int drawableId;
    private int resourceId;
    private int catalogDrawableId;
    private int puzzleDrawableId;
    private List<ArtifactItem> items = new ArrayList<ArtifactItem>();

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getCatalogDrawableId() {
        return catalogDrawableId;
    }

    public void setCatalogDrawableId(int catalogDrawableId) {
        this.catalogDrawableId = catalogDrawableId;
    }

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

    public int getPuzzleDrawableId() {
        return puzzleDrawableId;
    }

    public void setPuzzleDrawableId(int puzzleDrawableId) {
        this.puzzleDrawableId = puzzleDrawableId;
    }

    public List<ArtifactItem> getItems() {
        return items;
    }

    public void setItems(List<ArtifactItem> items) {
        this.items = items;
    }
}
