package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class AddNewActivity extends WardrobeFrameActivity {

    ImageButton btnAddFromCamera;
    ImageButton btnAddFromGallery;
    ImageButton btnCancel;


    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.addnew);

        btnAddFromCamera = (ImageButton) findViewById(R.id.btnAddFromCamera);
        btnAddFromCamera.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageManager.getInstance().doTakePhoto();
                }
            });

        btnAddFromGallery = (ImageButton) findViewById(R.id.btnAddFromGallery);
        btnAddFromGallery.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageManager.getInstance().doPickPhotoFromGallery();
                }
            });

        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
    }

    @Override
    protected void onReceiveResult(int requestCode, int resultCode, Intent data) throws Exception {
        ImageManager.getInstance().resetLock();
        Bitmap pic = data.getParcelableExtra("data");
        if (pic == null) {
            Uri selectedImage = data.getData();
            InputStream imageStream = AppContext.context.getContentResolver().openInputStream(selectedImage);
            pic = BitmapFactory.decodeStream(imageStream);
        }
        ArrayList<Parcelable> pics = new ArrayList<Parcelable>();
        pics.add(pic);
        Intent result = new Intent();

        result.putParcelableArrayListExtra(RESULT_PIC, pics);
        setResult(ADD_NEW, result);
        finish();
    }
}
