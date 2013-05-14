package jbolt.android.utils;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import jbolt.android.R;
import jbolt.android.base.AppContext;

/**
 * <p>Title: WidgetUtils</p>
 * <p>Description: WidgetUtils</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class WidgetUtils {

    static int[] ems = new int[]{
            R.drawable.face_14,
            R.drawable.face_15,
            R.drawable.face_16,
            R.drawable.face_17,
            R.drawable.face_18,
            R.drawable.face_19,
            R.drawable.face_20,
            R.drawable.face_21,
            R.drawable.face_22,
            R.drawable.face_23,
            R.drawable.face_24,
            R.drawable.face_25,
            R.drawable.face_26,
            R.drawable.face_27,
            R.drawable.face_28,
            R.drawable.face_29,
            R.drawable.face_30,
            R.drawable.face_31,
            R.drawable.face_32,
            R.drawable.face_33,
            R.drawable.face_34,
            R.drawable.face_35,
            R.drawable.face_36,
            R.drawable.face_37,
            R.drawable.face_38,
            R.drawable.face_39,
            R.drawable.face_40,
            R.drawable.face_41,
            R.drawable.face_42,
            R.drawable.face_43,
            R.drawable.face_44,
            R.drawable.face_45,
            R.drawable.face_46,
            R.drawable.face_47,
            R.drawable.face_48,
            R.drawable.face_49,
            R.drawable.face_50,
            R.drawable.face_51,
            R.drawable.face_52,
            R.drawable.face_53,
            R.drawable.face_54,
            R.drawable.face_55,
            R.drawable.face_56,
            R.drawable.face_57,
            R.drawable.face_58,
            R.drawable.face_59,
            R.drawable.face_60,
            R.drawable.face_61,
            R.drawable.face_62,
            R.drawable.face_63,
            R.drawable.face_64,
            R.drawable.face_65,
            R.drawable.face_66,
            R.drawable.face_67,
            R.drawable.face_68
    };

    public static boolean isWidgetVisible(View widget) {
        return widget.getVisibility() == View.VISIBLE;
    }

    public static void setWidgetVisible(View widget, boolean visible) {
        if (visible) {
            widget.setVisibility(View.VISIBLE);
        } else {
            widget.setVisibility(View.INVISIBLE);
        }
    }

    public static Spannable convertString2em(String content) {
        SpannableString spannable = new SpannableString(content);
        int startIndex = 0;
        int endIndex = 0;
        while (startIndex != -1) {
            startIndex = content.indexOf("/[", endIndex);
            endIndex = content.indexOf("]", startIndex);

            if (startIndex != -1 && endIndex != -1) {
                String[] emStr = StringUtilities.splitString2Array(content.substring(startIndex + 2, endIndex), "|");
                int x = Integer.parseInt(emStr[0]);
                int y = Integer.parseInt(emStr[1]);

                if (y < 1) {
                    y = 1;
                } else if (y > 5) {
                    y = 5;
                }
                if (x < 1) {
                    x = 1;
                } else if (x > 11) {
                    x = 11;
                }
                Drawable imgEm = AppContext.context.getResources().getDrawable(ems[(y - 1) * 11 + x - 1]);
                imgEm.setBounds(0, 0, imgEm.getIntrinsicWidth(), imgEm.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(imgEm, ImageSpan.ALIGN_BASELINE);
                spannable.setSpan(span, startIndex, endIndex + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        return spannable;
    }
}
