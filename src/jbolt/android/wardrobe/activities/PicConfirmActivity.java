package jbolt.android.wardrobe.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jbolt.android.R;
import jbolt.android.base.BaseHandler;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.SDCardUtilities;
import jbolt.android.utils.StringUtilities;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItem;
import jbolt.android.wardrobe.models.ArtifactTypeModel;

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
    private EditText txtContent;
    private String defaultType;
    private String defaultLatitude1;
    private String defaultLatitude2;
    private Spinner cboLatitude1;
    private Spinner cboLatitude2;
    private Spinner cboType;

    private ArrayAdapter latitude2Adapter;
    private List<String> typesSet = new ArrayList<String>();

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.pic_confirm);

        imgView = (ImageView) findViewById(R.id.imgView);
        txtContent = (EditText) findViewById(R.id.txtContent);
        txtContent.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_DONE || (
                                keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(txtContent.getWindowToken(), 0);
                            return true;
                        }
                        return false;
                    }
                });


        File thumbnail = new File(SDCardUtilities.getSdCardPath() + DataFactory.FILE_ROOT + "/tmp/thumbnail.jpeg");
        if (thumbnail.exists()) {
            FileInputStream fis = new FileInputStream(thumbnail);
            Bitmap thumbnailPic = BitmapFactory.decodeStream(fis);
            imgView.setImageBitmap(thumbnailPic);
        }
        btnOK = (ImageButton) findViewById(R.id.btnOk);
        btnOK.setOnClickListener(
                new OnClickListener() {

                    public void onClickAction(View view) {
                        confirm();
                    }
                });

        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
                new OnClickListener() {

                    public void onClickAction(View view) {
                        finish();
                    }
                });
        initLatitudeSpinner();
    }

    private void initLatitudeSpinner() {
        Resources resources = getResources();

        HashMap addParams = (HashMap) params;
        defaultType = (String) addParams.get(AddNewActivity.PARAM_CATALOG);
        defaultLatitude1 = (String) addParams.get(AddNewActivity.PARAM_CATEGORY1);
        defaultLatitude2 = (String) addParams.get(AddNewActivity.PARAM_CATEGORY2);

        if (StringUtilities.isEmpty(defaultType)) {
            defaultType = "clothes";
        }
        if (StringUtilities.isEmpty(defaultLatitude1)) {
            defaultLatitude1 = resources.getString(R.string.latitude_menu1_left);
        }

        ArrayAdapter<String> latitude1Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        latitude1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        latitude1Adapter.add(resources.getString(R.string.latitude_menu1_left));
        latitude1Adapter.add(resources.getString(R.string.latitude_menu2_left));
        latitude1Adapter.add(resources.getString(R.string.latitude_menu3_left));
        cboLatitude1 = (Spinner) findViewById(R.id.cboLatitude1);
        cboLatitude1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Spinner spinner = (Spinner) adapterView;
                        defaultLatitude1 = spinner.getSelectedItem().toString();
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
        cboLatitude1.setAdapter(latitude1Adapter);

        cboLatitude2 = (Spinner) findViewById(R.id.cboLatitude2);
        cboLatitude2.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Spinner spinner = (Spinner) adapterView;
                        defaultLatitude2 = spinner.getSelectedItem().toString();
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
        latitude2Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        latitude2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        latitude2Adapter.add(resources.getString(R.string.latitude_menu1_right));
        latitude2Adapter.add(resources.getString(R.string.latitude_menu2_right));
        cboLatitude2.setAdapter(latitude2Adapter);

        cboType = (Spinner) findViewById(R.id.cboType);
        cboType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Spinner spinner = (Spinner) adapterView;
                        defaultType = DataFactory.getSingle().getTypes().get(spinner.getSelectedItemPosition()).getId();
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.add(resources.getString(R.string.type1));
        typeAdapter.add(resources.getString(R.string.type2));
        typeAdapter.add(resources.getString(R.string.type3));
        typeAdapter.add(resources.getString(R.string.type4));
        typeAdapter.add(resources.getString(R.string.type5));
        typeAdapter.add(resources.getString(R.string.type6));
        typeAdapter.add(resources.getString(R.string.type7));
        typeAdapter.add(resources.getString(R.string.type8));
        typeAdapter.add(resources.getString(R.string.type9));
        cboType.setAdapter(typeAdapter);
        refreshSpinners();
    }

    private void refreshSpinners() {
        List<ArtifactTypeModel> typeModels = DataFactory.getSingle().getTypes();
        int index = 0;
        for (ArtifactTypeModel typeModel : typeModels) {
            if (defaultType.equals(typeModel.getId())) {
                break;
            }
            index++;
        }
        cboType.setSelection(index);


        Resources resources = getResources();
        index = 2;
        if (resources.getString(R.string.latitude_menu1_left).equals(defaultLatitude1)) {
            index = 0;
        } else if (resources.getString(R.string.latitude_menu2_left).equals(defaultLatitude1)) {
            index = 1;
        }
        cboLatitude1.setSelection(index);

        index = 0;
        if (resources.getString(R.string.latitude_menu2_right).equals(defaultLatitude2)) {
            index = 1;
        }
        cboLatitude2.setSelection(index);
    }

    private void confirm() {
        ArtifactItem item = new ArtifactItem();
        item.setLatitude1(defaultLatitude1);
        item.setLatitude2(defaultLatitude2);
        item.setDescription(txtContent.getText().toString());
        DataFactory.getSingle().addArtifactItem(
                item, defaultType, null, new BaseHandler() {
            @Override
            protected void handleMsg(Message msg) throws Exception {
                File picFile = new File(SDCardUtilities.getSdCardPath() + DataFactory.getSingle().getItemFolder(defaultType, "tmp") + "pic.jpeg");
                File thumbnailFile = new File(SDCardUtilities.getSdCardPath() + DataFactory.getSingle().getItemFolder(defaultType, "tmp") + "thumb.jpeg");
                if (picFile.exists()) {
                    picFile.delete();
                }
                if (thumbnailFile.exists()) {
                    thumbnailFile.delete();
                }
                finish();
            }
        });

    }


}