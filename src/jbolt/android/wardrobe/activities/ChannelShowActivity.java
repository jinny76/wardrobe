package jbolt.android.wardrobe.activities;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import jbolt.android.R;
import jbolt.android.listeners.OnClickListener;
import jbolt.android.utils.MessageHandler;
import jbolt.android.wardrobe.base.FlowLayoutScrollView;
import jbolt.android.wardrobe.base.WardrobeFrameActivity;

import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
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
        
        TextView hint = new TextView(this);
        

        executorService.submit(new Runnable() {
            public void run() {
                try {
                    assetManager = getAssets();
                    imagefiles = Arrays.asList(assetManager.list("images"));

                    handler.post(new Runnable() {
                        public void run() {

                            addImage(0, 18);
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


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
                addImage(0, 18);
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


    private void addImage(int current_page, int count) {

        Log.v("showtime", "addImage is load" + item_width);
        int y = 0;
        for (int x = 0; x < count; x++) {
            SecureRandom random = new SecureRandom();
            addBitMapToImage(imagefiles.get(random.nextInt(20)), y);
            Log.v("showtime", "pics:" + x + "loaded");
            y++;

            if (y >= 3) {
                y = 0;
            }
        }
    }


    public void addBitMapToImage(String path, int j) {

        Context mContext = ChannelShowActivity.this;
        LinearLayout imageholder = new LinearLayout(mContext);
        LinearLayout.LayoutParams imageparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageparams.setMargins(2, 4, 2, 4);
        imageholder.setLayoutParams(imageparams);
        imageholder.setOrientation(LinearLayout.VERTICAL);
        imageholder.setBackgroundColor(Color.WHITE);

        Log.v("showtime", "pics:" + path + "loaded");


        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View imagedescrlayout = inflater.inflate(R.layout.imageframe, null);
        TextView likeNum = (TextView) imagedescrlayout.findViewById(R.id.likenum);
        TextView discuNum = (TextView) imagedescrlayout.findViewById(R.id.discunum);
        TextView describeTxt = (TextView) imagedescrlayout.findViewById(R.id.describetext);
        SecureRandom random = new SecureRandom();

        likeNum.setText(String.valueOf(random.nextInt(80)));
        discuNum.setText(String.valueOf(random.nextInt(90)));
        describeTxt.setText("简单舒服的T袖 设计有蕾丝领和蕾丝下摆哦 ～这种T袖很百搭哦！");

        ImageView imageView = new ImageView(ChannelShowActivity.this);
        loadAssetsImage(path, imageView);


        imageholder.addView(imageView);
        imageholder.addView(imagedescrlayout);

        if (j == 0) {
            layout01.addView(imageholder);
        } else if (j == 1) {
            layout02.addView(imageholder);
        } else if (j == 2) {
            layout03.addView(imageholder);
        }

        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClickAction(View v) {
                ActivityDispatcher.commentsDetail(ChannelShowActivity.this);
                Toast.makeText(ChannelShowActivity.this, "图片高度" + v.getHeight(), Toast.LENGTH_SHORT).show();
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


