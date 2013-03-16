package jbolt.android.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ToggleButton<T> extends android.widget.ToggleButton {

    ToggleButtonGroup group = null;
    private T userObj;

    public ToggleButton(Context context) {
        super(context);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            super.setChecked(checked);
            if (checked && group != null) {
                for (ToggleButton toggleButton : group.getButtons()) {
                    if (toggleButton != this) {
                        toggleButton.changeChecked(false);
                    }
                }
            }
        }
    }

    public void changeChecked(boolean checked) {
        super.setChecked(checked);
    }

    public ToggleButtonGroup getGroup() {
        return group;
    }

    public void setGroup(ToggleButtonGroup group) {
        this.group = group;
    }

    public T getUserObj() {
        return userObj;
    }

    public void setUserObj(T userObj) {
        this.userObj = userObj;
    }
}
