package com.BgLogger.activity.calling;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
	
	private Context context;
	
	public ConnectionDetector( Context _context){
		this.context = _context;
	}
	
	//Check for all possible internet providers
	public boolean isConnectedToInternet(){
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivity != null){
			NetworkInfo[] information = connectivity.getAllNetworkInfo();
			if(information != null){
				for(int i = 0; i < information.length; i++){
					if(information[i].getState() == NetworkInfo.State.CONNECTED) return true;
				}
			}
		}
		
		return false;
		
		
	}
	

}
