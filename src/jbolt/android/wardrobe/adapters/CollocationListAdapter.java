package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.wardrobe.models.CollocationModel;

import java.util.ArrayList;
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
    /**
     * 此处后面会抽象一个数据对象
     */
    private String[] createTimeArray = new String[]{
        "2013年\n3月5日", "2013年\n3月5日", "2013年\n3月5日", "2013年\n3月5日", "2013年\n3月5日",
        "2013年\n3月5日", "2013年\n3月5日", "2013年\n3月5日", "2013年\n3月5日", "2013年\n3月5日"
    };

    Context context;

    public CollocationListAdapter(Context context) {
        this.context = context;
        for (String createTime : createTimeArray) {
            CollocationModel model = new CollocationModel();
            model.setCreateDate(createTime);
            models.add(model);
        }
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.lblTime.setText(((CollocationModel) getItem(i)).getCreateDate());

        return convertView;
    }

    /**
     * 为了提高速度，会做一个holder对象作为视图控件的载体，每次从resource中去完之后，控件会保存在视图内
     */
    class ViewHolder {

        TextView lblTime;
    }

}
