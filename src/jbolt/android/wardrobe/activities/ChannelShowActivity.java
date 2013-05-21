package jbolt.android.wardrobe.activities;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import jbolt.android.R;
import jbolt.android.base.BaseHandler;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.MessageHandler;
import jbolt.android.utils.image.ImageManager;
import jbolt.android.wardrobe.base.FlowLayoutScrollView;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;
import jbolt.android.wardrobe.models.Collocation;
import jbolt.android.wardrobe.models.ShowsType;
import jbolt.android.wardrobe.service.impl.CollocationManagerDefaultImpl;

import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChannelShowActivity extends WardrobeFrameActivity {

    private ImageButton btnNew;
    private ImageButton btnHot;
    private ImageButton btnAtt;

    private FlowLayoutScrollView scrollView;
    private AssetManager assetManager;
    private List<String> imagefiles;
    private int item_width;
    private LinearLayout layout01;
    private LinearLayout layout02;
    private LinearLayout layout03;

    private Handler handler = new Handler();

    private ExecutorService executorService = Executors.newFixedThreadPool(6);
	private Object[] showList;
	
	private List<LinearLayout> itemInFront;


    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        // TODO Auto-generated method
        setContentView(R.layout.channelshow);
        btnNew = (ImageButton) findViewById(R.id.btnTopNew);
        btnHot = (ImageButton) findViewById(R.id.btnTopHot);
        btnAtt = (ImageButton) findViewById(R.id.btnTopAtt);

        initSpecialTopButtons();
        initBottomButtons();

        initScroll();


        item_width = getWindowManager().getDefaultDisplay().getWidth() / 3;


        layout01 = (LinearLayout) findViewById(R.id.layout01);
        layout02 = (LinearLayout) findViewById(R.id.layout02);
        layout03 = (LinearLayout) findViewById(R.id.layout03);
        
        showHottest();
        
        
        
       
        

       

    }


    @Override
    protected void initSpecialTopButtons() {
        btnNew.setOnClickListener(
                new OnClickListener() {
                    public void onClickAction(View view) {
                        showNewest();
                    }
                });
        btnHot.setOnClickListener(
                new OnClickListener() {
                    public void onClickAction(View view) {
                        showHottest();
                    }
                });
        btnAtt.setOnClickListener(
                new OnClickListener() {
                    public void onClickAction(View view) {
                        showAttention();
                    }
                });
    }

    private void showNewest() {
        MessageHandler.showWarningMessage(this, "Show Newest");
    }

    private void showHottest() {
        MessageHandler.showWarningMessage(this, "Show Hottest");
        
        getList(ShowsType.HOTTEST);
      
        
        
       
        
        
    }

    private void showAttention() {
        MessageHandler.showWarningMessage(this, "Show Attention");
    }


    public void initScroll() {
        scrollView = (FlowLayoutScrollView) findViewById(R.id.showtimestream);
        scrollView.getView();

        scrollView.setOnScrollListener(new FlowLayoutScrollView.OnScrollListener() {

            @Override 
            public void onBottom() {
                // TODO Auto-generated method stub
                
            }


            @Override
            public void onTop() {
                // TODO Auto-generated method stub

            }


            @Override
            public void onScroll() {
                // TODO Auto-generated method stub

            }

        });
    }
    
    private void getList(int listType){
    	
    	
    	
    	 
        CollocationManagerDefaultImpl.loadShows(listType,jbolt.android.base.AppContext.getUser().getId(), new BaseHandler(){

			@Override
			protected void handleMsg(Message msg) throws Exception {
				// TODO Auto-generated method stub
				if (msg.obj instanceof Collection){
					showList = ((Collection)msg.obj).toArray();	
					
					 
					  
					 
				        
				        
				        addItem();
			      
					
				}else{
					 MessageHandler.showWarningMessage(ChannelShowActivity.this, (Exception) msg.obj);
					 Log.v("showtime", msg.toString());
				}				
			}
        	
        });
    	
    	    	
    }


    private void addItem() {

        Log.v("showtime", "addImage is load" + item_width);
        int y = 0;
        Context mContext = ChannelShowActivity.this;
        for (int x = 0; x < showList.length ; x++) {
        	
        	 Collocation showItem = (Collocation) showList[x];
        	 
        	 LinearLayout imageHolder = new LinearLayout(mContext);
             LinearLayout.LayoutParams imageparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
             imageparams.setMargins(2, 4, 2, 4);
             imageHolder.setLayoutParams(imageparams);
             imageHolder.setOrientation(LinearLayout.VERTICAL);
             imageHolder.setBackgroundColor(Color.WHITE);

             
             LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
             View imagedescrlayout = inflater.inflate(R.layout.imageframe, null);
             TextView likeNum = (TextView) imagedescrlayout.findViewById(R.id.likenum);
             TextView discuNum = (TextView) imagedescrlayout.findViewById(R.id.discunum);
             TextView describeTxt = (TextView) imagedescrlayout.findViewById(R.id.describetext);
             
             
             if (showItem.getAdoreCounter() == null){
             	likeNum.setText("0");
             }else{
             	likeNum.setText(Long.toString(showItem.getAdoreCounter()));
             }
             
             if (showItem.getCommentsCounter() == null){
             	discuNum.setText("0");
             }else{
             	discuNum.setText(Long.toString(showItem.getCommentsCounter()));
             }
             
             if (showItem.getDescription() == null){
             	describeTxt.setVisibility(View.GONE);
             }else{
             	describeTxt.setText(showItem.getDescription());
             }

             String url = ImageManager.getUrl(showItem.getId(), true);
             
             imageHolder.addView(imagedescrlayout);
             
             ImageView showThumb = new ImageView(ChannelShowActivity.this);
             
             //showThumb.setImageResource(R.drawable.loading);
             
             imageHolder.addView(imagedescrlayout);
			 
            
             AsyncLoadImage(url,showThumb,imageHolder);
            Log.v("showtime", "item:" + showItem.getId() + "added");
         
            
            if (y == 0) {
                layout01.addView(imageHolder);
            } else if (y == 1) {
                layout02.addView(imageHolder);
            } else if (y == 2) {
                layout03.addView(imageHolder);
            }

            
            
            y++;

            if (y >= 3) {
                y = 0;
            }
        }
    }


    public void AsyncLoadImage(final String url, final ImageView view, final LinearLayout layout) {

       
        
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    
                    
                    
                    
                    ImageManager.getInstance().lazyLoadImage(url, null, new HashMap(), view);
                    
                    handler.post(new Runnable() {
                        public void run() {
                        	 
                             
                                     

                             

                        	layout.addView(view);
                        	
                        	view.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClickAction(View v) {
                                    ActivityDispatcher.commentsDetail(ChannelShowActivity.this);
                                    Toast.makeText(ChannelShowActivity.this, "图片高度" + v.getHeight(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        
        
       


    }
    
    

    private void loadAssetsImage(final String path, final ImageView view) {
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    InputStream is = assetManager.open("images/" + path);
                    final Bitmap pic = decodeSampledBitmapFromStream(is, item_width, 1);
                    is.close();
                    handler.post(new Runnable() {
                        public void run() {
                            view.setImageBitmap(pic);
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        Log.v("showtime", "height:" + height + "  //width: " + width);

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        Log.v("showtime", inSampleSize + "height:" + height + "  //width: " + width);
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromStream(InputStream is, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    public static final float scalX = 100;
    public static final float scalY = 200;

    public static Bitmap adaptive(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float w = scalX / bitmap.getWidth();
        float h = scalY / bitmap.getHeight();
        matrix.postScale(w, h);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }
}


