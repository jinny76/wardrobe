package jbolt.android.wardrobe.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import jbolt.android.R;
import jbolt.android.utils.SDCardUtilities;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItemModel;

/**
 * <p>Title: PicConfirmActivity</p>
 * <p>Description: PicConfirmActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class PicConfirmActivity extends WardrobeFrameActivity {

    private ImageButton btnCancel;
    private ImageButton btnOK;
    private ImageView imgView;
    private String defaultType;
    private String defaultLatitude1;
    private String defaultLatitude2;
    private List<String> typesSet = new ArrayList<String>();

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.pic_confirm);

        imgView = (ImageView) findViewById(R.id.imgView);
        File thumbnail = new File(SDCardUtilities.getSdCardPath() + DataFactory.FILE_ROOT + "/tmp/thumbnail.jpeg");
        if (thumbnail.exists()) {
            FileInputStream fis = new FileInputStream(thumbnail);
            Bitmap thumbnailPic = BitmapFactory.decodeStream(fis);
            imgView.setImageBitmap(thumbnailPic);
        }
        btnOK = (ImageButton) findViewById(R.id.btnOk);
        btnOK.setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View view) {
                        confirm();
                    }
                });

        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
                new View.OnClickListener() {

                    public void onClick(View view) {
                        finish();
                    }
                });
        initLatitudeSpinner();
    }

    private void initLatitudeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Resources resources = getResources();
        defaultType = "clothes";
        defaultLatitude1 = resources.getString(R.string.latitude_menu1_left);
        defaultLatitude2 = resources.getString(R.string.latitude_menu1_right);

        //latitude 1
        adapter.add(resources.getString(R.string.latitude_menu1_left));
        adapter.add(resources.getString(R.string.latitude_menu2_left));
        Spinner spinner = (Spinner) findViewById(R.id.cboLatitude1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner) adapterView;
                defaultLatitude1 = spinner.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.cboLatitude2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner) adapterView;
                defaultLatitude2 = spinner.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add(resources.getString(R.string.latitude_menu1_right));
        adapter.add(resources.getString(R.string.latitude_menu2_right));
        adapter.add(resources.getString(R.string.latitude_menu3_right));
        adapter.add(resources.getString(R.string.latitude_menu4_right));
        adapter.add(resources.getString(R.string.latitude_menu5_right));
        spinner.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.cboType);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner) adapterView;
                defaultType = DataFactory.getSingle().getTypes().get(spinner.getSelectedItemPosition()).getId();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add(resources.getString(R.string.type1));
        adapter.add(resources.getString(R.string.type2));
        adapter.add(resources.getString(R.string.type3));
        adapter.add(resources.getString(R.string.type4));
        adapter.add(resources.getString(R.string.type5));
        adapter.add(resources.getString(R.string.type6));
        adapter.add(resources.getString(R.string.type7));
        adapter.add(resources.getString(R.string.type8));
        adapter.add(resources.getString(R.string.type9));
        spinner.setAdapter(adapter);
    }

    private void confirm() {
        ArtifactItemModel itemModel = new ArtifactItemModel();
        itemModel.setLatitude1(defaultLatitude1);
        itemModel.setLatitude2(defaultLatitude2);
        DataFactory.getSingle().addArtifactItem(itemModel, defaultType, null);
        finish();
    }


}