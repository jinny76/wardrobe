package jbolt.android.wardrobe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import jbolt.android.R;
import jbolt.android.adapters.BaseListAdapter;
import jbolt.android.base.GenericBaseActivity;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.WidgetUtils;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.activities.ShowBigPicActivity;
import jbolt.android.wardrobe.models.ArtifactItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Title: ClothesCatalogListAdapter</p>
 * <p>Description: ClothesCatalogListAdapter</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ClothesCatalogListAdapter extends BaseListAdapter implements View.OnTouchListener {

    private List<ArtifactItem> items = new ArrayList<ArtifactItem>();
    private Context context;
    private int upX;
    private int downX;
    private View.OnClickListener deleteListener;

    public ClothesCatalogListAdapter(Context context) {
        this.context = context;
    }

    public void setDeleteListener(View.OnClickListener deleteListener) {
        this.deleteListener = deleteListener;
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
        ArtifactItem item = (ArtifactItem) getItem(i);
        if (convertView == null || convertView.getTag() == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.clothescatalog_item, null);
            holder = new ViewHolder();
            holder.pic = (ImageView) convertView.findViewById(R.id.imgPic);
            holder.pic.setOnClickListener(
                    new OnClickListener() {
                        public void onClickAction(View view) {
                            ArtifactItem item = (ArtifactItem) view.getTag();
                            HashMap params = new HashMap();
                            params.put("type", item.getType());
                            params.put("id", item.getId());
                            ((GenericBaseActivity) context).startActivity(ShowBigPicActivity.class, params);
                        }
                    });
            holder.txtContent = (TextView) convertView.findViewById(R.id.txtContent);
            holder.btnArrow = (ImageButton) convertView.findViewById(R.id.btnDetails);
            holder.btnDelete = (ImageButton) convertView.findViewById(R.id.btnDelete);
            holder.pnlContainer = convertView.findViewById(R.id.pnlContainer);
            convertView.setTag(holder);
            convertView.setOnTouchListener(this);
            holder.pnlContainer.setOnTouchListener(this);
            holder.pnlContainer.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.btnDelete.setTag(item);
        holder.txtContent.setText(item.getDescription());
        ImageManager.getInstance().lazyLoadImage(ImageManager.getUrl(item.getId(), true), null, new HashMap<String, String>(), holder.pic);
        holder.btnArrow.setVisibility(View.VISIBLE);
        holder.btnDelete.setVisibility(View.INVISIBLE);
        return convertView;
    }

    public void setItems(List<ArtifactItem> items) {
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
                if (Math.abs(upX - downX) > 10) {
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
        View pnlContainer;
    }
}
