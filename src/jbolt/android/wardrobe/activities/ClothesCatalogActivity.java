package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.base.ClientHandler;
import jbolt.android.utils.MessageHandler;
import jbolt.android.wardrobe.adapters.ClothesCatalogListAdapter;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItem;

import java.util.List;

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
        listAdapter.setDeleteListener(
            new View.OnClickListener() {
                public void onClick(View view) {
                    ArtifactItem item = (ArtifactItem) view.getTag();
                    if (item != null) {
                        DataFactory.getSingle().deleteItem(item);
                        handler.sendMessageDelayed(handler.obtainMessage(1), 30);
                    }
                }
            });

        refreshAdapter();
        initMenuItems();
    }


    protected void refreshAdapter() {
        loadItems(
            new ClientHandler() {
                @Override
                public void handleMsg(Object obj) {
                    if (obj instanceof List) {
                        listAdapter.setItems((List<ArtifactItem>) obj);
                        listAdapter.notifyDataSetChanged();
                        listView.refreshDrawableState();
                    } else {
                        MessageHandler.showWarningMessage(AppContext.context, (String) obj);
                    }
                }
            });

    }
}
