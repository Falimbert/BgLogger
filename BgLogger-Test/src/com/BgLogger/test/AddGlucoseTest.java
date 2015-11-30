package com.BgLogger.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.BgLogger.activity.glucose.AddGlucoseLogActivity;
import com.robotium.solo.Solo;

public class AddGlucoseTest extends
		ActivityInstrumentationTestCase2<AddGlucoseLogActivity> {

	private Solo solo;
	private EditText text;
	
	public AddGlucoseTest() {
		super(AddGlucoseLogActivity.class);
		
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		super.setUp();
	}
	
	public void testLayout(){
		solo.assertCurrentActivity("Check your add glucose activity", AddGlucoseLogActivity.class);
		solo.waitForActivity(AddGlucoseLogActivity.class, 5000);
		solo.enterText(0,"ten");
		solo.clickOnButton(0);
		
		
	}

}
