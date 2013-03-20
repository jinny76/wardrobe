package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;
import jbolt.android.R;
import jbolt.android.wardrobe.adapters.ClothesCatalogListAdapter;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItemModel;

/**
 * <p>Title: ClothesCatalogActivity</p>
 * <p>Description: ClothesCatalogActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ClothesCatalogActivity extends ClothesCatalogAbstractActivity {

    private ClothesCatalogListAdapter listAdapter;
    private ListView listView;


    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.clothescatalog);
        Intent intent = getIntent();
        type = intent.getStringExtra(PARAM_KEY);
        btnTopAdd = (Button) findViewById(R.id.btnTopAdd);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();
        initBottomButtons();

        listView = (ListView) findViewById(R.id.lstClothesCatalog);
        listAdapter = new ClothesCatalogListAdapter(this);
        listView.setAdapter(listAdapter);
        listAdapter.setDeleteListener(new View.OnClickListener() {
            public void onClick(View view) {
                ArtifactItemModel itemModel = (ArtifactItemModel) view.getTag();
                if (itemModel != null) {
                    DataFactory.getSingle().deleteItem(itemModel);
                    handler.sendMessageDelayed(handler.obtainMessage(1), 30);
                }
            }
        });

        refreshAdapter();
        initMenuItems();
    }


    protected void refreshAdapter() {
        List<ArtifactItemModel> items = loadItems();
        listAdapter.setItems(items);
        listAdapter.notifyDataSetChanged();
    }

}
