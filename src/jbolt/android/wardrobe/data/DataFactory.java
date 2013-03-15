package jbolt.android.wardrobe.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jbolt.android.R;
import jbolt.android.wardrobe.models.ArtifactItemModel;
import jbolt.android.wardrobe.models.ArtifactTypeModel;

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
    private Map<String, ArtifactTypeModel> typeMapper = new HashMap<String, ArtifactTypeModel>();

    public static DataFactory getSingle() {
        if (single == null) {
            single = new DataFactory();
        }
        return single;
    }

    public List<ArtifactTypeModel> getTypes() {
        if (types.size() == 0) {
            String[] names = new String[]{"clothes", "shoe", "dress", "one_piece_dress"};
            int[] icons = new int[]{R.drawable.bottom_clothes_icon, R.drawable.bottom_shoe_icon,
                    R.drawable.bottom_dress_icon, R.drawable.bottom_one_piece_dress_icon};
            for (int i = 0; i < names.length; i++) {
                ArtifactTypeModel typeModel = new ArtifactTypeModel();
                typeModel.setId(names[i]);
                typeModel.setDrawableId(icons[i]);
                types.add(typeModel);
                typeMapper.put(names[i], typeModel);
                if (names[i].equals("clothes")) {
                    ArtifactItemModel item = new ArtifactItemModel();
                    item.setDrawable(R.drawable.pho2);
                    typeModel.getItems().add(item);
                } else if (names[i].equals("one_piece_dress")) {
                    ArtifactItemModel item = new ArtifactItemModel();
                    item.setDrawable(R.drawable.pho1);
                    typeModel.getItems().add(item);
                }
            }
        }
        return types;
    }

}
