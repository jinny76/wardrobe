package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.base.AppContext;
import jbolt.android.base.BaseHandler;
import jbolt.android.base.GenericBaseActivity;
import jbolt.android.wardrobe.activities.CollocationRoomActivity;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.Collocation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 * <p>Description:所有的列表控件都需要有一个adapter对象继承BaseAdapter接口</p>
 *
 * @author Jinni
 */
public class CollocationListAdapter extends BaseListAdapter {


    private Map<String, TreeSet<Collocation>> models = new HashMap<String, TreeSet<Collocation>>();
    private Context context;

    private static int counter = 0;

    public CollocationListAdapter(Context context) {
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
            holder.gayCollocations = (Gallery) convertView.findViewById(R.id.gayCollocations);
            holder.gayCollocations.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap params = new HashMap();
                        params.put("id", ((Collocation) parent.getAdapter().getItem(position)).getId());
                        Intent intent = new Intent(AppContext.context, CollocationRoomActivity.class);
                        intent.putExtra(GenericBaseActivity.PARAM_KEY, params);
                        AppContext.context.startActivity(intent);
                    }
                });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String date = models.keySet().toArray(new String[]{})[i];
        TreeSet<Collocation> collocationModels = models.get(date);

        CollocationsAdapter adapter = new CollocationsAdapter(context, collocationModels);
        holder.gayCollocations.setAdapter(adapter);
        Date createDate = collocationModels.iterator().next().getCreateDate();
        holder.lblTime.setText(new SimpleDateFormat("yyyy年\nMM月dd日").format(createDate));
        if (adapter.getCount() > 1) {
            holder.gayCollocations.setSelection(1);
        }

        return convertView;
    }

    public void refeshData() {
        DataFactory.getSingle().loadAllCollocations(
            new BaseHandler() {
                @Override
                protected void handleMsg(Message msg) throws Exception {
                    if (msg.obj instanceof List) {
                        models = DataFactory.getSingle().groupByDate((List<Collocation>) msg.obj);
                        notifyDataSetChanged();
                    }
                }
            });
    }

    /**
     * 为了提高速度，会做一个holder对象作为视图控件的载体，每次从resource中去完之后，控件会保存在视图内
     */
    class ViewHolder {

        TextView lblTime;
        Gallery gayCollocations;
    }

    @Override
    public ImageView[] imagesNeedFree(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        return null;
    }
}
