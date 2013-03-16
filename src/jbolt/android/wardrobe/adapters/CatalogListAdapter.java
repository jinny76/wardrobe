package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.wardrobe.models.CatalogItemModel;

/**
 * <p>Title: CatalogListAdapter</p>
 * <p>Description: CatalogListAdapter</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class CatalogListAdapter extends BaseListAdapter {

    private Context context;
    private List<CatalogItemModel> models = new ArrayList<CatalogItemModel>();

    public CatalogListAdapter(Context context) {
        this.context = context;
    }

    public void setModels(List<CatalogItemModel> models) {
        this.models = models;
    }

    public int getCount() {
        return models.size();
    }

    public Object getItem(int i) {
        return models.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.catalog_item, null);
            holder = new ViewHolder();
            holder.img1 = (ImageView) convertView.findViewById(R.id.img1);
            holder.img2 = (ImageView) convertView.findViewById(R.id.img2);
            holder.img3 = (ImageView) convertView.findViewById(R.id.img3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CatalogItemModel item = (CatalogItemModel) getItem(i);
        if (item.getType1() != null) {
            holder.img1.setImageResource(item.getType1().getCatalogDrawableId());
        }
        if (item.getType2() != null) {
            holder.img2.setImageResource(item.getType2().getCatalogDrawableId());
        }
        if (item.getType3() != null) {
            holder.img3.setImageResource(item.getType3().getCatalogDrawableId());
        }
        return convertView;
    }

    class ViewHolder {

        ImageView img1;
        ImageView img2;
        ImageView img3;
    }
}
