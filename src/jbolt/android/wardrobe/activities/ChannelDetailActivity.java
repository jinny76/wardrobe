package jbolt.android.wardrobe.activities;

import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
    
    private ImageView bigPic;
    private TextView likeNum;
    private TextView commentNum;
    private TextView describeTxt;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        // TODO Auto-generated method stub
        setContentView(R.layout.channeldetail);
        itemDetail = (Collocation) this.params;
        
        describeTxt = (TextView) findViewById(R.id.detaildescribe);
        
        
        
        CommentListAdapter commentAdapter = new CommentListAdapter(this, itemDetail.getComments());
        listTop = View.inflate(this, R.layout.imagedetail, null);
        
        bigPic = (ImageView) listTop.findViewById(R.id.detailimage);
        likeNum = (TextView) listTop.findViewById(R.id.likeNum);
        commentNum = (TextView) listTop.findViewById(R.id.discuNum);
        
        if (itemDetail.getAdoreCounter() == null){
         	likeNum.setText("0");
         }else{
         	likeNum.setText(Long.toString(itemDetail.getAdoreCounter()));
         }
         
         if (itemDetail.getCommentsCounter() == null){
         	commentNum.setText("0");
         }else{
         	commentNum.setText(Long.toString(itemDetail.getCommentsCounter()));
         }
         
         if (itemDetail.getDescription() == null){
         	describeTxt.setText(R.string.msg_empty_describe);
         }else{
         	describeTxt.setText(itemDetail.getDescription());
         }

        
        ImageManager.getInstance().lazyLoadImage(ImageManager.getUrl(itemDetail.getId(), false), null, new HashMap(), bigPic);
        commentList = (ListView) findViewById(R.id.commentlist);
        commentList.addHeaderView(listTop);
        // add header and footer before setting adapter
        commentList.setAdapter(commentAdapter);
    }
    

    


}
