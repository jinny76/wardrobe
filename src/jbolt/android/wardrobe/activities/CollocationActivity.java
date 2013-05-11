package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import jbolt.android.R;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.wardrobe.adapters.CollocationListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;

import java.util.HashMap;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class CollocationActivity extends WardrobeFrameActivity {

    protected Button btnTopAdd;

    protected ListView lstCollocation;
    protected CollocationListAdapter adapter;

    /**
     * 每一个activity需要继承此基类，实现该方法，每个类的layout有自己的空间，所以对于顶部button需要重新取一下
     *
     * @param savedInstanceState
     * @throws Exception
     */
    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.collocation);

        btnTopAdd = (Button) findViewById(R.id.btnTopAdd);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();
        initBottomButtons();

        lstCollocation = (ListView) findViewById(R.id.lstCollocation);
        lstCollocation.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new CollocationListAdapter(this);
        lstCollocation.setAdapter(adapter);
        refreshData();
    }

    private void refreshData() {
        if (adapter != null) {
            adapter.refeshData();
        }
    }

    @Override
    protected void initSpecialTopButtons() {
        btnTopAdd.setOnClickListener(
                new OnClickListener() {
                    public void onClickAction(View view) {
                        addNewCollocation();
                    }
                });
    }

    private void addNewCollocation() {
        startActivity(CollocationRoomActivity.class, new HashMap());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    protected void onReceiveResult(int requestCode, int resultCode, Intent data) throws Exception {
        super.onReceiveResult(requestCode, resultCode, data);
        if (resultCode != CANCEL_ADD) {
            if (requestCode == ADD_NEW) {
                startActivity(PicConfirmActivity.class, new HashMap(), CONFIRM_ADD_NEW);
            }
        }
    }
}
