package jbolt.android.wardrobe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import jbolt.android.R;
import jbolt.android.utils.WidgetUtils;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;

/**
 * <p>Title: MessageActivity</p>
 * <p>Description: MessageActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class MessageActivity extends Activity {

    ImageButton btnOk;
    ImageButton btnCancel;
    ImageButton btnFace;
    ImageButton face;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        btnOk = (ImageButton) findViewById(R.id.btnOk);
        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnFace = (ImageButton) findViewById(R.id.btnFace);
        face = (ImageButton) findViewById(R.id.face);
        face.setVisibility(View.INVISIBLE);
        btnFace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                WidgetUtils.setWidgetVisible(face, !WidgetUtils.isWidgetVisible(face));
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(WardrobeFrameActivity.CANCEL_ADD, null);
                finish();
            }
        });
    }
}
