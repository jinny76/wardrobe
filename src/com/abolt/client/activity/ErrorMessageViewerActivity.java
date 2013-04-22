package com.abolt.client.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import jbolt.android.R;
import jbolt.android.base.GenericBaseActivity;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ErrorMessageViewerActivity extends GenericBaseActivity {

    public static final String ERROR = "error";

    private Button btnClose;
    private TextView txtErrorMessage;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.errormessageviewer);

        btnClose = (Button) findViewById(R.id.btnClose);
        txtErrorMessage = (TextView) findViewById(R.id.txtErrorMessage);

        String exception = (String) getIntent().getExtras().get(ERROR);
        txtErrorMessage.setText(exception);
    }
}
