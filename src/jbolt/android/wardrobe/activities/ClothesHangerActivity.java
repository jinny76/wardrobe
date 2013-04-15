package jbolt.android.wardrobe.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import jbolt.android.R;
import jbolt.android.base.ClientHandler;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.WidgetUtils;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItem;
import jbolt.android.wardrobe.models.ArtifactTypeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    protected ImageButton btnShare;

    @Override
    protected void refreshAdapter() {
        loadItems(
            new ClientHandler() {
                @Override
                public void handleMsg(Object obj) {
                    ArtifactTypeModel typeModel = DataFactory.getSingle().findType(type);
                    final List<ArtifactItem> items = (List<ArtifactItem>) obj;
                    typeModel.setItems(items);
                    //DataFactory.getSingle().initThumbnail(type, true);
                    if (items.size() > 0) {
                        if (items.size() <= 1) {
                            index = 0;
                        }
                    } else {
                        index = -1;
                    }
                    refreshPic();
                }
            });
    }

    private void refreshPic() {
        WidgetUtils.setWidgetVisible(img1, index != -1);
        WidgetUtils.setWidgetVisible(img2, index != -1);
        WidgetUtils.setWidgetVisible(img3, index != -1);
        if (index != -1) {
            final ArtifactTypeModel typeModel = DataFactory.getSingle().findType(type);
            if (typeModel != null && typeModel.getItems() != null && typeModel.getItems().size() > 0) {
                List<ArtifactItem> items = typeModel.getItems();
                if (index > 0) {
                    ArtifactItem prevItem = items.get(index - 1);
                    ImageManager.getInstance().lazyLoadImage(ImageManager.getUrl(prevItem.getId(), true), null, new HashMap<String, String>(), img1);
                } else {
                    WidgetUtils.setWidgetVisible(img1, false);
                }
                ArtifactItem currItem = items.get(index);
                ImageManager.getInstance().lazyLoadImage(ImageManager.getUrl(currItem.getId(), true), null, new HashMap<String, String>(), img3);
                if (index < items.size() - 1) {
                    ArtifactItem nextItem = items.get(index + 1);
                    ImageManager.getInstance().lazyLoadImage(
                        ImageManager.getUrl(nextItem.getId(), true), null, new HashMap<String, String>(), img2);
                } else {
                    WidgetUtils.setWidgetVisible(img2, false);
                }
            }
        }
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.clotheshanger);
        Intent intent = getIntent();
        type = intent.getStringExtra(PARAM_KEY);
        btnTopAdd = (Button) findViewById(R.id.btnTopAdd);
        ImageButton btnDelete = (ImageButton) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    if (index != -1) {
                        MessageHandler.showOptionDialog(
                            ClothesHangerActivity.this, R.string.common_warning, R.string.msg_delete,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    List<ArtifactItem> items = new ArrayList<ArtifactItem>();
                                    loadItems(
                                        new ClientHandler() {
                                            @Override
                                            public void handleMsg(Object obj) {
                                            }
                                        });
                                    ArtifactItem item = items.get(index);
                                    DataFactory.getSingle().deleteItem(item);
                                    handler.sendMessageDelayed(handler.obtainMessage(1), 30);
                                }
                            }, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }
                        );
                    }
                }
            });
        img1 = (ImageView) findViewById(R.id.pic1);
        img2 = (ImageView) findViewById(R.id.pic2);
        img3 = (ImageView) findViewById(R.id.pic3);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();
        initBottomButtons();

        btnShare = (ImageButton) findViewById(R.id.btnShare);
        btnShare.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(ShareActivity.class, new HashMap());
                }
            });

        refreshAdapter();
        initMenuItems();
        findViewById(R.id.picFrame).setOnTouchListener(this);
        findViewById(R.id.pic3).setOnTouchListener(this);
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
                    List<ArtifactItem> items = DataFactory.getSingle().findType(type).getItems();
                    if (index > items.size() - 1) {
                        index = items.size() - 1;
                    } else if (index < 0) {
                        index = 0;
                    }
                    refreshPic();
                } else if (view == findViewById(R.id.pic3)) {
                    HashMap params = new HashMap();
                    if (index != -1) {
                        ArtifactItem item = DataFactory.getSingle().findType(type).getItems().get(index);
                        params.put("id", item.getId());
                        startActivity(ShowBigPicActivity.class, params);
                    }
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
