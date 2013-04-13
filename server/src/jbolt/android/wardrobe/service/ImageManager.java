package jbolt.android.wardrobe.service;

import java.io.File;

/**
 * <p>Title: ImageManager</p>
 * <p>Description: ImageManager</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public interface ImageManager {

    /**
     * Save picture
     *
     * @param id        Picture id
     * @param imgFile   Image file
     * @param thumbnail Is thumbnail
     */
    void savePic(String id, File imgFile, boolean thumbnail);

    /**
     * Load picture file object
     *
     * @param id        Id
     * @param thumbnail Is thunbnail
     * @return File
     */
    File loadPic(String id, boolean thumbnail);

}
