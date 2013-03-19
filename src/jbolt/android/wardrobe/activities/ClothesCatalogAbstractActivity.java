package jbolt.android.wardrobe.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import jbolt.android.R;
import jbolt.android.meta.MenuItem;
import jbolt.android.utils.WidgetUtils;
import jbolt.android.wardrobe.adapters.MenuListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.models.ArtifactTypeModel;

/**
 * <p>Title: ClothesCatalogAbstractActivity</p>
 * <p>Description: ClothesCatalogAbstractActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public abstract class ClothesCatalogAbstractActivity extends WardrobeFrameActivity {

    private MenuListAdapter leftMenuListAdapter;
    private MenuListAdapter rightMenuListAdapter;
    private ListView leftMenus;
    private ListView rightMenus;
    protected Button btnTopAdd;
    protected ArtifactTypeModel typeModel;

    @Override
    protected void initSpecialTopButtons() {
        btnTopAdd.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        addNew();
                    }
                });
    }

    protected void initMenuItems() {
        ImageButton latitude1 = (ImageButton) findViewById(R.id.latitude1);
        latitude1.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        WidgetUtils.setWidgetVisible(leftMenus, !WidgetUtils.isWidgetVisible(leftMenus));
                    }
                });
        ImageButton latitude2 = (ImageButton) findViewById(R.id.latitude2);
        latitude2.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        WidgetUtils.setWidgetVisible(rightMenus, !WidgetUtils.isWidgetVisible(rightMenus));
                    }
                });

        leftMenus = (ListView) findViewById(R.id.leftMenus);
        leftMenus.setVisibility(View.INVISIBLE);
        leftMenuListAdapter = new MenuListAdapter(this);
        leftMenus.setAdapter(leftMenuListAdapter);

        List<MenuItem> items = new ArrayList<MenuItem>();
        MenuItem item = new MenuItem();
        item.setTxt(getString(R.string.latitude_menu1_left));
        items.add(item);

        item = new MenuItem();
        item.setTxt(getString(R.string.latitude_menu2_left));
        items.add(item);

        leftMenuListAdapter.setLeft(true);
        leftMenuListAdapter.setItems(items);
        leftMenuListAdapter.notifyDataSetChanged();

        rightMenus = (ListView) findViewById(R.id.rightMenus);
        rightMenus.setVisibility(View.INVISIBLE);
        rightMenuListAdapter = new MenuListAdapter(this);
        rightMenus.setAdapter(rightMenuListAdapter);

        items = new ArrayList<MenuItem>();
        item = new MenuItem();
        item.setTxt(getString(R.string.latitude_menu1_right));
        items.add(item);

        item = new MenuItem();
        item.setTxt(getString(R.string.latitude_menu2_right));
        items.add(item);

        item = new MenuItem();
        item.setTxt(getString(R.string.latitude_menu3_right));
        items.add(item);

        item = new MenuItem();
        item.setTxt(getString(R.string.latitude_menu4_right));
        items.add(item);

        item = new MenuItem();
        item.setTxt(getString(R.string.latitude_menu5_right));
        items.add(item);

        rightMenuListAdapter.setItems(items);
        rightMenuListAdapter.notifyDataSetChanged();

        findViewById(R.id.btnMidHide).setVisibility(View.INVISIBLE);
        findViewById(R.id.latitudeBar).setVisibility(View.INVISIBLE);
    }

    protected abstract void refreshAdapter();
}
