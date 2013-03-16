package jbolt.android.wardrobe.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import jbolt.android.R;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItemModel;
import jbolt.android.wardrobe.models.ArtifactTypeModel;

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

    private RelativeLayout pnlItemsList;
    private ImageView imgPuzzle;

    private Button btnArrowUp;
    private Button btnArrowDown;
    private Button btnArrowLeft;
    private Button btnArrowRight;
    private HorizontalScrollView pnlTypes;
    private ScrollView pnlItems;

    private int counter = 1;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.collocationroom);
        pnlItemsList = (RelativeLayout) findViewById(R.id.pnlItemsList);
        imgPuzzle = (ImageView) findViewById(R.id.imgPuzzle);

        RelativeLayout pnlContent = (RelativeLayout) findViewById(R.id.pnlContent);

        Resources resources = getResources();
        List<ArtifactTypeModel> types = DataFactory.getSingle().getTypes();

        Button lastButton = null;
        for (final ArtifactTypeModel type : types) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
            Button btnType = new Button(this);
            btnType.setId(counter++);
            btnType.setBackgroundDrawable(resources.getDrawable(type.getDrawableId()));
            btnType.setTag(type);
            btnType.setText("");
            btnType.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        refreshItems(type);
                    }
                });
            btnType.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            if (lastButton != null) {
                layoutParams.addRule(RelativeLayout.RIGHT_OF, lastButton.getId());
            }
            pnlContent.addView(btnType, layoutParams);
            lastButton = btnType;
        }

        pnlTypes = (HorizontalScrollView) findViewById(R.id.pnlTypes);
        btnArrowLeft = (Button) findViewById(R.id.btnArrowLeft);
        btnArrowLeft.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    pnlTypes.scrollTo(pnlTypes.getScrollX() - 110, pnlTypes.getScrollY());
                }
            });

        btnArrowRight = (Button) findViewById(R.id.btnArrowRight);
        btnArrowRight.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    pnlTypes.scrollTo(pnlTypes.getScrollX() + 110, pnlTypes.getScrollY());
                }
            });

        pnlItems = (ScrollView) findViewById(R.id.pnlItems);
        btnArrowUp = (Button) findViewById(R.id.btnArrowUp);
        btnArrowUp.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    pnlItems.scrollTo(pnlItems.getScrollX(), pnlItems.getScrollY() - 220);
                }
            });

        btnArrowDown = (Button) findViewById(R.id.btnArrowDown);
        btnArrowDown.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    pnlItems.scrollTo(pnlItems.getScrollX(), pnlItems.getScrollY() + 220);
                }
            });

        refreshItems(types.get(0));
    }

    public void refreshItems(ArtifactTypeModel type) {
        Resources resources = getResources();
        List<ArtifactItemModel> items = type.getItems();
        imgPuzzle.setImageDrawable(resources.getDrawable(type.getPuzzleDrawableId()));

        ImageView lastImg = null;
        pnlItemsList.removeAllViews();
        for (final ArtifactItemModel item : items) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
            ImageView imgItem = new ImageView(this);
            imgItem.setId(counter++);
            imgItem.setBackgroundDrawable(resources.getDrawable(item.getDrawable()));
            imgItem.setTag(item);
            if (lastImg != null) {
                layoutParams.addRule(RelativeLayout.BELOW, lastImg.getId());
            }
            pnlItemsList.addView(imgItem, layoutParams);
            lastImg = imgItem;
        }

    }

    @Override
    protected void initSpecialTopButtons() {
        super.initSpecialTopButtons();
    }
}