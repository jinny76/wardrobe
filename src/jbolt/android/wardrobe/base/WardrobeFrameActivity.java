package jbolt.android.wardrobe.base;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.base.GenericBaseActivity;
import jbolt.android.utils.MessageHandler;
import jbolt.android.wardrobe.activities.ActivityDispatcher;
import jbolt.android.wardrobe.activities.AddNewActivity;
import jbolt.android.widget.ToggleButton;
import jbolt.android.widget.ToggleButtonGroup;

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
    protected ToggleButton btnBottomShowTime;
    protected ToggleButton btnBottomWardrobe;
    protected Button btnBottomAdd;
    protected ToggleButton btnBottomCollocation;
    protected ToggleButton btnBottomPersonalCentre;
    protected ToggleButton btnBottomOther;

    protected void initTopButtons() {
        btnTopReturn = (Button) findViewById(R.id.btnTopReturn);
        btnTopHome = (Button) findViewById(R.id.btnTopHome);

        btnTopReturn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        back();
                    }
                });

        if (btnTopHome != null) {
            btnTopHome.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            ActivityDispatcher.return2Home(WardrobeFrameActivity.this);
                        }
                    });
        }

        initSpecialTopButtons();
    }

    protected void initSpecialTopButtons() {
    }

    protected void initBottomButtons() {
        btnBottomShowTime = (ToggleButton) findViewById(R.id.btnBottomShowTime);
        btnBottomWardrobe = (ToggleButton) findViewById(R.id.btnBottomWardrobe);
        btnBottomAdd = (Button) findViewById(R.id.btnBottomAdd);
        btnBottomCollocation = (ToggleButton) findViewById(R.id.btnBottomCollocation);
        btnBottomPersonalCentre = (ToggleButton) findViewById(R.id.btnBottomPersonalCentre);
        List<ToggleButton> btns = new ArrayList<ToggleButton>();

        if (btnBottomShowTime != null) {
            btnBottomShowTime.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            showTime();
                        }
                    });
            btns.add(btnBottomShowTime);
        }
        if (btnBottomWardrobe != null) {
            btnBottomWardrobe.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            ActivityDispatcher.return2Home(WardrobeFrameActivity.this);
                        }
                    });
            btns.add(btnBottomWardrobe);
        }
        if (btnBottomAdd != null) {
            btnBottomAdd.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            addNew();
                        }
                    });
        }
        if (btnBottomCollocation != null) {
            btnBottomCollocation.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            ActivityDispatcher.collocate(WardrobeFrameActivity.this);
                        }
                    });
            btns.add(btnBottomCollocation);
        }
        if (btnBottomPersonalCentre != null) {
            btnBottomPersonalCentre.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            ActivityDispatcher.openPersonalCentre(WardrobeFrameActivity.this, AppContext.getUser().getId());
                        }
                    });
            btns.add(btnBottomPersonalCentre);
        }

        btnBottomOther = (ToggleButton) findViewById(R.id.btnBottomOther);
        if (btnBottomOther != null) {
            btnBottomOther.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            doOther();
                        }
                    });
            btns.add(btnBottomOther);
        }

        ArrayList<ToggleButton> specialButtons = initSpecialBottomButtons();
        if (specialButtons != null) {
            btns.addAll(specialButtons);
        }

        if (btns.size() > 0) {
            new ToggleButtonGroup(btns.toArray(new ToggleButton[btns.size()]));
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

    protected ArrayList<ToggleButton> initSpecialBottomButtons() {
        return null;
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
