package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.widget.Button;
import jbolt.android.R;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;

/**
 * <p>Title: PersonalCentreActivity</p>
 * <p>Description: PersonalCentreActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class PersonalCentreActivity extends WardrobeFrameActivity {

    private Button btnAdd;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.personalcentre);

//        btnAdd = (Button) findViewById(R.id.btnAdd);
//        initTopButtons();
//        initBottomButtons();
//        btnAdd.setVisibility(View.INVISIBLE);
    }
}