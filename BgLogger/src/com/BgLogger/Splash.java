package com.BgLogger;


import com.BgLogger.activity.slidingpane.Sliding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		
		Thread timer = new Thread() {
			public void run() {
				try {
					//make this thread sleep for 2000 ms
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				finally {
					//when time elapsed, start up a new intent
					Intent openStartingPoint = new Intent(Splash.this,Sliding.class);
					startActivity(openStartingPoint);

				}

			}
		};
		timer.start();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//finish the activity on pause
		finish();
	}
	
	

	

}
