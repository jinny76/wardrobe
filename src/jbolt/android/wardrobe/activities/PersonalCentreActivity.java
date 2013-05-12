package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.*;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.adapters.MessageListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.Collocation;
import jbolt.android.wardrobe.models.Person;
import jbolt.android.wardrobe.models.PersonMessages;
import jbolt.android.wardrobe.models.RelationsType;
import jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl;

import java.util.HashMap;
import java.util.List;

/**
 * <p>Title: PersonalCentreActivity</p>
 * <p>Description: PersonalCentreActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class PersonalCentreActivity extends WardrobeFrameActivity {

    public static final int CHANGE_USER_INFO = 1;
    private Button btnAdd;
    private Button btnFriend;
    private Button btnAttention;
    private Button btnFans;
    private ListView listView;
    private TextView btnOtherShow;
    private TextView txtNick;
    private MessageListAdapter listAdapter;
    private Person person;
    private ImageView imgPortrait;
    private ImageButton btnAllMessages;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.personalcentre);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnFriend = (Button) findViewById(R.id.btnFriend);
        btnAttention = (Button) findViewById(R.id.btnAttention);
        btnFans = (Button) findViewById(R.id.btnFans);
        btnOtherShow = (TextView) findViewById(R.id.btnOtherShow);
        initTopButtons();
        initBottomButtons();

        btnFans.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                startActivity(FriendListActivity.class, RelationsType.FANS);
            }
        });

        btnAttention.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                startActivity(FriendListActivity.class, RelationsType.OBSERVERS);
            }
        });

        btnFriend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                startActivity(FriendListActivity.class, RelationsType.FRIENDS);
            }
        });
        btnAdd.setVisibility(View.INVISIBLE);
        listView = (ListView) findViewById(R.id.lstMessages);
        listAdapter = new MessageListAdapter(this);
        listView.setAdapter(listAdapter);

        txtNick = (TextView) findViewById(R.id.txtNick);
        imgPortrait = (ImageView) findViewById(R.id.imgPortrait);
        imgPortrait.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                startActivity(PersonalInfoActivity.class, new HashMap(), CHANGE_USER_INFO);
            }
        });
        refreshUserInfo();

        btnOtherShow.setOnClickListener(new OnClickListener() {
            public void onClickAction(View view) {
                startActivity(MyShowCatalogActivity.class, new HashMap());
            }
        });

        btnAllMessages = (ImageButton) findViewById(R.id.btnAllMessages);
        btnAllMessages.setOnClickListener(new OnClickListener() {
            @Override
            protected void onClickAction(View view) {
                startActivity(MessageCentreActivity.class, null);
            }
        });
    }

    private void refreshUserInfo() {
        Person person = new Person();
        person.setId((String) params);
        PersonManagerDefaultImpl.find(
                person, new BaseHandler() {
            @Override
            protected void handleMsg(Message msg) throws Exception {
                if (msg.obj instanceof Person) {
                    PersonalCentreActivity.this.person = (Person) msg.obj;
                    setUserInfo();
                } else {
                    MessageHandler.showWarningMessage(PersonalCentreActivity.this, (Exception) msg.obj);
                }
            }
        });

    }

    private void setUserInfo() {
        refreshMessages(this.person.getId());
        refreshMyShows(this.person.getId());
        txtNick.setText(this.person.getNick());
        ImageManager.getInstance().lazyLoadImage(
                ImageManager.getUrl(this.person.getId(), true), null, new HashMap<String, String>(), imgPortrait);
    }

    private void refreshMyShows(String userId) {
        DataFactory.getSingle().loadMyShow(userId,
                new BaseHandler() {
                    @Override
                    protected void handleMsg(Message msg) throws Exception {
                        if (msg.obj instanceof List) {
                            List<Collocation> shows = (List<Collocation>) msg.obj;
                            ImageView[] imgShows = new ImageView[]{
                                    (ImageView) findViewById(R.id.imgShow1), (ImageView) findViewById(R.id.imgShow2),
                                    (ImageView) findViewById(R.id.imgShow3)};

                            for (int i = 0; i < shows.size() && i < 3; i++) {
                                ImageManager
                                        .getInstance()
                                        .lazyLoadImage(ImageManager.getUrl(shows.get(i).getId(), true), null, new HashMap<String, String>(), imgShows[i]);
                            }
                        } else {
                            MessageHandler.showWarningMessage(AppContext.context, (Exception) msg.obj);
                        }
                    }
                }

        );
    }

    protected void refreshMessages(String userId) {
        DataFactory.getSingle().loadPersonMessages(userId,
                new BaseHandler() {
                    @Override
                    protected void handleMsg(Message msg) throws Exception {
                        if (msg.obj instanceof List) {
                            listAdapter.setMessages((List<PersonMessages>) msg.obj);
                            listAdapter.notifyDataSetChanged();
                            listView.refreshDrawableState();
                        } else {
                            MessageHandler.showWarningMessage(AppContext.context, (Exception) msg.obj);
                        }
                    }
                });

    }

    @Override
    protected void onReceiveResult(int requestCode, int resultCode, Intent data) throws Exception {
        if (requestCode == CHANGE_USER_INFO) {
            this.person = (Person) data.getSerializableExtra("user");
            setUserInfo();
        }
    }
}