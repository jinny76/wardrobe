package jbolt.android.wardrobe.activities;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import jbolt.android.R;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.adapters.CommentListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.models.Collocation;


public class ChannelDetailActivity extends WardrobeFrameActivity {

    private View listTop;
    private ListView commentList;
    
    private Collocation itemDetail;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        // TODO Auto-generated method stub
        setContentView(R.layout.channeldetail);
        itemDetail = (Collocation) this.params;
        //ImageManager.getInstance().lazyLoadImage(url, null, new HashMap(), view);
        
        CommentListAdapter commentAdapter = new CommentListAdapter(this, itemDetail.getComments());
        listTop = View.inflate(this, R.layout.imagedetail, null);
        commentList = (ListView) findViewById(R.id.commentlist);
        commentList.addHeaderView(listTop);
        // add header and footer before setting adapter
        commentList.setAdapter(commentAdapter);
    }
    

    


}
