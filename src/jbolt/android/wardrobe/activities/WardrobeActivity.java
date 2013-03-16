package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import jbolt.android.R;
import jbolt.android.wardrobe.adapters.CollocationListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.widget.ToggleButton;
import jbolt.android.widget.ToggleButtonGroup;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class WardrobeActivity extends WardrobeFrameActivity {

    ToggleButton btnBottomShowTime;
    ToggleButton btnBottomWardrobe;
    Button btnBottomAdd;
    ToggleButton btnBottomCollocation;
    ToggleButton btnBottomPersonalCentre;

    ListView lstCollocation;

    /**
     * 每一个activity需要继承此基类，实现该方法，每个类的layout有自己的空间，所以对于顶部button需要重新取一下
     *
     * @param savedInstanceState
     * @throws Exception
     */
    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.wardrobe);
        topReturn = (Button) findViewById(R.id.btnTopReturn);
        btnTopHome = (Button) findViewById(R.id.btnTopHome);
        btnTopAdd = (Button) findViewById(R.id.btnTopAdd);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();
        btnBottomShowTime = (ToggleButton) findViewById(R.id.btnBottomShowTime);
        btnBottomShowTime.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    showTime();
                }
            });
        btnBottomWardrobe = (ToggleButton) findViewById(R.id.btnBottomWardrobe);
        btnBottomWardrobe.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    openWardrobe();
                }
            });
        btnBottomAdd = (Button) findViewById(R.id.btnBottomAdd);
        btnBottomAdd.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    addNew();
                }
            });
        btnBottomCollocation = (ToggleButton) findViewById(R.id.btnBottomCollocation);
        btnBottomCollocation.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    collocate();
                }
            });
        btnBottomPersonalCentre = (ToggleButton) findViewById(R.id.btnBottomPersonalCentre);
        btnBottomPersonalCentre.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    openPersonalCentre();
                }
            });

        ToggleButtonGroup bottomButtonGroup = new ToggleButtonGroup(
            new ToggleButton[]{
                btnBottomShowTime, btnBottomWardrobe, btnBottomCollocation, btnBottomPersonalCentre
            });

        lstCollocation = (ListView) findViewById(R.id.lstCollocation);
        lstCollocation.setAdapter(new CollocationListAdapter(this));
    }

    @Override
    protected void initSpecialTopButtons() {
        btnTopHome.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    return2Home();
                }
            });
        btnTopAdd.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    addNew();
                }
            });
    }

    /**
     * 打开个人中心
     */
    private void openPersonalCentre() {
    }

    /**
     * 添加搭配事件
     */
    private void collocate() {
    }

    /**
     * 添加打开衣橱事件
     */
    private void openWardrobe() {
    }

    /**
     * 添加秀场事件
     */
    private void showTime() {
    }

}
