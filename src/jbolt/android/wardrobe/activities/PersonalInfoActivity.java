package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.weibo.sdk.android.Oauth2AccessToken;
import jbolt.android.R;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.weibo.impl.AccessTokenKeeper;
import jbolt.weibo.impl.SinaUser;
import jbolt.weibo.impl.WeiboManagerSinaImpl;

/**
 * <p>Title: PersonalInfoActivity</p>
 * <p>Description: PersonalInfoActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class PersonalInfoActivity extends WardrobeFrameActivity {

    TextView txtNick;
    ImageView imgPortrait;
    Button btnChangePassword;

    EditText txtSignature;
    EditText txtNickName;
    EditText txtBirthday;
    EditText txtGender;
    EditText txtMail;
    EditText txtMobile;

    TextView txtSina;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.personal_info);

        txtNick = (TextView) findViewById(R.id.txtNick);
        txtNick.setText(AppContext.getUser().getNick());

        imgPortrait = (ImageView) findViewById(R.id.imgPortrait);
        imgPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doChangePortrait();
            }
        });

        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doChangePassword();
            }
        });

        txtSignature = (EditText) findViewById(R.id.txtSignature);
        txtNickName = (EditText) findViewById(R.id.txtNickName);
        txtBirthday = (EditText) findViewById(R.id.txtBirthday);
        txtGender = (EditText) findViewById(R.id.txtGender);
        txtMail = (EditText) findViewById(R.id.txtMail);
        txtMobile = (EditText) findViewById(R.id.txtMobile);

        txtSina = (TextView) findViewById(R.id.txtSina);
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(this);
        if (token.isSessionValid()) {
            txtSina.setText(AccessTokenKeeper.readUserName(this));
        } else {
            txtSina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WeiboManagerSinaImpl.getInstance().doAuthen(new BaseHandler() {
                        @Override
                        protected void handleMsg(Message msg) throws Exception {
                            if (msg != null) {
                                if (msg.obj instanceof SinaUser) {
                                    txtSina.setText(((SinaUser) msg.obj).getName());
                                } else if (msg.obj instanceof String) {
                                    txtSina.setText((CharSequence) msg.obj);
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    private void doChangePassword() {


    }

    private void doChangePortrait() {


    }
}
