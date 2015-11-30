package com.BgLogger;

import com.BgLogger.activity.calling.CallMainActivity;
import com.BgLogger.activity.slidingpane.Sliding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Initial extends Activity implements OnClickListener {

	Button addAccount, patient, emergencyCall, userManual;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_select);
		initializeVars();
		addAccount.setOnClickListener(this);
		emergencyCall.setOnClickListener(this);
		
	}

	private void initializeVars() {
		// TODO Auto-generated method stub
		addAccount = (Button) findViewById(R.id.add_accountB);
		emergencyCall = (Button) findViewById(R.id.emergencyCallB);
		userManual = (Button) findViewById(R.id.usermanualB);

	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		Intent i = null;
		switch (view.getId()) {
		// start up the corresponding activity depending on what button has been
		// clicked
		case R.id.add_accountB:
			i = new Intent(Initial.this, Sliding.class);
			break;
		case R.id.emergencyCallB:
			i = new Intent(Initial.this, CallMainActivity.class);
			break;
		case R.id.usermanualB:
			break;
		}
		startActivity(i);

	}

}
