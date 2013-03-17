package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.wardrobe.activities.CollocationActivity;
import jbolt.android.wardrobe.activities.CollocationRoomActivity;
import jbolt.android.wardrobe.models.ArtifactItemModel;
import jbolt.android.wardrobe.models.CollocationModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 * <p>Description:所有的列表控件都需要有一个adapter对象继承BaseAdapter接口</p>
 *
 * @author Jinni
 */
public class CollocationListAdapter extends BaseListAdapter {


    private List<CollocationModel> models = new ArrayList<CollocationModel>();
    private Context context;

    private static int counter = 0;

    public CollocationListAdapter(Context context, List<CollocationModel> models) {
        this.models = models;
        this.context = context;
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

    /**
     * 列表控件内部可以是一个视图组合，视图在此函数中返回
     *
     * @param i           行索引
     * @param convertView 主视图对象
     * @param viewGroup
     * @return 视图
     */
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.collocation_item, null);
            holder = new ViewHolder();
            holder.lblTime = (TextView) convertView.findViewById(R.id.lblTime);
            holder.pnlItemsList = (RelativeLayout) convertView.findViewById(R.id.pnlItemsList);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.pnlItemsList.removeAllViews();
        }

        final CollocationModel currCollocation = (CollocationModel) getItem(i);
        holder.lblTime.setText(currCollocation.getCreateDate());
        Resources resources = context.getResources();
        ImageView lastImg = null;
        for (final ArtifactItemModel item : currCollocation.getItems()) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
            ImageView imgItem = new ImageView(context);
            imgItem.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgItem.setId(counter++);
            imgItem.setBackgroundDrawable(resources.getDrawable(item.getDrawable()));
            imgItem.setTag(item);
            if (lastImg != null) {
                layoutParams.addRule(RelativeLayout.RIGHT_OF, lastImg.getId());
            }
            imgItem.setClickable(true);
            imgItem.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        ((CollocationActivity) context).startActivity(CollocationRoomActivity.class, new HashMap());
                    }
                });
            holder.pnlItemsList.addView(imgItem, layoutParams);
            lastImg = imgItem;
        }

        return convertView;
    }

    /**
     * 为了提高速度，会做一个holder对象作为视图控件的载体，每次从resource中去完之后，控件会保存在视图内
     */
    class ViewHolder {

        TextView lblTime;
        RelativeLayout pnlItemsList;
    }

    @Override
    public ImageView[] imagesNeedFree(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder != null && holder.pnlItemsList != null) {
            ImageView[] imageViews = new ImageView[holder.pnlItemsList.getChildCount()];
            for (int i = 0; i < holder.pnlItemsList.getChildCount(); i++) {
                imageViews[i] = (ImageView) holder.pnlItemsList.getChildAt(i);
            }
            return imageViews;
        }
        return null;
    }
}
