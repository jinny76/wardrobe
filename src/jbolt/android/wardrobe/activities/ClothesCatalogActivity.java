package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import jbolt.android.R;
import jbolt.android.wardrobe.adapters.ClothesCatalogListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.models.ClothesCatalogModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: ClothesCatalogActivity</p>
 * <p>Description: ClothesCatalogActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ClothesCatalogActivity extends WardrobeFrameActivity {

    private ClothesCatalogListAdapter listAdapter;
    private ListView listView;

    protected Button btnTopAdd;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.clothescatalog);

        btnTopAdd = (Button) findViewById(R.id.btnMore);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();
        initBottomButtons();

        listView = (ListView) findViewById(R.id.lstClothesCatalog);
        listAdapter = new ClothesCatalogListAdapter(this);
        listView.setAdapter(listAdapter);

        initListAdapter();
    }

    private void initListAdapter() {
        List<ClothesCatalogModel> items = new ArrayList<ClothesCatalogModel>();
        ClothesCatalogModel item = new ClothesCatalogModel();
        item.setContent("Content1");
        item.setImgId(R.drawable.pic);
        items.add(item);

        item = new ClothesCatalogModel();
        item.setContent("Content2");
        item.setImgId(R.drawable.pic_2);
        items.add(item);
        listAdapter.setCatalogs(items);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initSpecialTopButtons() {
        btnTopAdd.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    addNew();
                }
            });
    }

}
