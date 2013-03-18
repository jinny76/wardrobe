package sttri.citrusproject;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import jbolt.android.R;
import jbolt.android.base.GenericBaseActivity;

/**
 * <p>Title: ChannelShowActivity</p>
 * <p>Description: ChannelShowActivity</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class ChannelShowActivity extends GenericBaseActivity {


    private FlowLayoutScrollView scrollView;
    private AssetManager assetManager;
    private List<String> imagefiles;
    private int item_width;
    private LinearLayout layout01;
    private LinearLayout layout02;
    private LinearLayout layout03;

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) throws Exception {
        setContentView(R.layout.activity_main);
        Log.v("showtime", "activity is on");

        initScroll();

        assetManager = this.getAssets();
        item_width = getWindowManager().getDefaultDisplay().getWidth() / 3;


        try {
            imagefiles = Arrays.asList(assetManager.list("images"));
            Log.v("showtime", imagefiles.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        layout01 = (LinearLayout) findViewById(R.id.layout01);
        layout02 = (LinearLayout) findViewById(R.id.layout02);
        layout03 = (LinearLayout) findViewById(R.id.layout03);
        addImage(0, 30);
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

    private void addImage(int current_page, int count) {
        Log.v("showtime", "addImage is load" + item_width);
        int y = 0;
        for (int x = current_page * count; x < (current_page + 1) * count && x < imagefiles.size(); x++) {
            try {
                InputStream is = assetManager.open("images/" + imagefiles.get(x));
                addBitMapToImage(is, y, x);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.v("showtime", imagefiles.get(x).toString());
            y++;

            if (y >= 3) {
                y = 0;
            }
        }
    }

    public void addBitMapToImage(InputStream is, int j, int i) {
        Context mContext = ChannelShowActivity.this;
        LinearLayout imageholder = new LinearLayout(mContext);
        LinearLayout.LayoutParams imageparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageparams.setMargins(2, 4, 2, 4);
        imageholder.setLayoutParams(imageparams);
        imageholder.setOrientation(LinearLayout.VERTICAL);
        imageholder.setBackgroundColor(Color.WHITE);


        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View imagedescrlayout = inflater.inflate(R.layout.imageframe, null);
        //TextView text = (TextView)imagedescrlayout.findViewById(R.id.describetext);


        ImageView imageView = new ImageView(ChannelShowActivity.this);
        imageView.setImageBitmap(decodeSampledBitmapFromStream(is, item_width, 1));

        imageholder.addView(imageView);
        imageholder.addView(imagedescrlayout);

        if (j == 0) {
            layout01.addView(imageholder);
        } else if (j == 1) {
            layout02.addView(imageholder);
        } else if (j == 2) {
            layout03.addView(imageholder);
        }

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(ChannelShowActivity.this, "ͼƬ�߶�" + v.getHeight(), Toast.LENGTH_SHORT).show();

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