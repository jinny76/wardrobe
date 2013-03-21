package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.util.HashMap;
import jbolt.android.R;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItemModel;

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
        Intent intent = getIntent();
        HashMap params = (HashMap) intent.getSerializableExtra(PARAM_KEY);
        String type = (String) params.get("type");
        String itemId = (String) params.get("id");
        ArtifactItemModel itemModel = DataFactory.getSingle().getArtifactItem(type, itemId, true);
        DataFactory.getSingle().loadArtifactImg(itemModel, false);
        imgView.setImageBitmap(itemModel.getPic());

        ImageButton btnClose = (ImageButton) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }
}
