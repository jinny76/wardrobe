package jbolt.android.wardrobe.activities;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import jbolt.android.R;
import jbolt.android.base.BaseHandler;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.adapters.CommentListAdapter;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.models.Collocation;
import jbolt.android.wardrobe.models.CollocationComments;
import jbolt.android.wardrobe.models.RelationsType;

import jbolt.android.wardrobe.service.impl.*;




public class ChannelDetailActivity extends WardrobeFrameActivity {

    private View listTop;
    private ListView commentList;
    
    private Collocation itemDetail;
    
    private List<CollocationComments> commentData;
    private CommentListAdapter commentAdapter; 
    
    private ImageView bigPic;
    private TextView likeNum;
    private TextView commentNum;
    private TextView describeTxt;
    private TextView nickName;
    private ImageView ownerPortrait;
    
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        // TODO Auto-generated method stub
        setContentView(R.layout.channeldetail);
        itemDetail = (Collocation) this.params;
        
       
        
        
               
       
        listTop = View.inflate(this, R.layout.imagedetail, null);
        
        int aa = itemDetail.getComments().size();
        //list footer layout
        LinearLayout commentArea = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);  
        //此处相当于布局文件中的Android:layout_gravity属性  
        lp.gravity = Gravity.RIGHT;  
        
        commentArea.setOrientation(LinearLayout.HORIZONTAL);
        commentArea.setBackgroundColor(Color.WHITE);
        EditText commentEdit = new EditText(this);
        Button commentCommit = new Button(this);
        commentCommit.setText("发送");
        commentCommit.setLayoutParams(lp);
        commentEdit.setLayoutParams(lp);
        //此处相当于布局文件中的Android：gravity属性  
        commentCommit.setGravity(Gravity.CENTER);
        commentArea.addView(commentEdit);
        commentArea.addView(commentCommit);
        
        
        

        ownerPortrait = (ImageView) findViewById(R.id.userportrait);
        bigPic = (ImageView) listTop.findViewById(R.id.detailimage);
        likeNum = (TextView) listTop.findViewById(R.id.likeNum);
        commentNum = (TextView) listTop.findViewById(R.id.discuNum);
        describeTxt = (TextView) listTop.findViewById(R.id.imagedescribe);
        nickName = (TextView) findViewById(R.id.nickName);
        
       
        
        ImageManager.getInstance().lazyLoadImage(ImageManager.getUrl(itemDetail.getOwnerId(), true), null, new HashMap(), ownerPortrait);
        
        if (itemDetail.getNickName() == null){
        	nickName.setText("用户"+itemDetail.getOwnerId().substring(itemDetail.getOwnerId().length()-6));
        }else{
        	nickName.setText(itemDetail.getNickName());
        }
        
        
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
        //commentList.addFooterView(commentArea);
        // add header and footer before setting adapter
        
        CollocationManagerDefaultImpl.loadComments(itemDetail.getId(), new BaseHandler(){

			@Override
			protected void handleMsg(Message msg)
					throws Exception {
				// TODO Auto-generated method stub
				
				commentData = (List<CollocationComments>)msg.obj;
				
				commentAdapter = new CommentListAdapter(ChannelDetailActivity.this, commentData);
				commentList.setAdapter(commentAdapter);
			}
			
    		 
    	 });
        
        
        
        
        
        
        
        initTopButtons();
        initBottomFuncButton(itemDetail.getId());
        initPersonFuncButton(itemDetail.getOwnerId());
        
    }
    

    
    private void initBottomFuncButton(final String id){
    	
    	Button commentButton = (Button) findViewById(R.id.comment);
    	Button adoreButton = (Button) findViewById(R.id.likethis);
    	Button reportButton = (Button) findViewById(R.id.report);
    	
    	 if (commentButton != null) {
             commentButton.setOnClickListener(
                     new OnClickListener() {
                         public void onClickAction(View view) {
                        	 startActivity(CommentsActivity.class, id);
                             
                         }
                     });
         }
    	 
    	 if (adoreButton != null) {
             adoreButton.setOnClickListener(
                     new OnClickListener() {
                         public void onClickAction(final View view) {
                        	 CollocationManagerDefaultImpl.adore(id, new BaseHandler(){

								@Override
								protected void handleMsg(Message msg)
										throws Exception {
									// TODO Auto-generated method stub
									MessageHandler.showWarningMessage(ChannelDetailActivity.this, R.string.msg_adore);
									view.setEnabled(false);
									
								}
                        		 
                        	 });
                             
                         }
                     });
         }
    	 
    	 if (reportButton != null) {
    		 reportButton.setOnClickListener(
    				 new OnClickListener() {
    					 public void onClickAction(View view) {
                        	 HashMap<String, String> params = new HashMap<String, String>();
                        	 params.put("id", id);
                        	 params.put("owner", ChannelDetailActivity.this.itemDetail.getOwnerId());
                        	 startActivity(CollocationReportActivity.class, params);
                        	 
    					 }
    				 });
            		 
    	 }
    	
    	
    }
    

    
    private void initPersonFuncButton(final String ownerId){
    	final Button followBtn = (Button) findViewById(R.id.followuser);
    	final Button mailBtn = (Button) findViewById(R.id.mailuser);
    	
    	
    	
    	if (followBtn != null){
    		if(!jbolt.android.base.AppContext.getUser().getId().equals(ownerId)){
    			PersonManagerDefaultImpl.hasRelation(jbolt.android.base.AppContext.getUser().getId(), ownerId, RelationsType.OBSERVERS, new BaseHandler(){

    				@Override
    				protected void handleMsg(Message msg) throws Exception {
    					// TODO Auto-generatBo(Boolean) msg.obj;
    					Boolean aa = (Boolean)msg.obj;
    					if (aa){
    						followBtn.setEnabled(false);
    						followBtn.setText("已关注");
    					}
    					
    				}
    	        	
    	        });
    			    			
    			followBtn.setOnClickListener(
    					new OnClickListener() {
    						public void onClickAction(View view) {
    							PersonManagerDefaultImpl.addRelations(jbolt.android.base.AppContext.getUser().getId(), ownerId, RelationsType.OBSERVERS, new BaseHandler(){

									@Override
									protected void handleMsg(Message msg)
											throws Exception {
										// TODO Auto-generated method stub
										
									}
    								
    							});
                            	
    						}
    					});
    		}else{
    			followBtn.setEnabled(false);
    		}
    	}
    	
    	if (mailBtn != null){
    		if(!jbolt.android.base.AppContext.getUser().getId().equals(ownerId)){
    			mailBtn.setOnClickListener(new OnClickListener(){
					public void onClickAction(View view) {
						startActivity(MessageActivity.class, ownerId);
						
					}
    				
    			});
    		}else{
    			mailBtn.setEnabled(false);
    		}
    		
    	}
    }

    


}
