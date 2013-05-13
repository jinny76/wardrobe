package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import jbolt.android.R;
import jbolt.android.base.BaseHandler;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.wardrobe.adapters.MessageListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.PersonMessageType;
import jbolt.android.wardrobe.models.PersonMessages;
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
public class MessageCentreActivity extends WardrobeFrameActivity {

    ToggleButton btnComment;
    ToggleButton btnMessage;
    ToggleButton btnNotice;
    MessageListAdapter adapter;
    ListView lstMessages;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.messagecentre);

        //顶部按钮事件，每一个Activity必调
        initTopButtons();
        initBottomButtons();

        adapter = new MessageListAdapter(this);
        lstMessages = (ListView) findViewById(R.id.lstMessages);
        lstMessages.setAdapter(adapter);
        refreshList((Integer) params);
    }

    protected void initTopButtons() {
        btnComment = (ToggleButton) findViewById(R.id.btnComment);
        btnMessage = (ToggleButton) findViewById(R.id.btnMessage);
        btnNotice = (ToggleButton) findViewById(R.id.btnNotice);

        ToggleButtonGroup toggleButtonGroup = new ToggleButtonGroup(new ToggleButton[]{btnComment, btnMessage, btnNotice});

        btnComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                refreshList(PersonMessageType.COMMENTS);
            }
        });

        btnMessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                refreshList(PersonMessageType.PRIVATE_MSG);
            }
        });

        btnNotice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                refreshList(PersonMessageType.FRIENDS_AND_FANS);
            }
        });
    }

    public void refreshList(final Integer messageType) {
        DataFactory.getSingle().loadAllMessages(messageType, new BaseHandler() {
            @Override
            protected void handleMsg(Message msg) throws Exception {
                if (msg.obj instanceof List) {
                    adapter.getMessages().clear();
                    adapter.getMessages().addAll((List<PersonMessages>) msg.obj);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
