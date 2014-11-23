package com.android.muetclbookssearching;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.os.Handler;
import android.content.Intent;
import android.view.Window;


public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash_screen);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				finish();
				Intent intent = new Intent(SplashScreenActivity.this,UserAuthorization.class);
				SplashScreenActivity.this.startActivity(intent);
			}
		}, 8000);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
