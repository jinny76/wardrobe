package jbolt.android.wardrobe.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.utils.Log;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.ObjectUtilities;
import jbolt.android.utils.SDCardUtilities;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.models.ArtifactItem;
import jbolt.android.wardrobe.models.ArtifactTypeModel;
import jbolt.android.wardrobe.models.Collocation;
import jbolt.android.wardrobe.service.impl.ArtifactItemManagerDefaultImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

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
    private List<Collocation> collocations = new ArrayList<Collocation>();
    private Map<String, ArtifactTypeModel> typeMapper = new HashMap<String, ArtifactTypeModel>();
    private List<Collocation> allCollocations = new ArrayList<Collocation>();
    private Map<String, ArtifactItem> latitude1Mapper = new HashMap<String, ArtifactItem>();
    private Map<String, ArtifactItem> latitude2Mapper = new HashMap<String, ArtifactItem>();
    public static final String FILE_ROOT = "/wardrobe/";
    public static final String OWNER_ID = "NINI";

    public static DataFactory getSingle() {
        if (single == null) {
            single = new DataFactory();
        }
        return single;
    }

    public List<ArtifactItem> filter(String latitude1, String latitude2, String type) {
        List<ArtifactItem> res = new ArrayList<ArtifactItem>();
        ArtifactTypeModel typeModel = typeMapper.get(type);
        for (ArtifactItem item : typeModel.getItems()) {
            if ((latitude1 == null || item.getLatitude1().equals(latitude1))
                && (latitude2 == null || item.getLatitude2().equals(latitude2))) {
                res.add(item);
            }
        }
        return res;
    }

    public void deleteItem(ArtifactItem item) {
        ArtifactTypeModel typeModel = typeMapper.get(item.getType());
        typeModel.getItems().remove(item);
        SDCardUtilities.delete(getItemFolder(item.getType(), item.getId()));
    }

    public Map<String, TreeSet<Collocation>> groupByDate() {
        loadAllCollocations();
        Map<String, TreeSet<Collocation>> group = new HashMap<String, TreeSet<Collocation>>();
        for (Collocation collocationModel : allCollocations) {
            Date createDate = new Date(Long.parseLong(collocationModel.getCreateDate()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dayStr = sdf.format(createDate);
            TreeSet<Collocation> models = group.get(dayStr);
            if (models == null) {
                models = new TreeSet<Collocation>(
                    new Comparator<Collocation>() {
                        public int compare(
                            Collocation collocationModel, Collocation collocationModel2) {
                            return collocationModel2.getCreateDate().compareTo(collocationModel.getCreateDate());
                        }
                    });
                group.put(dayStr, models);
            }
            models.add(collocationModel);
        }
        return group;
    }

    public List<Collocation> getCollocations() {
        return collocations;
    }

    private void loadAllCollocations() {
        allCollocations.clear();
        File root = new File(SDCardUtilities.getSdCardPath() + getCollocationRoot());
        File[] files = root.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    Collocation item = getCollocationModel(file.getName(), true);
                    allCollocations.add(item);
                }
            }
        }
    }

    public Collocation getCollocationModel(String id, boolean loadImg) {
        Collocation item = null;
        try {
            byte[] objBin = SDCardUtilities.readSDCardFile(getCollocationPath(id) + "obj.item");
            if (objBin != null) {
                item = (Collocation) ObjectUtilities.readObject(objBin);
                if (loadImg) {
                    loadCollocationImg(item, true);
                }
            }
        } catch (Exception e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Find item failure!");
        }
        return item;
    }

    public void loadCollocationImg(Collocation item, boolean loadThumbnailOnly) {
        FileInputStream fis = null;
        try {
            if (item.getThumbnail() == null) {
                fis = new FileInputStream(
                    new File(SDCardUtilities.getSdCardPath() + getCollocationPath(item.getId()) + "thumb.jpeg"));
                Bitmap thumbnail = BitmapFactory.decodeStream(fis);
                item.setThumbnail(thumbnail);
            }
            if (!loadThumbnailOnly) {
                fis = new FileInputStream(
                    new File(SDCardUtilities.getSdCardPath() + getCollocationPath(item.getId()) + "pic.jpeg"));
                Bitmap pic = BitmapFactory.decodeStream(fis);
                item.setPic(pic);
            }
        } catch (FileNotFoundException e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(
                AppContext.context, "Can not find collocation:" + item.getId() + "." + item.getId());
        }
    }

    public void saveCollocation(Collocation model) {
        try {
            Date createDate = new Date();
            model.setId(UUID.randomUUID().toString());
            model.setCreateDate(String.valueOf(createDate.getTime()));
            byte[] objBin = ObjectUtilities.getObjectByteArray(model);
            SDCardUtilities.writeToSDCardFile(getCollocationPath(model.getId()) + "obj.item", objBin, false);
            if (model.getPic() != null) {
                ImageManager.getInstance().saveBitmap(
                    model.getPic(),
                    new File(SDCardUtilities.getSdCardPath() + getCollocationPath(model.getId()) + "pic.jpeg"),
                    new File(SDCardUtilities.getSdCardPath() + getCollocationPath(model.getId()) + "thumb.jpeg"));
            }
        } catch (IOException e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Add collocation failure!");
        }
    }

    public void updateArtifactItem(ArtifactItem item, Bitmap pic) {
        try {
            byte[] objBin = ObjectUtilities.getObjectByteArray(item);
            SDCardUtilities.writeToSDCardFile(getItemFolder(item.getType(), item.getId()) + "obj.item", objBin, false);
            if (pic != null) {
                ImageManager.getInstance().saveBitmap(
                    pic,
                    new File(
                        SDCardUtilities.getSdCardPath() + getItemFolder(item.getType(), item.getId()) + "pic.jpeg"),
                    new File(
                        SDCardUtilities.getSdCardPath() + getItemFolder(item.getType(), item.getId()) + "thumb.jpeg"));
                registerItem(item);
            }
        } catch (IOException e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Update failure!");
        } catch (Exception e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Update failure!");
        }
    }

    public void addArtifactItem(ArtifactItem item, String type, Bitmap pic) {
        ArtifactTypeModel typeModel = typeMapper.get(type);
        typeModel.getItems().add(item);
        item.setType(type);
        item.setOwnerId(OWNER_ID);
        item.setId(String.valueOf(typeModel.getItems().size() + 10));
        try {
            byte[] objBin = ObjectUtilities.getObjectByteArray(item);
            SDCardUtilities.writeToSDCardFile(getItemFolder(type, item.getId()) + "obj.item", objBin, false);
            File picFile = null;
            File thumbnailFile = null;
            if (pic != null) {
                picFile =
                    new File(SDCardUtilities.getSdCardPath() + getItemFolder(type, item.getId()) + "pic.jpeg");
                thumbnailFile =
                    new File(SDCardUtilities.getSdCardPath() + getItemFolder(type, item.getId()) + "thumb.jpeg");
                ImageManager.getInstance().saveBitmap(pic, picFile, thumbnailFile);
                registerItem(item);
            } else {
                File[] pics = copyImageForItem(item, type);
                picFile = pics[0];
                thumbnailFile = pics[1];
            }
            ArtifactItemManagerDefaultImpl.createWithPics(
                item, new File[]{picFile, thumbnailFile}, new BaseHandler() {
                @Override
                protected void handleMsg(Message msg) throws Exception {
                    System.out.println("msg = " + msg);
                }
            });
        } catch (IOException e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Add new failure!");
        } catch (Exception e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Add new failure!");
        }
    }

    public File[] copyImageForItem(ArtifactItem item, String type) {
        File[] pics = new File[2];
        byte[] pic =
            SDCardUtilities.readFile(SDCardUtilities.getSdCardPath() + DataFactory.FILE_ROOT + "/tmp/pic.jpeg");
        byte[] thumb = SDCardUtilities.readFile(
            SDCardUtilities.getSdCardPath() + DataFactory.FILE_ROOT + "/tmp/thumbnail.jpeg");
        if (pic != null) {
            pics[0] = SDCardUtilities.writeToSDCardFile(getItemFolder(type, item.getId()) + "pic.jpeg", pic, false);
        } else {
            Log.i(DataFactory.class.getName(), "Pic isn't found!");
        }
        if (thumb != null) {
            pics[1] = SDCardUtilities.writeToSDCardFile(getItemFolder(type, item.getId()) + "thumb.jpeg", thumb, false);
        }
        return pics;
    }

    public void initThumbnail(String type, boolean loadThumbnailOnly) {
        ArtifactTypeModel typeModel = typeMapper.get(type);
        List<ArtifactItem> items = typeModel.getItems();
        for (ArtifactItem item : items) {
            loadArtifactImg(item, loadThumbnailOnly);
        }
    }

    public ArtifactItem getArtifactItem(String type, String id, boolean loadImg) {
        ArtifactItem item = null;
        try {
            byte[] objBin = SDCardUtilities.readSDCardFile(getItemFolder(type, id) + "obj.item");
            if (objBin != null) {
                item = (ArtifactItem) ObjectUtilities.readObject(objBin);
                registerItem(item);
                if (loadImg) {
                    loadArtifactImg(item, true);
                }
            }
        } catch (Exception e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, "Find item failure!");
        }
        return item;
    }

    public void loadArtifactImg(ArtifactItem item, boolean loadThumbnailOnly) {
        FileInputStream fis = null;
        try {
            String type = item.getType();
            if (item.getThumbnail() == null) {
                fis = new FileInputStream(
                    new File(SDCardUtilities.getSdCardPath() + getItemFolder(type, item.getId()) + "thumb.jpeg"));
                Bitmap thumbnail = BitmapFactory.decodeStream(fis);
                item.setThumbnail(thumbnail);
            }
            if (!loadThumbnailOnly) {
                fis = new FileInputStream(
                    new File(SDCardUtilities.getSdCardPath() + getItemFolder(type, item.getId()) + "pic.jpeg"));
                Bitmap pic = BitmapFactory.decodeStream(fis);
                item.setPic(pic);
            }
        } catch (FileNotFoundException e) {
            Log.e(DataFactory.class.getName(), e.getMessage());
            MessageHandler
                .showWarningMessage(AppContext.context, "Can not find item:" + item.getType() + "." + item.getId());
        }
    }

    private String getItemFolder(String type, String itemId) {
        return getTypeFolder(type) + itemId + "/";
    }

    private String getCollocationPath(String id) {
        return getCollocationRoot() + id + "/";
    }

    private String getCollocationRoot() {
        return FILE_ROOT + "collocation/";
    }

    private String getTypeFolder(String type) {
        return FILE_ROOT + type + "/";
    }

    public List<ArtifactTypeModel> getTypes() {
        if (types.size() == 0) {
            String[] names =
                new String[]{"clothes", "tshirt", "sweater", "shirt", "dress", "pants", "accessory", "shoes", "others"};
            int[] resourceId =
                new int[]{
                    R.string.type1,
                    R.string.type2,
                    R.string.type3,
                    R.string.type4,
                    R.string.type5,
                    R.string.type6,
                    R.string.type7,
                    R.string.type8,
                    R.string.type9
                };
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
                typeModel.setResourceId(resourceId[i]);
                types.add(typeModel);
                typeMapper.put(names[i], typeModel);
                File folder = new File(SDCardUtilities.getSdCardPath() + getTypeFolder(names[i]));
                if (folder.exists()) {
                    File[] files = folder.listFiles();
                    if (files != null && files.length > 0) {
                        for (File file : files) {
                            if (file.isDirectory()) {
                                ArtifactItem item = getArtifactItem(names[i], file.getName(), false);
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

    private void registerItem(ArtifactItem item) {
        latitude1Mapper.put(item.getLatitude1(), item);
        latitude2Mapper.put(item.getLatitude2(), item);
    }

    public ArtifactTypeModel findType(String type) {
        return typeMapper.get(type);
    }
}
