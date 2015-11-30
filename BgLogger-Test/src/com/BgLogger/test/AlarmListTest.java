package com.BgLogger.test;

import com.BgLogger.activity.reminders.AlarmList;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class AlarmListTest extends ActivityInstrumentationTestCase2<AlarmList> {
	
	private Solo solo;

	public AlarmListTest() {
		super(AlarmList.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		super.setUp();
	}
	
	public void testAlarmList(){
		solo.assertCurrentActivity("Check your alarm list", AlarmList.class);
		solo.clickInList(1, 0);
	}

}
