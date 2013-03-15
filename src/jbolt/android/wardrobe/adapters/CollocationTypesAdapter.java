package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import jbolt.android.wardrobe.models.ArtifactTypeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class CollocationTypesAdapter extends BaseAdapter {

    private Context context;
    private List<ArtifactTypeModel> models;

    public CollocationTypesAdapter(Context context, List<ArtifactTypeModel> models)
        throws IllegalArgumentException, IllegalAccessException {
        this.context = context;
        this.models = models;
    }

    public int getCount() {
        return models.size();
    }

    public Object getItem(int position) {
        return models.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(((ArtifactTypeModel) getItem(position)).getDrawableId());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new Gallery.LayoutParams(80, 80));
        return imageView;
    }

}

