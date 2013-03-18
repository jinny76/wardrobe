package jbolt.android.wardrobe.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jbolt.android.R;
import jbolt.android.wardrobe.models.ArtifactItemModel;
import jbolt.android.wardrobe.models.ArtifactTypeModel;
import jbolt.android.wardrobe.models.CollocationModel;

/**
 * <p>Title: DataFactory</p>
 * <p>Description: DataFactory</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class DataFactory {

    public static DataFactory single;
    private List<ArtifactTypeModel> types = new ArrayList<ArtifactTypeModel>();
    private List<CollocationModel> collocations = new ArrayList<CollocationModel>();
    private Map<String, ArtifactTypeModel> typeMapper = new HashMap<String, ArtifactTypeModel>();

    public static DataFactory getSingle() {
        if (single == null) {
            single = new DataFactory();
        }
        return single;
    }

    public List<ArtifactTypeModel> getTypes() {
        if (types.size() == 0) {
            String[] names = new String[]{"clothes", "tshirt", "sweater", "shirt", "dress", "pants", "accessory", "shoes", "others"};
            int[] icons = new int[]{
                    R.drawable.bottom_clothes_icon, R.drawable.bottom_tshirt_icon,
                    R.drawable.bottom_sweater_icon, R.drawable.bottom_shirt_icon,
                    R.drawable.bottom_dress_icon, R.drawable.bottom_pants_icon,
                    R.drawable.bottom_accessory_icon, R.drawable.bottom_shoe_icon,
                    R.drawable.bottom_others_icon
            };

            int[] catalogIcons = new int[]{
                    R.drawable.clothes, R.drawable.tshirt,
                    R.drawable.sweater, R.drawable.shirt,
                    R.drawable.dress, R.drawable.pants,
                    R.drawable.accessory, R.drawable.shoes, R.drawable.others};
            int[] puzzles = new int[]{
                    R.drawable.module, R.drawable.module,
                    R.drawable.module, R.drawable.module,
                    R.drawable.module, R.drawable.module,
                    R.drawable.module, R.drawable.module,
                    R.drawable.module};
            int[][] items = new int[][]{
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3},
                    {R.drawable.pho2, R.drawable.pho3, R.drawable.pho1},
                    {R.drawable.pho3, R.drawable.pho1, R.drawable.pho2},
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3},
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3},
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3},
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3},
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3},
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3},
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3}
            };
            for (int i = 0; i < names.length; i++) {
                ArtifactTypeModel typeModel = new ArtifactTypeModel();
                typeModel.setId(names[i]);
                typeModel.setDrawableId(icons[i]);
                typeModel.setPuzzleDrawableId(puzzles[i]);
                typeModel.setCatalogDrawableId(catalogIcons[i]);
                types.add(typeModel);
                typeMapper.put(names[i], typeModel);
                int[] currItems = items[i];
                for (int currItem : currItems) {
                    ArtifactItemModel item = new ArtifactItemModel();
                    item.setDrawable(currItem);
                    typeModel.getItems().add(item);
                }
            }
        }
        return types;
    }


    public List<CollocationModel> getCollocations() {
        if (collocations.size() == 0) {
            int[][] items = new int[][]{
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3},
                    {R.drawable.pho2, R.drawable.pho3, R.drawable.pho1},
                    {R.drawable.pho3, R.drawable.pho1, R.drawable.pho2},
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3},
                    {R.drawable.pho2, R.drawable.pho3, R.drawable.pho1},
                    {R.drawable.pho3, R.drawable.pho1, R.drawable.pho2},
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3},
                    {R.drawable.pho2, R.drawable.pho3, R.drawable.pho1},
                    {R.drawable.pho3, R.drawable.pho1, R.drawable.pho2},
                    {R.drawable.pho1, R.drawable.pho2, R.drawable.pho3},
                    {R.drawable.pho2, R.drawable.pho3, R.drawable.pho1},
                    {R.drawable.pho3, R.drawable.pho1, R.drawable.pho2}
            };

            int index = 0;
            for (int[] currItems : items) {
                CollocationModel collocationModel = new CollocationModel();
                collocationModel.setId("Collocation" + index++);
                collocationModel.setCreateDate("2013年\n3月" + (31 - index) + "日");
                collocations.add(collocationModel);
                for (int currItem : currItems) {
                    ArtifactItemModel item = new ArtifactItemModel();
                    item.setDrawable(currItem);
                    collocationModel.getItems().add(item);
                }
            }
        }
        return collocations;
    }

}
