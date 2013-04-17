package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.utils.HttpManager;
import jbolt.android.utils.Log;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.image.ImageCache;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItem;
import jbolt.android.wardrobe.models.ArtifactTypeModel;
import jbolt.android.wardrobe.models.Collocation;
import jbolt.android.wardrobe.models.TemplateModel;
import jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl;
import jbolt.android.widget.ToggleButton;
import jbolt.android.widget.ToggleButtonGroup;
import jbolt.android.widget.TouchPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Title: CollocationRoomActivity</p>
 * <p>Description: CollocationRoomActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class CollocationRoomActivity extends WardrobeFrameActivity implements GestureDetector.OnGestureListener {

    private Button btnTopShow;
    private Button btnTopSave;

    private RelativeLayout pnlItemsList;
    private ImageView imgIntro;
    private EditText txtIntro;

    private Button btnArrowUp;
    private Button btnArrowDown;
    private Button btnArrowLeft;
    private Button btnArrowRight;
    private Button btnAddPic;
    private HorizontalScrollView pnlTypes;
    private ScrollView pnlItems;

    private int counter = 1;

    private TouchPane pnlPuzzle;
    private LinearLayout pnlTemplate1;
    private LinearLayout pnlTemplate2;

    private ArrayList<Template> templates;
    private Template selectedTemplate;

    private GestureDetector gestureDetector;

    private Boolean forUpdate = false;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.collocationroom);

        btnTopSave = (Button) findViewById(R.id.btnTopSave);
        btnTopShow = (Button) findViewById(R.id.btnTopShow);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();

        pnlItemsList = (RelativeLayout) findViewById(R.id.pnlItemsList);

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
            btnType.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showItems(type);
                    }
                });
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

        btnAddPic = (Button) findViewById(R.id.btnAddPic);
        btnAddPic.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addNew();
                }
            });

        imgIntro = (ImageView) findViewById(R.id.imgIntro);
        txtIntro = (EditText) findViewById(R.id.txtIntro);
        txtIntro.setOnEditorActionListener(
            new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE || (
                        keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                            keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        selectedTemplate.templateModel.setDescription(txtIntro.getText().toString());
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(txtIntro.getWindowToken(), 0);
                        return true;
                    }
                    return false;
                }
            });
        imgIntro.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    imgIntro.setVisibility(LinearLayout.INVISIBLE);
                    txtIntro.setVisibility(LinearLayout.VISIBLE);
                    txtIntro.requestFocus();
                }
            });

        pnlPuzzle = (TouchPane) findViewById(R.id.pnlPuzzle);

        pnlTemplate1 = (LinearLayout) findViewById(R.id.pnlTemplate1);
        pnlTemplate2 = (LinearLayout) findViewById(R.id.pnlTemplate2);

        setupTemplates();

        gestureDetector = new GestureDetector(this, this);

        pnlPuzzle.setGestureDetector(gestureDetector);

        HashMap mapParams = (HashMap) params;
        String id = (String) mapParams.get("id");

        if (id != null) {
            forUpdate = true;
            doLoadCollocation(id);
        }
    }

    private void doLoadCollocation(String id) {

        CollocationManagerDefaultImpl.findById(
            id, new BaseHandler() {
            @Override
            protected void handleMsg(Message msg) throws Exception {
                if (msg.obj instanceof Collocation) {
                    selectedTemplate.collocationModel = (Collocation) msg.obj;

                    Template template = templates.get(0);
                    if ("Template2".equals(selectedTemplate.collocationModel.getTemplateId())) {
                        switchTemplate();
                        template = templates.get(1);
                    }

                    int index = 0;
                    final List<ToggleButton> buttons = template.toggleButtonGroup.getButtons();
                    for (final ArtifactItem artifactItem : selectedTemplate.collocationModel.getItems()) {
                        final int currIndex = index;
                        HttpManager.getDrawableAsync(
                            ImageManager.getUrl(artifactItem.getId(), true), new HashMap<String, String>(), new BaseHandler() {
                            @Override
                            protected void handleMsg(Message msg) throws Exception {
                                buttons.get(currIndex).setBackgroundDrawable(
                                    ImageCache.getInstance().get(ImageManager.getUrl(artifactItem.getId(), true), new HashMap<String, String>()));
                            }
                        });
                        index++;
                    }
                } else {
                    MessageHandler.showWarningMessage(AppContext.context, (String) msg.obj);
                }
            }
        });
    }

    @Override
    protected void onReceiveResult(int requestCode, int resultCode, Intent data) throws Exception {
        if (resultCode != CANCEL_ADD) {
            if (requestCode == ADD_NEW) {
                HashMap params = new HashMap();
                params.put(AddNewActivity.PARAM_CATALOG, DataFactory.getSingle().getTypes().get(8).getId());
                startActivity(PicConfirmActivity.class, params, CONFIRM_ADD_NEW);
            }
        }
    }

    private void setupTemplates() {
        templates = new ArrayList<Template>();
        for (int i = 0; i < 2; i++) {
            Template template = new Template();
            switch (i) {
                case 0:
                    template.templateModel = new TemplateModel();
                    template.templateModel.setId("Template1");
                    template.templateModel.setDescription("Template1");
                    template.toggleButtonGroup = new ToggleButtonGroup(
                        new ToggleButton[]{
                            (ToggleButton) findViewById(R.id.btnT11),
                            (ToggleButton) findViewById(R.id.btnT12),
                            (ToggleButton) findViewById(R.id.btnT13),
                            (ToggleButton) findViewById(R.id.btnT14)
                        });
                    break;
                case 1:
                    template.templateModel = new TemplateModel();
                    template.templateModel.setId("Template2");
                    template.templateModel.setDescription("Template2");
                    template.toggleButtonGroup = new ToggleButtonGroup(
                        new ToggleButton[]{
                            (ToggleButton) findViewById(R.id.btnT21),
                            (ToggleButton) findViewById(R.id.btnT22),
                            (ToggleButton) findViewById(R.id.btnT23),
                            (ToggleButton) findViewById(R.id.btnT24)
                        });
                    break;
            }
            template.collocationModel = new Collocation();
            template.collocationModel.setTemplateId(template.templateModel.getId());
            for (int j = 0; j < 4; j++) {
                template.collocationModel.getItems().add(new ArtifactItem());
            }
            templates.add(template);
        }
        selectedTemplate = templates.get(0);
    }

    private void showItems(ArtifactTypeModel type) {
        refreshItems(type);
    }

    public void refreshItems(final ArtifactTypeModel type) {
        DataFactory.getSingle().loadArtifactItems(
            type.getId(), new BaseHandler() {
            @Override
            protected void handleMsg(Message msg) throws Exception {
                if (msg.obj instanceof List) {
                    ImageView lastImg = null;
                    pnlItemsList.removeAllViews();
                    for (final ArtifactItem item : ((List<ArtifactItem>) msg.obj)) {
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(90, 120);
                        ImageView imgItem = new ImageView(CollocationRoomActivity.this);
                        imgItem.setId(counter++);
                        ImageManager.getInstance()
                            .lazyLoadImage(ImageManager.getUrl(item.getId(), true), null, new HashMap<String, String>(), imgItem);
                        imgItem.setTag(item);
                        if (lastImg != null) {
                            layoutParams.addRule(RelativeLayout.BELOW, lastImg.getId());
                        }
                        layoutParams.setMargins(10, 10, 10, 10);
                        imgItem.setClickable(true);
                        imgItem.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    addItemToTemplate(item);
                                }
                            });
                        pnlItemsList.addView(imgItem, layoutParams);
                        lastImg = imgItem;
                    }
                }
            }
        });
    }

    private void addItemToTemplate(ArtifactItem item) {
        Button selectedButton = null;
        int index = 0;
        for (ToggleButton currBtn : selectedTemplate.toggleButtonGroup.getButtons()) {
            if (currBtn.isChecked()) {
                selectedButton = currBtn;
                break;
            }
            index++;
        }
        if (selectedButton != null) {
            selectedTemplate.collocationModel.getItems().set(index, item);
            try {
                selectedButton
                    .setBackgroundDrawable(ImageCache.getInstance().get(ImageManager.getUrl(item.getId(), true), new HashMap<String, String>()));
            } catch (Exception e) {
                Log.e(this.getClass().getName(), e.getMessage());
            }
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
        if (selectedTemplate != null && selectedTemplate.collocationModel != null) {
            if (selectedTemplate.collocationModel.getId() != null) {
                CollocationManagerDefaultImpl.showCollocation(
                    selectedTemplate.collocationModel.getId(), new BaseHandler() {
                    @Override
                    protected void handleMsg(Message msg) throws Exception {
                        MessageHandler.showWarningMessage(CollocationRoomActivity.this, R.string.msg_show);
                    }
                });
            } else {
                selectedTemplate.collocationModel.setShow(Boolean.TRUE);
                MessageHandler.showWarningMessage(CollocationRoomActivity.this, R.string.msg_show);
            }
        }
    }

    private void doSave() {
        for (ArtifactItem artifactItem : selectedTemplate.collocationModel.getItems()) {
            if (artifactItem.getId() == null) {
                MessageHandler.showWarningMessage(this, "Please select pics");
                return;
            }
        }

        try {
            pnlPuzzle.setDrawingCacheEnabled(true);
            pnlPuzzle.destroyDrawingCache();
            pnlPuzzle.buildDrawingCache();
            Bitmap drawingCache = pnlPuzzle.getDrawingCache();
            selectedTemplate.collocationModel
                .setPic(ImageManager.getInstance().extractMiniThumb(drawingCache, 180, 240, false));
            selectedTemplate.collocationModel.setThumbnail(
                ImageManager.getInstance().extractMiniThumb(drawingCache, 90, 120, false));
            selectedTemplate.collocationModel.beforeSave();
            DataFactory.getSingle().saveCollocation(
                selectedTemplate.collocationModel, new BaseHandler() {
                @Override
                protected void handleMsg(Message msg) throws Exception {
                    if (msg.obj instanceof String) {
                        MessageHandler.showWarningMessage(AppContext.context, (String) msg.obj);
                    } else {
                        finish();
                    }
                }
            });
        } catch (Exception e) {
            MessageHandler.showWarningMessage(this, e.getMessage());
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(
        MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(
        MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        if (!forUpdate && Math.abs(motionEvent2.getY() - motionEvent.getY()) > 100 && Math.abs(v2) > 100) {
            switchTemplate();
        }

        return false;
    }

    private void switchTemplate() {
        if (pnlTemplate1.getVisibility() == View.VISIBLE) {
            pnlTemplate1.setVisibility(View.INVISIBLE);
            pnlTemplate2.setVisibility(View.VISIBLE);
            selectedTemplate = templates.get(1);
        } else {
            pnlTemplate2.setVisibility(View.INVISIBLE);
            pnlTemplate1.setVisibility(View.VISIBLE);
            selectedTemplate = templates.get(0);
        }
    }

    class Template {

        TemplateModel templateModel;
        ToggleButtonGroup toggleButtonGroup;
        Collocation collocationModel;
    }
}