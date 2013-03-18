package jbolt.android.wardrobe.base;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jbolt.android.R;
import jbolt.android.base.GenericBaseActivity;
import jbolt.android.utils.MessageHandler;
import jbolt.android.wardrobe.activities.CollocationActivity;
import jbolt.android.wardrobe.activities.WardrobeCatalogActivity;
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

    protected Button btnTopReturn;
    protected Button btnTopHome;

    protected ToggleButton btnBottomShowTime;
    protected ToggleButton btnBottomWardrobe;
    protected Button btnBottomAdd;
    protected ToggleButton btnBottomCollocation;
    protected ToggleButton btnBottomPersonalCentre;

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
                            return2Home();
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
                            openWardrobe();
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
                            collocate();
                        }
                    });
            btns.add(btnBottomCollocation);
        }
        if (btnBottomPersonalCentre != null) {
            btnBottomPersonalCentre.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            openPersonalCentre();
                        }
                    });
            btns.add(btnBottomPersonalCentre);
        }
        if (btns.size() > 0) {
            new ToggleButtonGroup(btns.toArray(new ToggleButton[btns.size()]));
        }
        initSpecialBottomButtons();
    }

    /**
     * 打开秀场事件
     */
    protected void showTime() {
        MessageHandler.showWarningMessage(this, "Show Time");
    }


    /**
     * 打开个人中心
     */
    private void openPersonalCentre() {
        MessageHandler.showWarningMessage(this, "Persional Centre");
    }

    /**
     * 添加搭配事件
     */
    private void collocate() {
        if (!(this instanceof CollocationActivity)) {
            startActivity(CollocationActivity.class, new HashMap());
        }
    }

    /**
     * 添加打开衣橱事件
     */
    private void openWardrobe() {
        MessageHandler.showWarningMessage(this, "Open Wardrobe");
    }


    /**
     * 此处处理新增事件
     */
    protected void addNew() {
        MessageHandler.showWarningMessage(this, "Add New");
    }


    protected void initSpecialBottomButtons() {
    }


    /**
     * 返回主页事件
     */
    protected void return2Home() {
        Intent intent = new Intent(getApplicationContext(), WardrobeCatalogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * 返回
     */
    protected void back() {
        this.finish();
    }

}
