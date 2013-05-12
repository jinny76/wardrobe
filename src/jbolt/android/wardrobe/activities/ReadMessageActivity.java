package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.base.GenericBaseActivity;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.WidgetUtils;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.PersonMessageType;
import jbolt.android.wardrobe.models.PersonMessages;

/**
 * <p>Title: jbolt.android.wardrobe.activities</p>
 * <p>Description: jbolt.android.wardrobe.activities</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: JBolt.com</p>
 *
 * @author Jinni
 */
public class ReadMessageActivity extends GenericBaseActivity {

    ImageButton btnOk;
    TextView txtContent;
    TextView lblTitle;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.readmessage);
        btnOk = (ImageButton) findViewById(R.id.btnOk);
        txtContent = (TextView) findViewById(R.id.txtContent);
        lblTitle = (TextView) findViewById(R.id.lblTitle);
        btnOk.setOnClickListener(getOkCommand());

        DataFactory.getSingle().loadPrivateMessages((String) params, new BaseHandler() {
            @Override
            protected void handleMsg(Message msg) throws Exception {
                if (msg.obj instanceof Exception) {
                    MessageHandler.showWarningMessage(AppContext.context, (Exception) msg.obj);
                } else {
                    PersonMessages messages = (PersonMessages) msg.obj;
                    if (messages != null) {
                        Integer messageType = messages.getType();
                        switch (messageType) {
                            case PersonMessageType.PRIVATE_MSG:
                                lblTitle.setText(getResources().getString(R.string.mail));
                                break;
                            default:
                                lblTitle.setText(getResources().getString(R.string.notice));
                        }
                        txtContent.setText(WidgetUtils.convertString2em(messages.getMsg()));
                    }
                }
            }
        });
    }

    protected View.OnClickListener getOkCommand() {
        return new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                finish();
            }
        };
    }
}