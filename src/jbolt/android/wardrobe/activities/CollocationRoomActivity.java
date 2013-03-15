package jbolt.android.wardrobe.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import jbolt.android.R;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItemModel;
import jbolt.android.wardrobe.models.ArtifactTypeModel;
import jbolt.android.widget.ToggleButton;
import jbolt.android.widget.ToggleButtonGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: CollocationRoomActivity</p>
 * <p>Description: CollocationRoomActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class CollocationRoomActivity extends WardrobeFrameActivity {

    private LinearLayout itemList;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.collocationroom);
        itemList = (LinearLayout) findViewById(R.id.itemsList);
        LinearLayout linearLayoutList = (LinearLayout) findViewById(R.id.linearLayoutList);
        linearLayoutList.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams bottomLinerLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1);
        bottomLinerLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        Resources resources = getResources();
        ToggleButtonGroup group = new ToggleButtonGroup();
        List<ToggleButton> buttons = new ArrayList<ToggleButton>();
        List<ArtifactTypeModel> types = DataFactory.getSingle().getTypes();

        for (final ArtifactTypeModel type : types) {
            ToggleButton<ArtifactTypeModel> btn = new ToggleButton<ArtifactTypeModel>(this);
            btn.setBackgroundDrawable(resources.getDrawable(type.getDrawableId()));
            btn.setUserObj(type);
            btn.setText("");
            btn.setTextOn("");
            btn.setTextOff("");
            btn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        refreshItems(type);
                    }
                });
            btn.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            linearLayoutList.addView(btn, bottomLinerLayoutParams);
            buttons.add(btn);
        }
        group.setButtons(buttons);
        group.initButtons();

    }

    public void refreshItems(ArtifactTypeModel type) {
        LinearLayout.LayoutParams bottomLinerLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1);
        bottomLinerLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        for (ArtifactItemModel item : type.getItems()) {
//             itemList.
        }
    }

    @Override
    protected void initSpecialTopButtons() {
        super.initSpecialTopButtons();
    }
}