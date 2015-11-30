package com.BgLogger.test;

import com.BgLogger.Splash;
import com.BgLogger.activity.glucose.AddGlucoseLogActivity;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class SplashTest extends ActivityInstrumentationTestCase2<Splash> {

	private Solo solo;
	
	public SplashTest() {
		super(Splash.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		super.setUp();
	}
	
	public void testActivity(){
		solo.assertCurrentActivity("Check your main activity", Splash.class);
	}
	
	

}
