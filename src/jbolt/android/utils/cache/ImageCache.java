package jbolt.android.utils.cache;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import jbolt.android.utils.SDCardUtilities;
import jbolt.android.utils.image.ImageManager;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ImageCache extends HashMap {

    private static ImageCache instance;
    public static final int MAX_OBJECTS = 200;
    public static final String CACHE_DIR = SDCardUtilities.getRootDir()
        + "/DCIM/abolt/cache/";

    private LRUCache<String, String> fileCache;

    public static ImageCache getInstance() {
        if (instance == null) {
            instance = new ImageCache();
            File cacheDir = new File(CACHE_DIR);
            if (cacheDir.exists()) {
                File[] imageFiles = cacheDir.listFiles();
                for (File file : imageFiles) {
                    file.delete();
                }
            }
        }

        return instance;
    }

    private ImageCache() {
        fileCache = new LRUCache<String, String>(MAX_OBJECTS);
        fileCache.addCacheListener(
            new CacheListener<String, String>() {
                public void onAddCache(String key, String value) {
                }

                public void onRemoveCache(String key, String value) {
                    File imageFile = new File(value);
                    imageFile.delete();
                }
            });
    }

    public Drawable get(String url, Map<String, String> params) throws Exception {
        String key = url;
        if (params != null) {
            if (!(params instanceof TreeMap)) {
                params = new TreeMap<String, String>(params);
            }

            key += "|" + params.toString();
        }
        String filePath = fileCache.get(key);
        Object imageRef = super.get(key);
        if (imageRef instanceof SoftReference) {
            Drawable drawable = (Drawable) ((SoftReference) imageRef).get();
            if (drawable == null && filePath != null) {
                drawable = Drawable.createFromPath(filePath);
                return drawable;
            }
            return drawable;
        }

        return null;
    }

    public Drawable put(String url, Map<String, String> params, Drawable value) throws Exception {
        String key = url;
        if (params != null) {
            if (!(params instanceof TreeMap)) {
                params = new TreeMap<String, String>(params);
            }

            key += "|" + params.toString();
        }
        if (!containsKey(key)) {
            SoftReference<Drawable> image = new SoftReference<Drawable>(value);
            super.put(key, image);
            String extName = key.substring(key.lastIndexOf(".") + 1);
            final String filePath = CACHE_DIR + UUID.randomUUID().toString()
                + "."
                + extName;
            File imageFile = new File(filePath);
            imageFile.getParentFile().mkdirs();
            ImageManager.getInstance().saveBitmap(((BitmapDrawable) value).getBitmap(), imageFile);
            fileCache.put(key, filePath);
        }
        return value;
    }
}
