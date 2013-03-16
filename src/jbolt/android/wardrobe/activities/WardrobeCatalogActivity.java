package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import jbolt.android.R;
import jbolt.android.wardrobe.adapters.CatalogListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactTypeModel;
import jbolt.android.wardrobe.models.CatalogItemModel;

/**
 * <p>Title: WardrobeCatalogActivity</p>
 * <p>Description: WardrobeCatalogActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class WardrobeCatalogActivity extends WardrobeFrameActivity {

    private Button btnMore;
    private ListView lstCatalog;
    private CatalogListAdapter listAdapter;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.catalog);
        topReturn = (Button) findViewById(R.id.btnTopReturn);
        btnTopHome = (Button) findViewById(R.id.btnTopHome);
        btnMore = (Button) findViewById(R.id.btnMore);
        //顶部按钮事件，每一个Activity必调
        initTopButtons();
        lstCatalog = (ListView) findViewById(R.id.lstCatalog);
        listAdapter = new CatalogListAdapter(this);
        lstCatalog.setAdapter(listAdapter);

        List<ArtifactTypeModel> types = DataFactory.getSingle().getTypes();
        List<CatalogItemModel> items = new ArrayList<CatalogItemModel>();
        int i = 0;
        CatalogItemModel catalogItem = new CatalogItemModel();
        for (int index = 0; index < types.size(); i++) {
            ArtifactTypeModel type = types.get(index);
            if (i == 0) {
                catalogItem.setType1(type);
            }
            if (i == 1) {
                catalogItem.setType2(type);
            }
            if (i == 2) {
                catalogItem.setType3(type);
                items.add(catalogItem);
                i = 0;
            }
        }
        listAdapter.setModels(items);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initSpecialTopButtons() {
        btnMore.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        more();
                    }
                });
    }

    private void more() {
    }
}