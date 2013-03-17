package jbolt.android.wardrobe.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import jbolt.android.R;
import jbolt.android.utils.MessageHandler;
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

    private Button btnTopShow;
    private Button btnTopSave;

    private RelativeLayout pnlItemsList;
    private ImageView imgPuzzle;
    private ImageView imgIntro;
    private EditText txtIntro;

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

        btnTopSave = (Button) findViewById(R.id.btnTopSave);
        btnTopShow = (Button) findViewById(R.id.btnTopShow);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();

        pnlItemsList = (RelativeLayout) findViewById(R.id.pnlItemsList);
        imgPuzzle = (ImageView) findViewById(R.id.imgPuzzle);

        RadioGroup pnlContent = (RadioGroup) findViewById(R.id.pnlContent);

        Resources resources = getResources();
        List<ArtifactTypeModel> types = DataFactory.getSingle().getTypes();

        for (final ArtifactTypeModel type : types) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
            RadioButton btnType = new RadioButton(this);
            btnType.setId(counter++);
            btnType.setButtonDrawable(resources.getDrawable(type.getDrawableId()));
            btnType.setWidth(120);
            btnType.setTag(type);
            btnType.setText("");
            btnType.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        refreshItems(type);
                    }
                });
            btnType.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            pnlContent.addView(btnType, layoutParams);
            if (pnlContent.getChildCount() == 1) {
                btnType.setChecked(true);
            }
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

        imgIntro = (ImageView) findViewById(R.id.imgIntro);
        txtIntro = (EditText) findViewById(R.id.txtIntro);
        imgIntro.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    imgIntro.setVisibility(LinearLayout.INVISIBLE);
                    txtIntro.setVisibility(LinearLayout.VISIBLE);
                    txtIntro.requestFocus();
                }
            });
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
        btnTopSave.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    doSave();
                }
            });

        btnTopShow.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    doShow();
                }
            });

    }

    private void doShow() {
        MessageHandler.showWarningMessage(this, "Do Show");
    }

    private void doSave() {
        MessageHandler.showWarningMessage(this, "Do Save");
    }
}