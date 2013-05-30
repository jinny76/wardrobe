package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.wardrobe.models.CollocationComments;
import jbolt.android.utils.WidgetUtils;

import java.util.List;

public class CommentListAdapter extends BaseListAdapter {

    private List<CollocationComments> models;
    private Context context;


    public CommentListAdapter(Context context, List<CollocationComments> list) {
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
        final CollocationComments currComment = (CollocationComments) getItem(id);
        
        SpannableString commentStr = (SpannableString) WidgetUtils.convertString2em("来自 用户"+currComment.getOwnerId().substring(currComment.getOwnerId().length()-6)+" "+currComment.getComments());
        
        
        commentStr.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        commentStr.setSpan(new ForegroundColorSpan(Color.parseColor("#ff1493")), 2, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        commentStr.setSpan(new ForegroundColorSpan(Color.BLACK), 12,  commentStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        
        
        holder.comments.setText(commentStr);
        return convertView;
    }

    public class ViewHolder {

        public TextView comments;
    }

}
