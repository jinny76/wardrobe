package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import jbolt.android.R;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.Log;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;

import java.util.HashMap;

/**
 * <p>Title: ShowBigPicActivity</p>
 * <p>Description: ShowBigPicActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ShowBigPicActivity extends WardrobeFrameActivity {

    private ImageView imgView;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.bigpic);
        imgView = (ImageView) findViewById(R.id.bigpic);
        try {
            Intent intent = getIntent();
            HashMap params = (HashMap) intent.getSerializableExtra(PARAM_KEY);
            String itemId = (String) params.get("id");
            ImageManager.getInstance().lazyLoadImage(ImageManager.getUrl(itemId, false), null, new HashMap<String, String>(), imgView);
            ImageButton btnClose = (ImageButton) findViewById(R.id.btnClose);
            btnClose.setOnClickListener(
                    new OnClickListener() {
                        public void onClickAction(View view) {
                            finish();
                        }
                    });
        } catch (Exception e) {
            Log.e(ShowBigPicActivity.class.getName(), e.getMessage());
        }
    }
}
