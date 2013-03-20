package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.base.GenericBaseActivity;
import jbolt.android.utils.WidgetUtils;
import jbolt.android.wardrobe.activities.ClothesHangerActivity;
import jbolt.android.wardrobe.models.ArtifactTypeModel;
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
        final ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.catalog_item, null);
            holder = new ViewHolder();
            holder.img1 = (ImageButton) convertView.findViewById(R.id.img1);
            holder.img1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    callClothesCatalogActivity(holder.item.getType1());
                }
            });
            holder.img2 = (ImageButton) convertView.findViewById(R.id.img2);
            holder.img2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    callClothesCatalogActivity(holder.item.getType2());
                }
            });
            holder.img3 = (ImageButton) convertView.findViewById(R.id.img3);
            holder.img3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    callClothesCatalogActivity(holder.item.getType3());
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CatalogItemModel item = (CatalogItemModel) getItem(i);
        holder.item = item;
        if (item.getType1() != null) {
            holder.img1.setBackgroundResource(item.getType1().getCatalogDrawableId());
        }
        if (item.getType2() != null) {
            holder.img2.setBackgroundResource(item.getType2().getCatalogDrawableId());
        }
        WidgetUtils.setWidgetVisible(holder.img2, item.getType2() != null);
        if (item.getType3() != null) {
            holder.img3.setBackgroundResource(item.getType3().getCatalogDrawableId());
        }
        WidgetUtils.setWidgetVisible(holder.img3, item.getType3() != null);
        return convertView;
    }

    private void callClothesCatalogActivity(ArtifactTypeModel typeModel) {
        Intent intent = new Intent(context, ClothesHangerActivity.class);
        intent.putExtra(GenericBaseActivity.PARAM_KEY, typeModel.getId());
        context.startActivity(intent);
    }

    class ViewHolder {

        ImageButton img1;
        ImageButton img2;
        ImageButton img3;
        CatalogItemModel item;
    }
}
