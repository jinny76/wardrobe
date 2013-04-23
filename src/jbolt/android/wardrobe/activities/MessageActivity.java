package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import jbolt.android.R;
import jbolt.android.base.BaseHandler;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.MessageHandler;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.webservice.ex.ClientAppException;

/**
 * <p>Title: MessageActivity</p>
 * <p>Description: MessageActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class MessageActivity extends CommentsActivity {

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        super.onCreateActivity(savedInstanceState);

        TextView txtTitle = (TextView) findViewById(R.id.lblTitle);
        txtTitle.setText(R.string.mail);
    }

    @Override
    protected View.OnClickListener getOkCommand() {
        return new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                if (txtContent.getText().length() == 0) {
                    MessageHandler.showWarningMessage(MessageActivity.this, R.string.msg_mail_empty);
                } else {
                    DataFactory.getSingle().sendMessage(
                            txtContent.getText().toString(), (String) params, new BaseHandler() {
                        @Override
                        protected void handleMsg(Message msg) throws Exception {
                            if (msg.obj == null) {
                                MessageHandler.showWarningMessage(MessageActivity.this, R.string.msg_mail_success);
                                finish();
                            } else {
                                MessageHandler.showWarningMessage(MessageActivity.this, (ClientAppException) msg.obj);
                            }
                        }
                    });
                }
            }
        };
    }
}
