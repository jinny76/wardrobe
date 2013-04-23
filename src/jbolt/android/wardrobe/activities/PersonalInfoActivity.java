package jbolt.android.wardrobe.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.weibo.sdk.android.Oauth2AccessToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import jbolt.android.R;
import jbolt.android.base.AppConfig;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.SDCardUtilities;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.Person;
import jbolt.android.wardrobe.service.impl.PersonManagerDefaultImpl;
import jbolt.android.webservice.ex.ClientAppException;
import jbolt.weibo.impl.AccessTokenKeeper;
import jbolt.weibo.impl.SinaUser;
import jbolt.weibo.impl.WeiboManagerSinaImpl;
import org.apache.commons.lang.StringUtils;

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

        ImageManager.getInstance().lazyLoadImage(
                ImageManager.getUrl(AppContext.getUser().getId(), true), null, new HashMap<String, String>(), imgPortrait);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        txtSignature = (EditText) findViewById(R.id.txtSignature);
        txtNickName = (EditText) findViewById(R.id.txtNickName);
        txtBirthday = (EditText) findViewById(R.id.txtBirthday);
        txtGender = (EditText) findViewById(R.id.txtGender);
        txtMail = (EditText) findViewById(R.id.txtMail);
        txtMobile = (EditText) findViewById(R.id.txtMobile);

        Person person = AppContext.getUser();
        if (!StringUtils.isEmpty(person.getSignature())) {
            txtSignature.setText(person.getSignature());
        }
        if (!StringUtils.isEmpty(person.getNick())) {
            txtNickName.setText(person.getNick());
        }
        if (!StringUtils.isEmpty(person.getMail())) {
            txtMail.setText(person.getMail());
        }
        if (!StringUtils.isEmpty(person.getMobile())) {
            txtMobile.setText(person.getMobile());
        }

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

    private void save() {
        String signature = txtSignature.getText().toString();
        String nickName = txtNickName.getText().toString();
        String birthday = txtBirthday.getText().toString();
        String gender = txtGender.getText().toString();
        String mail = txtMail.getText().toString();
        String mobile = txtMobile.getText().toString();
        Person person = AppContext.getUser();
        person.setSignature(signature);
        person.setNick(nickName);
        person.setGender(gender);
        person.setMail(mail);
        person.setMobile(mobile);
        PersonManagerDefaultImpl.update(person, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.obj instanceof ClientAppException) {
                    MessageHandler.showWarningMessage(AppContext.context, (ClientAppException) msg.obj);
                } else {
                    finish();
                }
            }
        });
    }

    protected void onReceiveResult(int requestCode, int resultCode, Intent data) throws Exception {
        super.onReceiveResult(requestCode, resultCode, data);
        if (resultCode != CANCEL_ADD) {
            if (requestCode == ADD_NEW) {
                String userId = AppConfig.getSysConfig(DataFactory.USER_ID);
                final File thumbnail = new File(SDCardUtilities.getSdCardPath() + DataFactory.FILE_ROOT + "/tmp/thumbnail.jpeg");
                File pic = new File(SDCardUtilities.getSdCardPath() + DataFactory.FILE_ROOT + "/tmp/pic.jpeg");
                if (thumbnail.exists()) {
                    PersonManagerDefaultImpl.changePortrait(userId, new File[]{thumbnail, pic}, new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg.obj instanceof ClientAppException) {
                                MessageHandler.showWarningMessage(AppContext.context, (ClientAppException) msg.obj);
                            } else {
                                FileInputStream fis = null;
                                try {
                                    fis = new FileInputStream(thumbnail);
                                    Bitmap thumbnailPic = BitmapFactory.decodeStream(fis);
                                    imgPortrait.setImageBitmap(thumbnailPic);
                                } catch (FileNotFoundException e) {
                                    e(e.getMessage());
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private void doChangePortrait() {
        startActivity(AddNewActivity.class, new HashMap(), ADD_NEW);
    }
}
