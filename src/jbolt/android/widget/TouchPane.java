package jbolt.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class TouchPane extends FrameLayout {

    GestureDetector gestureDetector = null;

    public TouchPane(Context context) {
        super(context);
    }

    public TouchPane(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchPane(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result = super.dispatchTouchEvent(ev);

        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(ev);
        }

        return result;
    }

    public void setGestureDetector(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }
}
