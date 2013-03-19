package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.utils.WidgetUtils;
import jbolt.android.wardrobe.data.DataFactory;
import jbolt.android.wardrobe.models.ArtifactItemModel;

/**
 * <p>Title: ClothesCatalogListAdapter</p>
 * <p>Description: ClothesCatalogListAdapter</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ClothesCatalogListAdapter extends BaseListAdapter implements View.OnTouchListener {

    private List<ArtifactItemModel> items = new ArrayList<ArtifactItemModel>();
    private Context context;
    private int upX;
    private int downX;

    public ClothesCatalogListAdapter(Context context) {
        this.context = context;
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int i) {
        return items.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        ArtifactItemModel item = (ArtifactItemModel) getItem(i);
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.clothescatalog_item, null);
            holder = new ViewHolder();
            holder.pic = (ImageView) convertView.findViewById(R.id.imgPic);
            holder.txtContent = (TextView) convertView.findViewById(R.id.txtContent);
            holder.btnArrow = (ImageButton) convertView.findViewById(R.id.btnDetails);
            holder.btnDelete = (ImageButton) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
            convertView.setOnTouchListener(this);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtContent.setText(item.getDescription());
        DataFactory.getSingle().loadArtifactImg(item, true);
        if (item.getThumbnail() != null) {
            holder.pic.setImageBitmap(item.getThumbnail());
        }
        holder.btnDelete.setVisibility(View.INVISIBLE);
        return convertView;
    }

    public void setItems(List<ArtifactItemModel> items) {
        this.items = items;
    }

    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    public void onLongPress(MotionEvent motionEvent) {
    }


    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE://滑动
                break;
            case MotionEvent.ACTION_DOWN://按下
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP://松开
                upX = (int) event.getX();
                if (Math.abs(upX - downX) > 20) {
                    ViewHolder holder = (ViewHolder) view.getTag();
                    WidgetUtils.setWidgetVisible(holder.btnDelete, !WidgetUtils.isWidgetVisible(holder.btnDelete));
                    WidgetUtils.setWidgetVisible(holder.btnArrow, !WidgetUtils.isWidgetVisible(holder.btnArrow));
                }
                upX = -1;
                downX = -1;
                break;
            default:
                break;
        }
        return true;
    }

    class ViewHolder {

        ImageView pic;
        TextView txtContent;
        ImageButton btnArrow;
        ImageButton btnDelete;
    }
}
