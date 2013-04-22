package jbolt.android.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.Toast;
import com.abolt.client.activity.ErrorMessageViewerActivity;
import jbolt.android.R;
import jbolt.android.base.AppContext;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MessageHandler {

    public static void showWarningMessage(Context context, String message) {
        if (message == null) {
            message = "<NULL>";
        }
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showWarningMessage(Context context, int messageId) {
        Toast toast;
        try {
            toast = Toast.makeText(context, messageId, Toast.LENGTH_LONG);
        } catch (Resources.NotFoundException e) {
            toast = Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showWarningMessage(Context context, final Exception e) {
        final String message =
                e.getMessage() == null ? AppContext.getString(R.string.common_nullpointer) : e.getMessage();
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.lblError)
                .setMessage(message)
                .setPositiveButton(
                        R.string.common_warning, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(AppContext.context, ErrorMessageViewerActivity.class);
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        intent.putExtra(ErrorMessageViewerActivity.ERROR, message + "\r\n" + sw.toString());
                        AppContext.context.startActivity(intent);
                    }
                })
                .setNegativeButton(
                        R.string.common_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public static AlertDialog showOptionDialog(
            Context context, int title, int message, DialogInterface.OnClickListener okListener,
            DialogInterface.OnClickListener cancelListener) {
        return new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.common_ok, okListener)
                .setNegativeButton(R.string.common_cancel, cancelListener)
                .show();
    }

}
