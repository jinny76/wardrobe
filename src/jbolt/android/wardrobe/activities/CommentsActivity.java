package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import jbolt.android.R;
import jbolt.android.base.BaseHandler;
import jbolt.android.base.GenericBaseActivity;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.WidgetUtils;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.webservice.ex.ClientAppException;

/**
 * <p>Title: CommentsActivity</p>
 * <p>Description: CommentsActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class CommentsActivity extends GenericBaseActivity {

    ImageButton btnOk;
    ImageButton btnCancel;
    ImageButton btnFace;
    ImageView imgface;
    EditText txtContent;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.comments);
        btnOk = (ImageButton) findViewById(R.id.btnOk);
        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnFace = (ImageButton) findViewById(R.id.btnFace);
        imgface = (ImageView) findViewById(R.id.imgface);
        txtContent = (EditText) findViewById(R.id.txtContent);
        imgface.setVisibility(View.INVISIBLE);

        imgface.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            float x = event.getX();
                            float y = event.getY();
                            int xIndex = (int) (x / 25);
                            int yIndex = (int) (y / 20);

                            System.out.println("yIndex = " + yIndex);
                            System.out.println("xIndex = " + xIndex);

                            String emName = "/[" + xIndex + "|" + yIndex + "]";
                            renderContent(txtContent, emName);
                            WidgetUtils.setWidgetVisible(imgface, false);
                        }
                        return true;
                    }
                });

        btnFace.setOnClickListener(
                new OnClickListener() {
                    public void onClickAction(View view) {
                        WidgetUtils.setWidgetVisible(imgface, !WidgetUtils.isWidgetVisible(imgface));
                    }
                });
        btnCancel.setOnClickListener(
                new OnClickListener() {
                    public void onClickAction(View view) {
                        setResult(WardrobeFrameActivity.CANCEL_ADD, null);
                        finish();
                    }
                });
        btnOk.setOnClickListener(getOkCommand());
    }

    protected View.OnClickListener getOkCommand() {
        return new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                if (txtContent.getText().length() == 0) {
                    MessageHandler.showWarningMessage(CommentsActivity.this, R.string.msg_comment_empty);
                } else {
                    DataFactory.getSingle().addCommemts(
                            txtContent.getText().toString(), (String) params, new BaseHandler() {
                        @Override
                        protected void handleMsg(Message msg) throws Exception {
                            if (msg.obj instanceof String) {
                                MessageHandler.showWarningMessage(CommentsActivity.this, R.string.msg_comment_success);
                                finish();
                            } else {
                                MessageHandler.showWarningMessage(CommentsActivity.this, (ClientAppException) msg.obj);
                            }
                        }
                    });
                }
            }
        };
    }

    private void renderContent(EditText txtContent, String emName) {
        int cursor = txtContent.getSelectionStart();
        String content = txtContent.getText().toString();
        if (cursor < content.length()) {
            content = content.substring(0, cursor) + emName + content.substring(cursor);
        } else {
            content += emName;
        }
        txtContent.setText(WidgetUtils.convertString2em(content));
        txtContent.setSelection(cursor + emName.length());
    }
}