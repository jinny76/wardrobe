package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import jbolt.android.R;
import jbolt.android.base.BaseHandler;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.wardrobe.adapters.PersonalRelationsAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.Person;
import jbolt.android.wardrobe.models.RelationsType;
import jbolt.android.widget.ToggleButton;
import jbolt.android.widget.ToggleButtonGroup;

import java.util.List;

/**
 * <p>Title: jbolt.android.wardrobe.activities</p>
 * <p>Description: jbolt.android.wardrobe.activities</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: JBolt.com</p>
 *
 * @author Jinni
 */
public class FriendListActivity extends WardrobeFrameActivity {

    ToggleButton btnAttention;
    ToggleButton btnFans;
    ToggleButton btnFriend;
    PersonalRelationsAdapter adapter;
    ListView lstFriends;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.friendlist);

        //顶部按钮事件，每一个Activity必调
        initTopButtons();
        initBottomButtons();

        adapter = new PersonalRelationsAdapter(this);
        lstFriends = (ListView) findViewById(R.id.lstFriends);
        lstFriends.setAdapter(adapter);
        refreshList((Integer) params);
    }

    protected void initTopButtons() {
        btnAttention = (ToggleButton) findViewById(R.id.btnAttention);
        btnFans = (ToggleButton) findViewById(R.id.btnFans);
        btnFriend = (ToggleButton) findViewById(R.id.btnFriend);

        ToggleButtonGroup toggleButtonGroup = new ToggleButtonGroup(new ToggleButton[]{btnAttention, btnFans, btnFriend});

        btnAttention.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                refreshList(RelationsType.OBSERVERS);
            }
        });

        btnFans.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                refreshList(RelationsType.FANS);
            }
        });

        btnFriend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                refreshList(RelationsType.FRIENDS);
            }
        });
    }

    public void refreshList(final Integer friendType) {
        DataFactory.getSingle().loadAllFriends(friendType, new BaseHandler() {
            @Override
            protected void handleMsg(Message msg) throws Exception {
                if (msg.obj instanceof List) {
                    adapter.getRelations().clear();
                    adapter.getRelations().addAll((List<Person>) msg.obj);
                    adapter.setRelationType(friendType);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}


