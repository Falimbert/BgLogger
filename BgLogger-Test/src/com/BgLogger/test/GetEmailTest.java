package com.BgLogger.test;

import com.BgLogger.Splash;
import com.BgLogger.activity.email.GetEmailAddress;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

public class GetEmailTest extends
		ActivityInstrumentationTestCase2<GetEmailAddress> {

private Solo solo;
	
	public GetEmailTest() {
		super(GetEmailAddress.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		super.setUp();
	}
	
	public void testActivity(){
		solo.assertCurrentActivity("Check your email getting activity", GetEmailAddress.class);
	//	solo.isCheckBoxChecked(1);
		solo.clickOnCheckBox(1);
		solo.clickOnButton(0);
	}

}
