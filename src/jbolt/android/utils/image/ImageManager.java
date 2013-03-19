package jbolt.android.utils.image;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import jbolt.android.base.AppContext;
import jbolt.android.utils.HttpManager;
import jbolt.android.utils.MessageHandler;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ImageManager {

    private static ImageManager instance;

    private boolean lock;
    public static final int CAMERA_WITH_DATA = 3023;
    public static final int PHOTO_PICKED_WITH_DATA = 3021;
    public static final int STYLE_PHOTO = 1;
    public static final int STYLE_PORTRAIT = 2;

    public static ImageManager getInstance() {
        if (instance == null) {
            instance = new ImageManager();
        }

        return instance;
    }

    private ImageManager() {
    }

    public Bitmap extractMiniThumb(Bitmap source, int width, int height, boolean recycle) throws Exception {
        if (source == null) {
            return null;
        }
        float scale;
        if (source.getWidth() < source.getHeight()) {
            scale = width / (float) source.getWidth();
        } else {
            scale = height / (float) source.getHeight();
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap miniThumbnail = transform(matrix, source, width, height, true, recycle);
        return miniThumbnail;
    }


    public Bitmap transform(
            Matrix scaler, Bitmap source,
            int targetWidth, int targetHeight, boolean scaleUp, boolean recycle) throws Exception {
        int deltaX = source.getWidth() - targetWidth;
        int deltaY = source.getHeight() - targetHeight;
        if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
            /*
                * In this case the bitmap is smaller, at least in one dimension,
                * than the target. Transform it by placing as much of the image as
                * possible into the target and leaving the top/bottom or left/right
                * (or both) black.
                */
            Bitmap b2 = Bitmap.createBitmap(
                    targetWidth, targetHeight,
                    Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b2);
            int deltaXHalf = Math.max(0, deltaX / 2);
            int deltaYHalf = Math.max(0, deltaY / 2);
            Rect src = new Rect(
                    deltaXHalf, deltaYHalf, deltaXHalf
                    + Math.min(targetWidth, source.getWidth()), deltaYHalf
                    + Math.min(targetHeight, source.getHeight()));
            int dstX = (targetWidth - src.width()) / 2;
            int dstY = (targetHeight - src.height()) / 2;
            Rect dst = new Rect(
                    dstX, dstY, targetWidth - dstX, targetHeight
                    - dstY);
            c.drawBitmap(source, src, dst, null);
            if (recycle) {
                source.recycle();
            }
            return b2;
        }
        float bitmapWidthF = source.getWidth();
        float bitmapHeightF = source.getHeight();
        float bitmapAspect = bitmapWidthF / bitmapHeightF;
        float viewAspect = (float) targetWidth / targetHeight;
        if (bitmapAspect > viewAspect) {
            float scale = targetHeight / bitmapHeightF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        } else {
            float scale = targetWidth / bitmapWidthF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        }
        Bitmap b1;
        if (scaler != null) {
            // this is used for minithumb and crop, so we want to filter here.
            b1 = Bitmap.createBitmap(
                    source, 0, 0, source.getWidth(), source
                    .getHeight(), scaler, true);
        } else {
            b1 = source;
        }
        if (recycle && b1 != source) {
            source.recycle();
        }
        int dx1 = Math.max(0, b1.getWidth() - targetWidth);
        int dy1 = Math.max(0, b1.getHeight() - targetHeight);
        Bitmap b2 = Bitmap.createBitmap(
                b1, dx1 / 2, dy1 / 2, targetWidth,
                targetHeight);
        if (b2 != b1) {
            if (recycle || b1 != source) {
                b1.recycle();
            }
        }
        return b2;
    }

    public void lazyLoadImage(String imageUrl, String bigUrl, Map<String, String> params, final ImageView imgView) {
        try {
            ImageTag tag = new ImageTag(imageUrl, bigUrl);
            Drawable drawable = ImageCache.getInstance().get(imageUrl, params);
            if (drawable == null) {
                ImageTag oriTag = (ImageTag) imgView.getTag();
                if (oriTag != null && oriTag.getLazyLoadHandler() != null) {
                    oriTag.getLazyLoadHandler().unlinkView();
                }
                ImageLoadHandler handler = new ImageLoadHandler(imageUrl, params, imgView);
                tag.setLazyLoadHandler(handler);
                imgView.setTag(tag);
                HttpManager.getDrawableAsync(imageUrl, params, handler);
            } else {
                imgView.setTag(tag);
                imgView.setImageDrawable(drawable);
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    public void doTakePhoto() {
        try {
            if (!lock) {
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                AppContext.context.startActivityForResult(intent, CAMERA_WITH_DATA);
                lock = true;
            }
        } catch (ActivityNotFoundException e) {
            MessageHandler.showWarningMessage(
                    AppContext.context, "Picture is not found!");
        }
    }

    public void doPickPhotoFromGallery() {
        try {
            if (!lock) {
                final Intent intent = getPhotoPickIntent();
                AppContext.context.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
                lock = true;
            }
        } catch (ActivityNotFoundException e) {
            MessageHandler.showWarningMessage(AppContext.context, "Picture is not found!");
        }
    }

    public static Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
        return intent;
    }

    public void onReceiveResult(int resultCode, Intent data, File tempFile, ImageView imgReview, int style)
            throws Exception {
        lock = false;
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        Bitmap photo = data.getParcelableExtra("data");
        if (photo == null) {
            Uri selectedImage = data.getData();
            InputStream imageStream = AppContext.context.getContentResolver().openInputStream(selectedImage);
            photo = BitmapFactory.decodeStream(imageStream);
        }
        savePhoto(photo, tempFile, imgReview, style);
    }

    public Drawable getAndroidDrawable(String pDrawableName) {
        int resourceId = Resources.getSystem().getIdentifier(pDrawableName, "drawable", "android");
        if (resourceId == 0) {
            return null;
        } else {
            return Resources.getSystem().getDrawable(resourceId);
        }
    }

    public void savePhoto(Bitmap photo, File imgFile, ImageView imgReview, int style) throws Exception {
        if (photo != null) {
            if (photo.getWidth() > photo.getHeight()) {
                if (photo.getWidth() > 800) {
                    photo = ImageManager.getInstance().extractMiniThumb(photo, 800, 480, true);
                }
                saveBitmap(photo, imgFile);
                if (STYLE_PHOTO == style) {
                    imgReview.setImageBitmap(ImageManager.getInstance().extractMiniThumb(photo, 60, 45, true));
                } else if (STYLE_PORTRAIT == style) {
                    imgReview.setImageBitmap(ImageManager.getInstance().extractMiniThumb(photo, 48, 48, true));
                }
            } else {
                if (photo.getHeight() > 800) {
                    photo = ImageManager.getInstance().extractMiniThumb(photo, 480, 800, true);
                }
                saveBitmap(photo, imgFile);
                if (STYLE_PHOTO == style) {
                    imgReview.setImageBitmap(ImageManager.getInstance().extractMiniThumb(photo, 45, 60, true));
                } else if (STYLE_PORTRAIT == style) {
                    imgReview.setImageBitmap(ImageManager.getInstance().extractMiniThumb(photo, 48, 48, true));
                }
            }
        } else {
            MessageHandler.showWarningMessage(AppContext.context, "Picture is not found!");
        }
    }

    public void saveBitmap(Bitmap photo, File imgFile, File thumbnailFile) {
        saveBitmap(photo, imgFile);
        if (thumbnailFile != null) {
            Bitmap thumbnail = null;
            try {
                thumbnail = ImageManager.getInstance().extractMiniThumb(photo, 60, 80, false);
                saveBitmap(thumbnail, thumbnailFile);
            } catch (Exception e) {
                MessageHandler.showWarningMessage(AppContext.context, e);
            }
        }
    }

    public void saveBitmap(Bitmap photo, File imgFile) {
        FileOutputStream out = null;
        try {
            File file = new File(imgFile.getAbsolutePath());
            if (!file.exists()) {
                File dir = file.getParentFile();
                if (dir != null && !dir.exists()) {
                    dir.mkdirs();
                }
            }
            if (!imgFile.exists()) {
                imgFile.createNewFile();
            }
            out = new FileOutputStream(imgFile);
            photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            Log.i(ImageManager.class.getName(), "---------------Write image to:" + imgFile.getAbsolutePath());
        } catch (Exception e) {
            Log.i(ImageManager.class.getName(), "xxxxxxxxxxxxxxxxxxWrite image failure:" + imgFile.getAbsolutePath());
            Log.i(ImageManager.class.getName(), "xxxxxxxxxxxxxxxxxxWrite image failure:" + e.getMessage());
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    public void resetLock() {
        lock = false;
    }
}
