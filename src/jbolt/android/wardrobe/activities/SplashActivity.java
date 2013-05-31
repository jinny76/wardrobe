package jbolt.android.wardrobe.activities;

import jbolt.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	
	private final int splashDisplayTime = 800;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		new Handler().postDelayed(new Runnable(){
			
			@Override
			public void run(){
				Intent callMain = new Intent(SplashActivity.this, WardrobeCatalogActivity.class);
				startActivity(callMain);
				SplashActivity.this.finish();
			}
			
		}, splashDisplayTime);
		
	}

	

}
