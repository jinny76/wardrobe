package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.base.ClientHandler;
import jbolt.android.utils.MessageHandler;
import jbolt.android.wardrobe.adapters.MessageListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.PersonMessages;

/**
 * <p>Title: PersonalCentreActivity</p>
 * <p>Description: PersonalCentreActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class PersonalCentreActivity extends WardrobeFrameActivity {

    private Button btnAdd;
    private ListView listView;
    private MessageListAdapter listAdapter;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.personalcentre);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        initTopButtons();
        initBottomButtons();
        btnAdd.setVisibility(View.INVISIBLE);
        listView = (ListView) findViewById(R.id.messages);
        listAdapter = new MessageListAdapter(this);
        listView.setAdapter(listAdapter);
        refreshAdapter();
    }

    protected void loadMessages(final ClientHandler handler) {
        DataFactory.getSingle().loadPersonMessages(new BaseHandler() {
            @Override
            protected void handleMsg(Message msg) throws Exception {
                handler.handleMsg(msg.obj);
            }
        });
    }

    protected void refreshAdapter() {
        loadMessages(
                new ClientHandler() {
                    @Override
                    public void handleMsg(Object obj) {
                        if (obj instanceof List) {
                            listAdapter.setMessages((List<PersonMessages>) obj);
                            listAdapter.notifyDataSetChanged();
                            listView.refreshDrawableState();
                        } else {
                            MessageHandler.showWarningMessage(AppContext.context, (String) obj);
                        }
                    }
                });

    }
}