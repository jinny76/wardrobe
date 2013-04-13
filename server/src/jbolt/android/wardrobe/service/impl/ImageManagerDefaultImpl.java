package jbolt.android.wardrobe.service.impl;

import java.io.File;
import jbolt.android.wardrobe.service.ImageManager;
import jbolt.core.utilities.FileUtilities;

/**
 * <p>Title: ImageManagerDefaultImpl</p>
 * <p>Description: ImageManagerDefaultImpl</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ImageManagerDefaultImpl implements ImageManager {

    private final static String REPO_DIR = System.getProperty("user.home") + File.separator + "jbolt" + File.separator + "wardrobe";

    public void savePic(String id, File imgFile, boolean thumbnail) {
        String save2Path = REPO_DIR + File.separator + id + File.separator + "img" + (thumbnail ? "_small" : "") + ".jpg";
        FileUtilities.copyFile(imgFile.getAbsolutePath(), save2Path);
    }

    public File loadPic(String id, boolean thumbnail) {
        String path = REPO_DIR + File.separator + id + File.separator + "img" + (thumbnail ? "_small" : "") + ".jpg";
        return new File(path);
    }
}
