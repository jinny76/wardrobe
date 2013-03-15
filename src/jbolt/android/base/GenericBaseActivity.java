package jbolt.android.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.Date;
import jbolt.android.utils.Log;
import jbolt.android.utils.MessageHandler;

public abstract class GenericBaseActivity extends Activity {

    private Intent recentIntent;
    private Date recentIntentInvokeTime;

    public static final int INTENT_INTERVAL = 10 * 1000;

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
        try {
            onCreateActivity(savedInstanceState);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
            MessageHandler.showWarningMessage(AppContext.context, e);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        /*if (!intent.equals(recentIntent) || (
            recentIntentInvokeTime != null
                && System.currentTimeMillis() - recentIntentInvokeTime.getTime() > INTENT_INTERVAL)) {
            recentIntent = intent;
            recentIntentInvokeTime = new Date();*/
        super.startActivityForResult(intent, requestCode);
        //}
    }

    @Override
    public void startActivity(Intent intent) {
        /*if (!intent.equals(recentIntent) || (
            recentIntentInvokeTime != null
                && System.currentTimeMillis() - recentIntentInvokeTime.getTime() > INTENT_INTERVAL)) {
            recentIntent = intent;
            recentIntentInvokeTime = new Date();*/
        super.startActivity(intent);
        //}
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
