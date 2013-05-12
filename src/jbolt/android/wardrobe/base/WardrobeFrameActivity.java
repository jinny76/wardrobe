package jbolt.android.wardrobe.base;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.base.GenericBaseActivity;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.MessageHandler;
import jbolt.android.wardrobe.activities.ActivityDispatcher;
import jbolt.android.wardrobe.activities.AddNewActivity;

import java.util.HashMap;

/**
 * <p>Title: WardrobeFrameActivity</p>
 * <p>Description: WardrobeFrameActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public abstract class WardrobeFrameActivity extends GenericBaseActivity {

    public static final int ADD_NEW = 1;
    public static final int CANCEL_ADD = 4;
    public static final int CONFIRM_ADD_NEW = 2;
    public static final int SWITCH_HANGER = 3;
    public static final String RESULT_PIC = "RESULT_PIC";
    protected Button btnTopReturn;
    protected Button btnTopHome;
    protected ImageButton btnBottomShowTime;
    protected ImageButton btnBottomWardrobe;
    protected ImageButton btnBottomAdd;
    protected ImageButton btnBottomCollocation;
    protected ImageButton btnBottomPersonalCentre;
    protected ImageButton btnBottomOther;

    protected void initTopButtons() {
        btnTopReturn = (Button) findViewById(R.id.btnTopReturn);
        btnTopHome = (Button) findViewById(R.id.btnTopHome);

        btnTopReturn.setOnClickListener(
                new OnClickListener() {
                    public void onClickAction(View view) {
                        back();
                    }
                });

        if (btnTopHome != null) {
            btnTopHome.setOnClickListener(
                    new OnClickListener() {
                        public void onClickAction(View view) {
                            ActivityDispatcher.return2Home(WardrobeFrameActivity.this);
                        }
                    });
        }

        initSpecialTopButtons();
    }

    protected void initSpecialTopButtons() {
    }

    protected void initBottomButtons() {
        btnBottomShowTime = (ImageButton) findViewById(R.id.btnBottomShowTime);
        btnBottomWardrobe = (ImageButton) findViewById(R.id.btnBottomWardrobe);
        btnBottomAdd = (ImageButton) findViewById(R.id.btnBottomAdd);
        btnBottomCollocation = (ImageButton) findViewById(R.id.btnBottomCollocation);
        btnBottomPersonalCentre = (ImageButton) findViewById(R.id.btnBottomPersonalCentre);
        btnBottomOther = (ImageButton) findViewById(R.id.btnBottomOther);

        if (btnBottomShowTime != null) {
            btnBottomShowTime.setOnClickListener(
                    new OnClickListener() {
                        public void onClickAction(View view) {
                            showTime();
                        }
                    });
        }
        if (btnBottomWardrobe != null) {
            btnBottomWardrobe.setOnClickListener(
                    new OnClickListener() {
                        public void onClickAction(View view) {
                            ActivityDispatcher.return2Home(WardrobeFrameActivity.this);
                        }
                    });
        }
        if (btnBottomAdd != null) {
            btnBottomAdd.setOnClickListener(
                    new OnClickListener() {
                        public void onClickAction(View view) {
                            addNew();
                        }
                    });
        }
        if (btnBottomCollocation != null) {
            btnBottomCollocation.setOnClickListener(
                    new OnClickListener() {
                        public void onClickAction(View view) {
                            ActivityDispatcher.collocate(WardrobeFrameActivity.this);
                        }
                    });
        }
        if (btnBottomPersonalCentre != null) {
            btnBottomPersonalCentre.setOnClickListener(
                    new OnClickListener() {
                        public void onClickAction(View view) {
                            ActivityDispatcher.openPersonalCentre(WardrobeFrameActivity.this, AppContext.getUser().getId());
                        }
                    });
        }

        if (btnBottomOther != null) {
            btnBottomOther.setOnClickListener(
                    new OnClickListener() {
                        public void onClickAction(View view) {
                            doOther();
                        }
                    });
        }
    }

    protected void doOther() {
        MessageHandler.showWarningMessage(this, "Other");
    }

    /**
     * 打开秀场事件
     */
    protected void showTime() {
        ActivityDispatcher.openShow(this);
    }

    /**
     * 此处处理新增事件
     */
    protected void addNew() {
        startActivity(AddNewActivity.class, new HashMap(), ADD_NEW);
    }

    /**
     * 返回
     */
    protected void back() {
        this.finish();
    }

    protected void exit() {
        MessageHandler.showOptionDialog(
                this, R.string.common_warning, R.string.msg_exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                }, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }
        );
    }

}
