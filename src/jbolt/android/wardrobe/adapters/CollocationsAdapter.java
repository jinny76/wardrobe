package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.models.Collocation;

import java.util.HashMap;
import java.util.TreeSet;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class CollocationsAdapter extends BaseAdapter {

    private Context context;
    private TreeSet<Collocation> models;

    public CollocationsAdapter(Context context, TreeSet<Collocation> models) {
        this.context = context;
        this.models = models;
    }

    public int getCount() {
        return models.size();
    }

    public Object getItem(int position) {
        return models.toArray()[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        Collocation collocation = (Collocation) getItem(position);
        ImageManager.getInstance().lazyLoadImage(ImageManager.getUrl(collocation.getId(), true), null, new HashMap<String, String>(), imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new Gallery.LayoutParams(90, 120));
        return imageView;
    }

}

