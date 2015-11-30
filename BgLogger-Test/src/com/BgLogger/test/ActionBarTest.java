package com.BgLogger.test;

import com.BgLogger.activity.reminders.AlarmList;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class ActionBarTest extends ActivityInstrumentationTestCase2<AlarmList> {

	private Solo solo;
	public ActionBarTest() {
		super(AlarmList.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		super.setUp();
	}
	
	public void testActionBar(){
		solo.assertCurrentActivity("Check your alarm list", AlarmList.class);
		solo.clickOnActionBarItem(com.BgLogger.R.id.action_add_new_alarm);
	}

}
