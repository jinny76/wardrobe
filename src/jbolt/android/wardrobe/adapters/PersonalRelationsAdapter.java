package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.models.Person;

/**
 * <p>Title: PersonalRelationsAdapter</p>
 * <p>Description: PersonalRelationsAdapter</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class PersonalRelationsAdapter extends BaseListAdapter {

    private List<Person> relations;
    private Context context;

    public PersonalRelationsAdapter(Context context) {
        this.context = context;
    }

    public List<Person> getRelations() {
        return relations;
    }

    public void setRelations(List<Person> relations) {
        this.relations = relations;
    }

    public int getCount() {
        return relations.size();
    }

    public Object getItem(int i) {
        return relations.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.personal_relation, null);
            holder.portrait = (ImageView) convertView.findViewById(R.id.portrait);
            holder.nickName = (TextView) convertView.findViewById(R.id.nick);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Person person = (Person) getItem(i);
        ImageManager.getInstance().lazyLoadImage(ImageManager.getUrl(person.getId(), true), null, new HashMap<String, String>(), holder.portrait);
        String lblNick = context.getResources().getString(R.string.lblNick);
        holder.nickName.setText(lblNick + person.getNick());
        return convertView;
    }

    class ViewHolder {
        ImageView portrait;
        TextView nickName;
        Button btnPrivateMsg;
        Button btnCancelFriend;
        Button btnAddFriend;
        Button btnBlackList;
        Button btnCancelAttention;
    }
}
