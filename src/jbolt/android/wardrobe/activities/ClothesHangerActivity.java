package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.util.List;
import jbolt.android.R;
import jbolt.android.utils.WidgetUtils;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItemModel;

/**
 * <p>Title: ClothesHangerActivity</p>
 * <p>Description: ClothesHangerActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ClothesHangerActivity extends ClothesCatalogAbstractActivity implements View.OnTouchListener {

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private int index = 1;
    private int downX;
    private int upX;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    List<ArtifactItemModel> items = DataFactory.getSingle().findType(type).getItems();
                    if (index > items.size() - 1) {
                        index = items.size() - 1;
                    } else if (index < 0) {
                        index = 0;
                    }
                    refreshPic();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void refreshAdapter() {
        List<ArtifactItemModel> items = DataFactory.getSingle().findType(type).getItems();
        DataFactory.getSingle().initThumbnail(type, true);
        if (items.size() > 0) {
            if (items.size() <= 1) {
                index = 0;
            } else {
                index = 1;
            }
        } else {
            index = -1;
        }
        refreshPic();
    }

    private void refreshPic() {
        WidgetUtils.setWidgetVisible(img1, index != -1);
        WidgetUtils.setWidgetVisible(img2, index != -1);
        WidgetUtils.setWidgetVisible(img3, index != -1);
        if (index != -1) {
            List<ArtifactItemModel> items = DataFactory.getSingle().findType(type).getItems();
            if (index > 0) {
                img1.setImageBitmap(items.get(index - 1).getThumbnail());
            }
            img3.setImageBitmap(items.get(index).getThumbnail());
            if (index < items.size() - 1) {
                img2.setImageBitmap(items.get(index + 1).getThumbnail());
            }
        }
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.clotheshanger);
        Intent intent = getIntent();
        type = intent.getStringExtra(PARAM_KEY);
        btnTopAdd = (Button) findViewById(R.id.btnTopAdd);
        img1 = (ImageView) findViewById(R.id.pic1);
        img2 = (ImageView) findViewById(R.id.pic2);
        img3 = (ImageView) findViewById(R.id.pic3);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();
        initBottomButtons();

        refreshAdapter();
        initMenuItems();
        findViewById(R.id.picFrame).setOnTouchListener(this);
    }

    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE://滑动
                break;
            case MotionEvent.ACTION_DOWN://按下
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP://松开
                upX = (int) event.getX();
                if (Math.abs(upX - downX) > 20) {
                    if (upX > downX) {
                        index--;
                    } else {
                        index++;
                    }
                    handler.sendMessageDelayed(handler.obtainMessage(1), 30);
                }
                upX = -1;
                downX = -1;
                break;
            default:
                break;
        }
        return true;
    }
}
