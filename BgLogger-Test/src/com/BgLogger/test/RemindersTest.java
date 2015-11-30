package com.BgLogger.test;

import com.BgLogger.activity.reminders.AddNewAlarm;
import com.BgLogger.activity.reminders.AlarmList;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class RemindersTest extends ActivityInstrumentationTestCase2<AddNewAlarm> {

	private Solo solo;
	@SuppressWarnings("deprecation")
	public RemindersTest() {
		super("com.BgLogger.test", AddNewAlarm.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		super.setUp();
	}
	
	public void testButtons(){
		solo.assertCurrentActivity("Chceck your activity",AddNewAlarm.class );
		solo.clickOnButton("Submit");
	
		
		
	}

}
