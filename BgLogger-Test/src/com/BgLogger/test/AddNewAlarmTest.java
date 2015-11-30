package com.BgLogger.test;

import com.BgLogger.activity.reminders.AddNewAlarm;
import com.robotium.solo.Solo;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

public class AddNewAlarmTest extends
		ActivityInstrumentationTestCase2<AddNewAlarm> {

	
	private Solo solo;
	
	public AddNewAlarmTest() {
		super(AddNewAlarm.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		Intent i = new Intent();
		Bundle basket = new Bundle();
		basket.putString("date", "2014/05/10 10:20 AM");
		basket.putString("test", "Random Blood Sugar");
		basket.putInt("id", 2);
		basket.putInt("requestcode",2);
		i.putExtras(basket);
		setActivityIntent(i);
		solo = new Solo(getInstrumentation(), getActivity());
		super.setUp();
	}
	
	public void testEditAlarm(){
		solo.assertCurrentActivity("Chceck your activity",AddNewAlarm.class );
		solo.clickOnButton("Submit");
		
	}

}
