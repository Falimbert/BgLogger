package com.BgLogger.activity.calling;

import com.BgLogger.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class Call extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String number = getIntent().getExtras().getString("number");
		
		Intent i = new Intent(Intent.ACTION_CALL);
		i.setData(Uri.parse("tel:"+number)); startActivity(i);
		finish();

	}
	

}
