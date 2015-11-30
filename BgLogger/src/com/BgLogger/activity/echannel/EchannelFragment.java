package com.BgLogger.activity.echannel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.BgLogger.Help;
import com.BgLogger.R;
import com.BgLogger.activity.calling.ConnectionDetector;

@SuppressLint("NewApi")
public class EchannelFragment extends Fragment {

	View rootView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ConnectionDetector connection = new ConnectionDetector(getActivity());
		boolean isConnected = connection.isConnectedToInternet();
		if (!isConnected) {
			// No internet connection
			Toast.makeText(getActivity(),
					"Please connect to a working internet connection ",
					Toast.LENGTH_LONG).show();
		}

		rootView = inflater.inflate(R.layout.echannel, container, false);
		WebView view = (WebView) rootView.findViewById(R.id.wvEchannel);
		view.setWebViewClient(new WebViewClient());
		WebSettings webSettings = view.getSettings();
		webSettings.setJavaScriptEnabled(true);
		view.loadUrl("http://www.echannelling.com/EChannel/channel_doctor.jsp");
		return rootView;
	}

}
