package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.wardrobe.models.PersonMessages;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: MessageListAdapter</p>
 * <p>Description: MessageListAdapter</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class MessageListAdapter extends BaseListAdapter {

    private List<PersonMessages> messages = new ArrayList<PersonMessages>();
    private Context context;

    public MessageListAdapter(Context context) {
        this.context = context;
    }

    public void setMessages(List<PersonMessages> messages) {
        this.messages = messages;
    }

    public int getCount() {
        return messages.size();
    }

    public Object getItem(int i) {
        return messages.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        PersonMessages item = (PersonMessages) getItem(i);
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.personmessage_item, null);
            holder = new ViewHolder();
            holder.txtItem = (TextView) convertView.findViewById(R.id.txtItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!StringUtils.isEmpty(item.getMsg())) {
            holder.txtItem.setText(item.getMsg());
        }
        return convertView;
    }

    class ViewHolder {

        TextView txtItem;
    }
}
