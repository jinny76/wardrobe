package jbolt.android.wardrobe.activities;

import java.util.HashMap;

import jbolt.android.R;
import jbolt.android.base.BaseHandler;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.MessageHandler;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl;
import jbolt.android.webservice.ex.ClientAppException;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class CollocationReportActivity extends CommentsActivity {
	TextView txtTitle;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        super.onCreateActivity(savedInstanceState);

        txtTitle = (TextView) findViewById(R.id.lblTitle);
        txtTitle.setText(R.string.offence_report);
    }

    @Override
    protected View.OnClickListener getOkCommand() {
        return new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                if (txtContent.getText().length() == 0) {
                    MessageHandler.showWarningMessage(CollocationReportActivity.this, R.string.msg_offence_empty);
                } else {
                	CollocationManagerDefaultImpl.reportIllegalCollocation((String) ((HashMap)params).get("id"), txtContent.getText().toString(), (String) ((HashMap)params).get("owner"), new BaseHandler() {
                        @Override
                        protected void handleMsg(Message msg) throws Exception {
                            if (msg.obj == null) {
                                MessageHandler.showWarningMessage(CollocationReportActivity.this, R.string.msg_offence_success);
                                finish();
                            } else {
                                MessageHandler.showWarningMessage(CollocationReportActivity.this, (ClientAppException) msg.obj);
                            }
                        }
                    });
                }
            }
        };
    }
}
