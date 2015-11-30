package com.BgLogger.activity.calling;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;


public class GPSEnable {

	private boolean isGPSEnabled;
	private LocationManager locationManager;
	private Context context;
	private boolean isNetworkEnabled;

	public GPSEnable(Context context) {
		this.context = context;
	}

	public boolean checkGPSOrNetwork() {

		locationManager = (LocationManager) context
				.getSystemService(context.LOCATION_SERVICE);
		// getting GPS status
		isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// getting network status
		isNetworkEnabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (!isGPSEnabled && !isNetworkEnabled) {
			return false;

		}
		return true;
	}


}
