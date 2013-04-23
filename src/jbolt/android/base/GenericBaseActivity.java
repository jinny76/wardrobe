package jbolt.android.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import jbolt.android.utils.Log;
import jbolt.android.utils.MessageHandler;

import java.io.Serializable;

public abstract class GenericBaseActivity extends Activity {

    public static final String PARAM_KEY = "PARAM_KEY";

    protected Serializable params = null;
    protected Handler handler = new Handler();

    @Override
    protected void onStart() {
        super.onStart();
        AppContext.context = this;
        i(AppContext.context.toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AppContext.context = this;
        i(AppContext.context.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppContext.context = this;
        i(AppContext.context.toString());
    }

    @Override
    @Deprecated
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.setContext(this);
        i(AppContext.context.toString());
        this.params = this.getIntent().getSerializableExtra(PARAM_KEY);

        try {
            onCreateActivity(savedInstanceState);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        try {
            super.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    /**
     * 调用其他模块，提供一个调用事件名，需要返回数据。
     *
     * @param activityClass 需要调用的模块类名
     * @param params        传递的参数，必须是可以序列化的对象，默认存储到params属性中。
     * @param requestCode   事件名
     */
    public void startActivity(Class activityClass, Serializable params, int requestCode) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(PARAM_KEY, params);

        startActivityForResult(intent, requestCode);
    }

    /**
     * 调用其他的模块，不需要返回数据。
     *
     * @param activityClass 需要调用的模块类名
     * @param params        传递的参数，必须是可以序列化的对象，默认存储到params属性中。
     */
    public void startActivity(Class activityClass, Serializable params) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(PARAM_KEY, params);

        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        try {
            super.startActivity(intent);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    @Override
    @Deprecated
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            onReceiveResult(requestCode, resultCode, data);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    protected void updateUI(Runnable task) {
        handler.post(task);
    }

    protected void onReceiveResult(int requestCode, int resultCode, Intent data) throws Exception {
    }

    protected abstract void onCreateActivity(Bundle savedInstanceState) throws Exception;

    protected String tag() {
        return this.getClass().getName();
    }

    public void d(String message) {
        Log.d(tag(), message);
    }

    public void d(String message, Throwable throwable) {
        Log.d(tag(), message, throwable);
    }

    public void i(String message) {
        Log.i(tag(), message);
    }

    public void i(String message, Throwable throwable) {
        Log.i(tag(), message, throwable);
    }

    public void w(String message) {
        Log.w(tag(), message);
    }

    public void w(String message, Throwable throwable) {
        Log.w(tag(), message, throwable);
    }

    public void e(String message) {
        Log.e(tag(), message);
    }

    public void e(String message, Throwable throwable) {
        Log.e(tag(), message, throwable);
    }

}
