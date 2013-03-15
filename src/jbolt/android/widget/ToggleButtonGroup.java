package jbolt.android.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ToggleButtonGroup {

    private List<ToggleButton> buttons = new ArrayList<ToggleButton>();

    public ToggleButtonGroup(ToggleButton[] toggleButtons) {
        buttons = Arrays.asList(toggleButtons);
        initButtons();
    }

    public ToggleButtonGroup() {
    }

    public void initButtons() {
        Boolean checked = null;
        for (ToggleButton toggleButton : buttons) {
            toggleButton.setGroup(this);
            if (checked != null) {
                toggleButton.setChecked(false);
            } else if (toggleButton.isChecked()) {
                checked = true;
            }
        }
    }

    public List<ToggleButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<ToggleButton> buttons) {
        this.buttons = buttons;
    }
}
