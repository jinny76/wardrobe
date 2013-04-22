package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import jbolt.android.R;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;

/**
 * <p>Title: PersonalInfoActivity</p>
 * <p>Description: PersonalInfoActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class PersonalInfoActivity extends WardrobeFrameActivity {

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.personal_info);
    }
}
