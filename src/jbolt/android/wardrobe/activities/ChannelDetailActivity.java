package jbolt.android.wardrobe.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import jbolt.android.R;
import jbolt.android.wardrobe.adapters.CommentListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.data.ChannelShowData;


public class ChannelDetailActivity extends WardrobeFrameActivity {

    private View listTop;
    private ListView commentList;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        // TODO Auto-generated method stub
        setContentView(R.layout.channeldetail);
        ChannelShowData commentsData = new ChannelShowData();
        CommentListAdapter commentAdapter = new CommentListAdapter(this, commentsData.getComments());
        listTop = View.inflate(this, R.layout.imagedetail, null);
        commentList = (ListView) findViewById(R.id.commentlist);
        commentList.addHeaderView(listTop);
        // add header and footer before setting adapter
        commentList.setAdapter(commentAdapter);
    }


}
