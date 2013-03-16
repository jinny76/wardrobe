package jbolt.android.adapters;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public abstract class BaseListAdapter extends BaseAdapter implements AbsListView.RecyclerListener {

    public ImageView[] imagesNeedFree(View view) {
        return null;
    }

    /**
     * 清除指定View的图片缓存
     *
     * @param view
     */
    public void onMovedToScrapHeap(View view) {
        ImageView[] imageViews = imagesNeedFree(view);
        if (imageViews != null) {
            for (ImageView imageView : imageViews) {
                final Drawable drawable = imageView.getDrawable();
                if (drawable instanceof BitmapDrawable) {
                    ((BitmapDrawable) drawable).getBitmap().recycle();
                }
            }
        }
    }

}
