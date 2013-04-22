package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.base.AppContext;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.activities.MessageActivity;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
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

    private Integer relationType;
    private List<Person> relations = new ArrayList<Person>();
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

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.personal_relation, null);
            holder.imgPortrait = (ImageView) convertView.findViewById(R.id.imgPortrait);
            holder.txtNick = (TextView) convertView.findViewById(R.id.txtNick);
            holder.pnl1 = (LinearLayout) convertView.findViewById(R.id.pnl1);
            holder.pnl2 = (LinearLayout) convertView.findViewById(R.id.pnl2);
            holder.pnl3 = (LinearLayout) convertView.findViewById(R.id.pnl3);
            holder.btnAddFriend1 = (Button) convertView.findViewById(R.id.btnAddFriend1);
            holder.btnAddFriend2 = (Button) convertView.findViewById(R.id.btnAddFriend2);
            holder.btnMail1 = (Button) convertView.findViewById(R.id.btnMail1);
            holder.btnMail2 = (Button) convertView.findViewById(R.id.btnMail2);
            holder.btnMail3 = (Button) convertView.findViewById(R.id.btnMail3);
            holder.btnCancelAttention1 = (Button) convertView.findViewById(R.id.btnCancelAttention1);
            holder.btnBlacklist2 = (Button) convertView.findViewById(R.id.btnBlacklist2);
            holder.btnCancelFriend3 = (Button) convertView.findViewById(R.id.btnCancelFriend3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Person person = (Person) getItem(i);
        ImageManager.getInstance().lazyLoadImage(ImageManager.getUrl(person.getId(), true), null, new HashMap<String, String>(), holder.imgPortrait);
        holder.txtNick.setText(person.getNick());

        switch (relationType) {
            case 1:
                holder.pnl1.setVisibility(View.VISIBLE);
                holder.pnl2.setVisibility(View.INVISIBLE);
                holder.pnl3.setVisibility(View.INVISIBLE);
                holder.btnAddFriend1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addFriend(person.getId());
                    }
                });
                holder.btnMail1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMail(person.getId());
                    }
                });
                holder.btnCancelAttention1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelAttention(person.getId());
                    }
                });
                break;
            case 2:
                holder.pnl1.setVisibility(View.INVISIBLE);
                holder.pnl2.setVisibility(View.VISIBLE);
                holder.pnl3.setVisibility(View.INVISIBLE);
                holder.btnAddFriend2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addFriend(person.getId());
                    }
                });
                holder.btnMail2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMail(person.getId());
                    }
                });
                holder.btnBlacklist2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addBlacklist(person.getId());
                    }
                });
                break;
            case 3:
                holder.pnl1.setVisibility(View.INVISIBLE);
                holder.pnl2.setVisibility(View.INVISIBLE);
                holder.pnl3.setVisibility(View.VISIBLE);
                holder.btnMail3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMail(person.getId());
                    }
                });
                holder.btnCancelFriend3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelFriend(person.getId());
                    }
                });
                break;
        }

        return convertView;
    }

    private void cancelFriend(String userId) {
        MessageHandler.showWarningMessage(AppContext.context, "Cancel Friend");

    }

    private void addBlacklist(String userId) {
        MessageHandler.showWarningMessage(AppContext.context, "Add Blacklist");

    }

    private void cancelAttention(String userId) {
        MessageHandler.showWarningMessage(AppContext.context, "Cancel Attention");
    }

    private void sendMail(String userId) {
        ((WardrobeFrameActivity) AppContext.context).startActivity(MessageActivity.class, userId);
    }

    private void addFriend(String userId) {
        MessageHandler.showWarningMessage(AppContext.context, "Add Friend");
    }

    class ViewHolder {

        ImageView imgPortrait;
        TextView txtNick;
        LinearLayout pnl1;
        LinearLayout pnl2;
        LinearLayout pnl3;
        Button btnAddFriend1;
        Button btnCancelAttention1;
        Button btnMail1;
        Button btnBlacklist2;
        Button btnAddFriend2;
        Button btnMail2;
        Button btnCancelFriend3;
        Button btnMail3;
    }
}
