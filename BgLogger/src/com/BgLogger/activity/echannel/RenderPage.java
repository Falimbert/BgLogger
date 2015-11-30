package com.BgLogger.activity.echannel;

import com.BgLogger.R;
import com.BgLogger.activity.slidingpane.Sliding;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class RenderPage extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.echannel);
		WebView view = (WebView)findViewById(R.id.wvEchannel);
		view.loadUrl("http://www.echannelling.com/EChannel/channel_doctor.jsp");
	}
	

}
