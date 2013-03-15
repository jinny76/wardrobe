package jbolt.android.utils.image;

import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import java.util.Map;
import jbolt.android.base.BaseHandler;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ImageLoadHandler extends BaseHandler {

    private String imageUrl;
    private Map<String, String> params;
    private ImageView imageView;

    public ImageLoadHandler(String imageUrl, Map<String, String> param, ImageView imageView) {
        this.imageUrl = imageUrl;
        this.imageView = imageView;
        this.params = param;
    }

    @Override
    public void handleMsg(Message msg) throws Exception {
        Object result = msg.obj;
        if (result instanceof Exception) {
            Log.e(this.getClass().getName(), ((Exception) result).getMessage(), (Exception) result);
            //MessageHandler.showWarningMessage(CurrContext.context, (Exception) result);
        } else {
            Drawable drawable = (Drawable) msg.obj;
            if (imageView != null) {
                imageView.setImageDrawable(drawable);
            }
            ImageCache.getInstance().put(imageUrl, params, drawable);
        }
    }

    public void unlinkView() {
        this.imageView = null;
    }
}
