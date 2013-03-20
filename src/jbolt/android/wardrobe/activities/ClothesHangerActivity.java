package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import jbolt.android.R;
import jbolt.android.wardrobe.models.ArtifactTypeModel;

/**
 * <p>Title: ClothesHangerActivity</p>
 * <p>Description: ClothesHangerActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ClothesHangerActivity extends ClothesCatalogAbstractActivity {

    @Override
    protected void refreshAdapter() {
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.clotheshanger);
        setContentView(R.layout.clothescatalog);
        Intent intent = getIntent();
        typeModel = (ArtifactTypeModel) intent.getSerializableExtra(PARAM_KEY);
        btnTopAdd = (Button) findViewById(R.id.btnTopAdd);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();
        initBottomButtons();

        refreshAdapter();
        initMenuItems();
    }
}
