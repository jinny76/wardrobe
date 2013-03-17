package jbolt.android.utils;

import android.view.View;

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
}
