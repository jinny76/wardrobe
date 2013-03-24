package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import jbolt.android.R;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ShareActivity extends WardrobeFrameActivity {

    ImageButton btnCancel;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.share);
        initTopButtons();

        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }
}
