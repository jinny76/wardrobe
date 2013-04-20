package jbolt.android.wardrobe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import jbolt.android.R;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;

/**
 * <p>Title: OffenceReportActivity</p>
 * <p>Description: OffenceReportActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class OffenceReportActivity extends Activity {

    ImageButton btnOk;
    ImageButton btnCancel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments);
        btnOk = (ImageButton) findViewById(R.id.btnOk);
        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(WardrobeFrameActivity.CANCEL_ADD, null);
                finish();
            }
        });
    }
}