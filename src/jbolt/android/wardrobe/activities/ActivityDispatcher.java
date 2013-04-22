package jbolt.android.wardrobe.activities;

import android.content.Context;
import android.content.Intent;
import java.io.Serializable;
import java.util.HashMap;
import jbolt.android.base.GenericBaseActivity;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;


/**
 * <p>Title: ActivityDispatcher</p>
 * <p>Description: ActivityDispatcher</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ActivityDispatcher {

    public static void return2Home(Context context) {
        if (!(context instanceof WardrobeCatalogActivity)) {
            Intent intent = new Intent(context.getApplicationContext(), WardrobeCatalogActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    public static void openShow(Context context) {
        if (!(context instanceof ChannelShowActivity)) {
            Intent intent = new Intent(context.getApplicationContext(), ChannelShowActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    public static void openPersonalInfo(Context context) {
        if (!(context instanceof PersonalInfoActivity)) {
            Intent intent = new Intent(context.getApplicationContext(), PersonalInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    /**
     * 添加搭配事件
     */
    public static void collocate(Context context) {
        if (!(context instanceof CollocationActivity)) {
            startActivity(context, CollocationActivity.class, new HashMap());
        }
    }

    public static void openPersonalCentre(Context context) {
        if (!(context instanceof PersonalCentreActivity)) {
            startActivity(context, PersonalCentreActivity.class, new HashMap());
        }
    }

    /**
     * 调用其他的模块，不需要返回数据。
     *
     * @param activityClass 需要调用的模块类名
     * @param params        传递的参数，必须是可以序列化的对象，默认存储到params属性中。
     */
    public static void startActivity(Context context, Class activityClass, Serializable params) {
        Intent intent = new Intent(context, activityClass);
        intent.putExtra(GenericBaseActivity.PARAM_KEY, params);

        context.startActivity(intent);
    }

    public static void callClothesCatalogActivity(Context context, String type) {
        if (ClothesCatalogAbstractActivity.hangerView) {
            Intent intent = new Intent(context, ClothesHangerActivity.class);
            intent.putExtra(GenericBaseActivity.PARAM_KEY, type);
            ((GenericBaseActivity) context).startActivityForResult(intent, WardrobeFrameActivity.SWITCH_HANGER);
        } else {
            Intent intent = new Intent(context, ClothesCatalogActivity.class);
            intent.putExtra(GenericBaseActivity.PARAM_KEY, type);
            ((GenericBaseActivity) context).startActivityForResult(intent, WardrobeFrameActivity.SWITCH_HANGER);
        }
    }

    public static void channelShow(Context context) {
        if (!(context instanceof ChannelShowActivity)) {
            Intent intent = new Intent(context.getApplicationContext(), ChannelShowActivity.class);
            context.startActivity(intent);
        }
    }


    public static void commentsDetail(Context context) {
        if (!(context instanceof ChannelDetailActivity)) {
            startActivity(context, ChannelDetailActivity.class, new HashMap());
        }
    }

}
