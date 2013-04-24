package jbolt.android.wardrobe.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.weibo.sdk.android.Oauth2AccessToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.HashMap;
import jbolt.android.R;
import jbolt.android.base.AppConfig;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.listeners.OnClickListener;
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
    Spinner spnGender;
    EditText txtMail;
    EditText txtMobile;
    TextView txtSina;
    String sinaUser = null;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.personal_info);

        txtNick = (TextView) findViewById(R.id.txtNick);
        txtNick.setText(AppContext.getUser().getNick());

        imgPortrait = (ImageView) findViewById(R.id.imgPortrait);
        imgPortrait.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                doChangePortrait();
            }
        });

        ImageManager.getInstance().lazyLoadImage(
                ImageManager.getUrl(AppContext.getUser().getId(), true), null, new HashMap<String, String>(), imgPortrait);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                save();
            }
        });

        txtSignature = (EditText) findViewById(R.id.txtSignature);
        txtNickName = (EditText) findViewById(R.id.txtNickName);
        txtBirthday = (EditText) findViewById(R.id.txtBirthday);
        txtBirthday.setText(AppContext.getUser().getBirthday());
        txtGender = (EditText) findViewById(R.id.txtGender);
        spnGender = (Spinner) findViewById(R.id.spnGender);
        ArrayAdapter<CharSequence> genderAdapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item, new String[]{"男", "女", "不告诉你"});
        spnGender.setAdapter(genderAdapter);
        spnGender.setPrompt(getResources().getString(R.string.msg_select_gender));
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtGender.setText((CharSequence) spnGender.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        txtGender.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickAction(View v) {
                spnGender.performClick();
            }
        });
        txtGender.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    spnGender.performClick();
                }
            }
        });

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
            txtSina.setOnClickListener(new OnClickListener() {
                @Override
                public void onClickAction(View v) {
                    WeiboManagerSinaImpl.getInstance().doAuthen(new BaseHandler() {
                        @Override
                        protected void handleMsg(Message msg) throws Exception {
                            if (msg != null) {
                                if (msg.obj instanceof SinaUser) {
                                    sinaUser = ((SinaUser) msg.obj).getName();
                                } else if (msg.obj instanceof String) {
                                    sinaUser = (String) msg.obj;
                                }
                                updateUI(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtSina.setText(sinaUser);
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }

        final Calendar cd = Calendar.getInstance();
        cd.set(1980, 0, 1);
        txtBirthday.setOnClickListener(new OnClickListener() {
            public void onClickAction(View v) {
                showDatePicker(cd);
            }
        });
        txtBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker(cd);
                }
            }
        });
    }

    private void showDatePicker(Calendar cd) {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtBirthday.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, cd.get(Calendar.YEAR), cd.get(Calendar.MONTH), cd.get(Calendar.DAY_OF_MONTH)).show();
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
        person.setBirthday(birthday);
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
