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
                String emStr = content.substring(startIndex, endIndex + 1);
                Drawable imgEm = AppContext.context.getResources().getDrawable(R.drawable.em1);
                imgEm.setBounds(0, 0, imgEm.getIntrinsicWidth(), imgEm.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(imgEm, ImageSpan.ALIGN_BASELINE);
                spannable.setSpan(span, startIndex, endIndex + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        return spannable;
    }
}
