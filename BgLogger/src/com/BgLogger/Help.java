package com.BgLogger;

import com.BgLogger.activity.calling.CallMainActivity;
import com.BgLogger.activity.calling.ConnectionDetector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Help extends ActionBarActivity{

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		ConnectionDetector connection = new ConnectionDetector(Help.this);
		boolean isConnected = connection.isConnectedToInternet();
		if(!isConnected){
			// No internet connection
		       Toast.makeText(Help.this, "Please connect to a working internet connection ", Toast.LENGTH_LONG).show();
		       finish();
		       return;
		}

		title(getString(R.string.help_centre));
		setContentView(R.layout.echannel);
		WebView view = (WebView)findViewById(R.id.wvEchannel);
		view.setWebViewClient(new WebViewClient());
		WebSettings webSettings = view.getSettings();
		webSettings.setJavaScriptEnabled(true);
		view.loadUrl("https://sites.google.com/site/bloodglucoseloggerapp/");
		
	}
	

}
