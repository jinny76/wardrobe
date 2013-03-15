package jbolt.android.wardrobe.base;

import android.view.View;
import android.widget.Button;
import jbolt.android.base.GenericBaseActivity;
import jbolt.android.utils.MessageHandler;

/**
 * <p>Title: WardrobeFrameActivity</p>
 * <p>Description: WardrobeFrameActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public abstract class WardrobeFrameActivity extends GenericBaseActivity {

    protected Button topReturn;
    protected Button btnTopHome;
    protected Button btnTopAdd;

    protected void initTopButtons() {
        topReturn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        back();
                    }
                });
        initSpecialTopButtons();
    }

    protected void initSpecialTopButtons() {
    }

    /**
     * 此处处理新增事件
     */
    protected void addNew() {
        MessageHandler.showWarningMessage(this, "Add New");
    }

    /**
     * 返回主页事件
     */
    protected void return2Home() {
        MessageHandler.showWarningMessage(this, "Return To Home");
    }

    /**
     * 返回
     */
    protected void back() {
        MessageHandler.showWarningMessage(this, "Back");
    }

}
