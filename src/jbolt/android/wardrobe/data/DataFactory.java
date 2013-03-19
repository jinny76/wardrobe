package jbolt.android.wardrobe.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.utils.Log;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.ObjectUtilities;
import jbolt.android.utils.SDCardUtilities;
import jbolt.android.utils.StringUtilities;
import jbolt.android.utils.image.ImageManager;
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
    private Map<String, ArtifactItemModel> latitude1Mapper = new HashMap<String, ArtifactItemModel>();
    private Map<String, ArtifactItemModel> latitude2Mapper = new HashMap<String, ArtifactItemModel>();
    public static final String FILE_ROOT = "/wardrobe/";
    public static final String OWNER_ID = "NINI";

    public static DataFactory getSingle() {
        if (single == null) {
            single = new DataFactory();
        }
        return single;
    }

    public List<ArtifactItemModel> filter(String latitude1, String latitude2, String type) {
        List<ArtifactItemModel> res = new ArrayList<ArtifactItemModel>();
        ArtifactTypeModel typeModel = typeMapper.get(type);
        for (ArtifactItemModel item : typeModel.getItems()) {
            if (StringUtilities.isEmpty(latitude1)) {
                if (latitude2.equals(item.getLatitude2())) {
                    res.add(item);
                }
            } else {
                if (latitude1.equals(item.getLatitude1())) {
                    if (!StringUtilities.isEmpty(latitude2)) {
                        if (latitude2.equals(item.getLatitude2())) {
                            res.add(item);
                        }
                    } else {
                        res.add(item);
                    }
                }
            }
        }
        return res;
    }

    public void saveCollocation(CollocationModel model) {
        try {
            byte[] objBin = ObjectUtilities.getObjectByteArray(model);
            SDCardUtilities.writeToSDCardFile(getCollocationPath(model.getId()) + "obj.item", objBin, false);
            if (model.getPic() != null) {
                ImageManager.getInstance().saveBitmap(model.getPic(),
                        new File(SDCardUtilities.getSdCardPath() + getCollocationPath(model.getId()) + "pic.jpeg"),
                        new File(SDCardUtilities.getSdCardPath() + getCollocationPath(model.getId()) + "thumb.jpeg"));
            }
        } catch (IOException e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Add collocation failure!");
        }
    }

    public void updateArtifactItem(ArtifactItemModel item, Bitmap pic) {
        try {
            byte[] objBin = ObjectUtilities.getObjectByteArray(item);
            SDCardUtilities.writeToSDCardFile(getItemFolder(item.getType(), item.getId()) + "obj.item", objBin, false);
            if (pic != null) {
                ImageManager.getInstance().saveBitmap(pic,
                        new File(SDCardUtilities.getSdCardPath() + getItemFolder(item.getType(), item.getId()) + "pic.jpeg"),
                        new File(SDCardUtilities.getSdCardPath() + getItemFolder(item.getType(), item.getId()) + "thumb.jpeg"));
                registerItem(item);
            }
        } catch (IOException e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Add new failure!");
        } catch (Exception e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Add new failure!");
        }
    }

    public void addArtifactItem(ArtifactItemModel item, String type, Bitmap pic) {
        ArtifactTypeModel typeModel = typeMapper.get(type);
        typeModel.getItems().add(item);
        item.setType(type);
        item.setOwnerId(OWNER_ID);
        item.setId(String.valueOf(typeModel.getItems().size() + 10));
        try {
            byte[] objBin = ObjectUtilities.getObjectByteArray(item);
            SDCardUtilities.writeToSDCardFile(getItemFolder(type, item.getId()) + "obj.item", objBin, false);
            if (pic != null) {
                ImageManager.getInstance().saveBitmap(pic,
                        new File(SDCardUtilities.getSdCardPath() + getItemFolder(type, item.getId()) + "pic.jpeg"),
                        new File(SDCardUtilities.getSdCardPath() + getItemFolder(type, item.getId()) + "thumb.jpeg"));
                registerItem(item);
            } else {
                copyImageForItem(item, type);
            }
        } catch (IOException e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Add new failure!");
        } catch (Exception e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Add new failure!");
        }
    }

    public void copyImageForItem(ArtifactItemModel item, String type) {
        byte[] pic = SDCardUtilities.readFile(SDCardUtilities.getSdCardPath() + DataFactory.FILE_ROOT + "/tmp/pic.jpeg");
        byte[] thumb = SDCardUtilities.readFile(SDCardUtilities.getSdCardPath() + DataFactory.FILE_ROOT + "/tmp/thumbnail.jpeg");
        if (pic != null) {
            SDCardUtilities.writeToSDCardFile(getItemFolder(type, item.getId()) + "pic.jpeg", pic, false);
        } else {
            Log.i(DataFactory.class.getName(), "Pic isn't found!");
        }
        if (thumb != null) {
            SDCardUtilities.writeToSDCardFile(getItemFolder(type, item.getId()) + "thumb.jpeg", thumb, false);
        }
    }

    public ArtifactItemModel getArtifactItem(String type, String id, boolean loadImg) {
        ArtifactItemModel item = null;
        try {
            byte[] objBin = SDCardUtilities.readSDCardFile(getItemFolder(type, id) + "obj.item");
            if (objBin != null) {
                item = (ArtifactItemModel) ObjectUtilities.readObject(objBin);
                registerItem(item);
                if (loadImg) {
                    loadArtifactImg(item, true);
                }
            }
        } catch (Exception e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Add new failure!");
        }
        return item;
    }

    public void loadArtifactImg(ArtifactItemModel item, boolean loadThumbnailOnly) {
        FileInputStream fis = null;
        try {
            String type = item.getType();
            if (item.getThumbnail() == null) {
                fis = new FileInputStream(new File(SDCardUtilities.getSdCardPath() + getItemFolder(type, item.getId()) + "thumb.jpeg"));
                Bitmap thumbnail = BitmapFactory.decodeStream(fis);
                item.setThumbnail(thumbnail);
            }
            if (!loadThumbnailOnly) {
                fis = new FileInputStream(new File(SDCardUtilities.getSdCardPath() + getItemFolder(type, item.getId()) + "pic.jpeg"));
                Bitmap pic = BitmapFactory.decodeStream(fis);
                item.setPic(pic);
            }
        } catch (FileNotFoundException e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Can not find item:" + item.getType() + "." + item.getId());
        }
    }

    private String getItemFolder(String type, String itemId) {
        return getTypeFolder(type) + itemId + "/";
    }

    private String getCollocationPath(String id) {
        return FILE_ROOT + "collocation/" + id + "/";
    }

    private String getTypeFolder(String type) {
        return FILE_ROOT + type + "/";
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
            for (int i = 0; i < names.length; i++) {
                ArtifactTypeModel typeModel = new ArtifactTypeModel();
                typeModel.setId(names[i]);
                typeModel.setDrawableId(icons[i]);
                typeModel.setPuzzleDrawableId(puzzles[i]);
                typeModel.setCatalogDrawableId(catalogIcons[i]);
                types.add(typeModel);
                typeMapper.put(names[i], typeModel);
                File folder = new File(SDCardUtilities.getSdCardPath() + getTypeFolder(names[i]));
                if (folder.exists()) {
                    File[] files = folder.listFiles();
                    if (files != null && files.length > 0) {
                        for (File file : files) {
                            if (file.isDirectory()) {
                                ArtifactItemModel item = getArtifactItem(names[i], file.getName(), false);
                                if (item != null) {
                                    item.setType(names[i]);
                                    typeModel.getItems().add(item);
                                    registerItem(item);
                                }
                            }
                        }
                    }
                }
            }
        }
        return types;
    }

    private void registerItem(ArtifactItemModel item) {
        latitude1Mapper.put(item.getLatitude1(), item);
        latitude2Mapper.put(item.getLatitude2(), item);
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
