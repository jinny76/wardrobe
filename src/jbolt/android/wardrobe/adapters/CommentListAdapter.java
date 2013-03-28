package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.wardrobe.models.Comments;

public class CommentListAdapter extends BaseListAdapter {

    private List<Comments> models;
    private Context context;


    public CommentListAdapter(Context context, List<Comments> list) {
        this.models = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return models.size();
    }

    @Override
    public Object getItem(int id) {
        // TODO Auto-generated method stub
        return models.get(id);
    }

    @Override
    public long getItemId(int id) {
        // TODO Auto-generated method stub
        return id;
    }

    @Override
    public View getView(int id, View convertView, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        ViewHolder holder = new ViewHolder();
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.commenttext_item, null);
            holder.comments = (TextView) convertView.findViewById(R.id.comments);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Comments currComment = (Comments) getItem(id);
        holder.comments.setText(Html.fromHtml("<font color=#000000>来自&nbsp;</font><font color=#ff1493>" + currComment.getUserId() + "</font><font color=#000000>" + currComment.getComments() + "</font>"));
        return convertView;
    }

    public class ViewHolder {
        public TextView comments;
    }

}
