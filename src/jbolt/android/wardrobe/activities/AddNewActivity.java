package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import java.io.File;
import java.io.InputStream;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.Log;
import jbolt.android.utils.SDCardUtilities;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class AddNewActivity extends WardrobeFrameActivity {

    public static final String PARAM_CATALOG = "catalog";
    public static final String PARAM_CATEGORY1 = "category1";
    public static final String PARAM_CATEGORY2 = "category2";
    ImageButton btnAddFromCamera;
    ImageButton btnAddFromGallery;
    ImageButton btnCancel;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.addnew);

        btnAddFromCamera = (ImageButton) findViewById(R.id.btnAddFromCamera);
        btnAddFromCamera.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClickAction(View view) {
                        ImageManager.getInstance().doTakePhoto();
                    }
                });

        btnAddFromGallery = (ImageButton) findViewById(R.id.btnAddFromGallery);
        btnAddFromGallery.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClickAction(View view) {
                        ImageManager.getInstance().doPickPhotoFromGallery();
                    }
                });

        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClickAction(View view) {
                        setResult(CANCEL_ADD, null);
                        finish();
                    }
                });
    }

    @Override
    protected void onReceiveResult(int requestCode, int resultCode, Intent data) throws Exception {
        ImageManager.getInstance().resetLock();
        Bitmap pic = null;
        if (data != null) {
            pic = data.getParcelableExtra("data");
            if (pic == null || pic.getWidth() < 480) {
                try {
                    Uri selectedImage = data.getData();
                    InputStream imageStream = AppContext.context.getContentResolver().openInputStream(selectedImage);
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inSampleSize = 1;
                    pic = BitmapFactory.decodeStream(imageStream, null, opts);
                } catch (Exception e) {
                    Log.i(AddNewActivity.class.getName(), e.getMessage());
                }
            }
        } else {
            try {
                File tmpFile = new File(SDCardUtilities.getSdCardPath() + "/tmp.jpg");
                Uri selectedImage = Uri.fromFile(tmpFile);
                InputStream imageStream = AppContext.context.getContentResolver().openInputStream(selectedImage);
                tmpFile.delete();
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 1;
                pic = BitmapFactory.decodeStream(imageStream, null, opts);
            } catch (Exception e) {
                Log.i(AddNewActivity.class.getName(), e.getMessage());
            }
        }
        if (pic != null) {
            ImageManager.getInstance().saveBitmap(
                    pic,
                    new File(SDCardUtilities.getSdCardPath() + DataFactory.FILE_ROOT + "/tmp/pic.jpeg"),
                    new File(SDCardUtilities.getSdCardPath() + DataFactory.FILE_ROOT + "/tmp/thumbnail.jpeg"));

            setResult(ADD_NEW, null);
        } else {
            setResult(CANCEL_ADD, null);
        }
        finish();
    }
}
