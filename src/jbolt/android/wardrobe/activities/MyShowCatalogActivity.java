package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.wardrobe.adapters.MyShowListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.Collocation;

import java.util.List;

/**
 * <p>Title: MyShowCatalogActivity</p>
 * <p>Description: MyShowCatalogActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class MyShowCatalogActivity extends WardrobeFrameActivity {

    private ListView lstMyShows;
    private MyShowListAdapter myShowListAdapter;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.myshow_list);
        lstMyShows = (ListView) findViewById(R.id.lstMyShows);
        myShowListAdapter = new MyShowListAdapter(this);
        lstMyShows.setAdapter(myShowListAdapter);
        refreshMyShows();
    }

    private void refreshMyShows() {
        DataFactory.getSingle().loadMyShow(AppContext.getUser().getId(),
                new BaseHandler() {
                    @Override
                    protected void handleMsg(Message msg) throws Exception {
                        if (msg.obj instanceof List) {
                            myShowListAdapter.setItems((List<Collocation>) msg.obj);
                            myShowListAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }


}
