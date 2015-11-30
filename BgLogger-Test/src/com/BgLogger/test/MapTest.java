package com.BgLogger.test;

import com.BgLogger.activity.calling.CallMainActivity;
import com.robotium.solo.Solo;

import android.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class MapTest extends ActivityInstrumentationTestCase2<CallMainActivity> {

	private Solo solo;
	private Fragment map;
	private Button call;
	@SuppressWarnings("deprecation")
	public MapTest() {
		super("com.BgLogger.test", CallMainActivity.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		super.setUp();
	}
	
	public void testMap(){
		solo.assertCurrentActivity("Check calling", CallMainActivity.class);
	//	map = solo.getCurrentActivity() .getFragmentManager() .findFragmentById(com.BgLogger.R.id.map);
		solo.waitForActivity(CallMainActivity.class, 10000);
		solo.clickOnButton("Call Now");
	//	call = getCurrentActivity()findViewById(com.BgLogger.R.id.bCall);
	//	solo.
		
	}

}
