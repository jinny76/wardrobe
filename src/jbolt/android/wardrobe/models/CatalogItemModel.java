package jbolt.android.wardrobe.models;

/**
 * <p>Title: CatalogItemModel</p>
 * <p>Description: CatalogItemModel</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class CatalogItemModel {

    private ArtifactTypeModel type1;
    private ArtifactTypeModel type2;
    private ArtifactTypeModel type3;

    public ArtifactTypeModel getType1() {
        return type1;
    }

    public void setType1(ArtifactTypeModel type1) {
        this.type1 = type1;
    }

    public ArtifactTypeModel getType2() {
        return type2;
    }

    public void setType2(ArtifactTypeModel type2) {
        this.type2 = type2;
    }

    public ArtifactTypeModel getType3() {
        return type3;
    }

    public void setType3(ArtifactTypeModel type3) {
        this.type3 = type3;
    }
}
