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
 * <p>Title: OffenceReportActivity</p>
 * <p>Description: OffenceReportActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class OffenceReportActivity extends CommentsActivity {

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
                    MessageHandler.showWarningMessage(OffenceReportActivity.this, R.string.msg_offence_empty);
                } else {
                    DataFactory.getSingle().addOffenceReport(
                            txtContent.getText().toString(), (String) params, new BaseHandler() {
                        @Override
                        protected void handleMsg(Message msg) throws Exception {
                            if (msg.obj == null) {
                                MessageHandler.showWarningMessage(OffenceReportActivity.this, R.string.msg_offence_success);
                                finish();
                            } else {
                                MessageHandler.showWarningMessage(OffenceReportActivity.this, (ClientAppException) msg.obj);
                            }
                        }
                    });
                }
            }
        };
    }
}