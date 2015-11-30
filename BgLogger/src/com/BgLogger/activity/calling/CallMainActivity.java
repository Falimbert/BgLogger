package com.BgLogger.activity.calling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import com.BgLogger.ActionBarActivity;
import com.BgLogger.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
//import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class CallMainActivity extends ActionBarActivity implements
		LocationListener, OnClickListener {

	private LocationManager locationManager;
	private String provider;
	private Location location;
	private double latitude;
	private double longitude;
	private GoogleMap map;
	private List<HashMap<String, String>> places = null;

	private LinearLayout linlaHeaderProgress;
	private GPSEnable gps;
	private ConnectionDetector connection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//checking if the gps provider is available
				gps = new GPSEnable(CallMainActivity.this);
				boolean isEnabled = gps.checkGPSOrNetwork();
					
				//checking if the network connectivity is available
				connection = new ConnectionDetector(CallMainActivity.this);
				boolean isConnected = connection.isConnectedToInternet();
				if(!isConnected && !isEnabled){

					// No internet connection and GPS not enabled
			       Toast.makeText(CallMainActivity.this, "Please connect to a working internet connection and enable GPS", Toast.LENGTH_LONG).show();
			       finish();
			       return;
				}
				else if(!isEnabled){
					// GPS not enabled
				       Toast.makeText(CallMainActivity.this, "Please enable GPS to proceed", Toast.LENGTH_LONG).show();
				       finish();
				       return;
				}
				
				else if(!isConnected){
					// No internet connection
				       Toast.makeText(CallMainActivity.this, "Please connect to a working internet connection ", Toast.LENGTH_LONG).show();
				       finish();
				       return;
				}

		setContentView(R.layout.map);
		title(getString(R.string.current_location));
		
		
		linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
		Button callButton = (Button) findViewById(R.id.bCall);
		callButton.setOnClickListener(this);
		
		
		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();
			return;

		}
		
		else { // Google Play Services are available

			// Getting reference to the SupportMapFragment

			SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);

			// Getting Google Map 
			map = fragment.getMap();

			// Enabling MyLocation in Google Map
			map.setMyLocationEnabled(true);

			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();

			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location From GPS
			Location location = locationManager.getLastKnownLocation(provider);

			if (location != null) {
				onLocationChanged(location);
			}

			locationManager.requestLocationUpdates(provider, 20000, 0, this);

			StringBuilder sb = new StringBuilder(
					"https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
			sb.append("name=%22Nursing%20Home%22");
			sb.append("&rankby=distance");
			sb.append("&location=" + latitude + "," + longitude);
			sb.append("&sensor=false");
			sb.append("&key=AIzaSyCsbHKS7n9KIIlp3C_Kzac_nmjsPxM2Kj0");

			// Creating a new non-ui thread task to download json data
			PlacesTask placesTask = new PlacesTask();

			// Invokes the "doInBackground()" method of the class PlaceTask
			placesTask.execute(sb.toString());

		}
	}

	/* A class, to download Google Places */
	private class PlacesTask extends AsyncTask<String, Integer, String> {

		String data = null;

		@Override
		protected void onPreExecute() {
			linlaHeaderProgress.setVisibility(View.VISIBLE);
		}

		// Invoked by execute() method of this object
		@Override
		protected String doInBackground(String... url) {
			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(String result) {
			ParserTask parserTask = new ParserTask();

			// Start parsing the Google places in JSON format
			// Invokes the "doInBackground()" method of the class ParseTask
			parserTask.execute(result);
			linlaHeaderProgress.setVisibility(View.GONE);
		}

	}


	/* A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();
		//	iStream.close();
		//	urlConnection.disconnect();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		//	Intent i = new Intent(CallMainActivity.this, Stupid.class);
		//	i.putExtra("Exception", e.toString());
		//	startActivity(i);

		} finally {
			iStream.close();
			urlConnection.disconnect();
		}

		return data;
	}
	
	
	private class Download extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... strUrl) {
			// TODO Auto-generated method stub
			
			
			String data = "";
			InputStream iStream = null;
			HttpURLConnection urlConnection = null;
			try {
				URL url = new URL(strUrl[0]);

				// Creating an http connection to communicate with url
				urlConnection = (HttpURLConnection) url.openConnection();

				// Connecting to url
				urlConnection.connect();

				// Reading data from url
				iStream = urlConnection.getInputStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						iStream));

				StringBuffer sb = new StringBuffer();

				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				data = sb.toString();

				br.close();
			//	iStream.close();
			//	urlConnection.disconnect();

			} catch (Exception e) {
				Log.d("Exception while downloading url", e.toString());
			//	Intent i = new Intent(CallMainActivity.this, Stupid.class);
			//	i.putExtra("Exception", e.toString());
			//	startActivity(i);

			} finally {
				try {
					iStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				urlConnection.disconnect();
			}

			return data;
		}
		
	}

	/* A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<HashMap<String, String>>> {

		JSONObject jObject;

		// Invoked by execute() method of this object
		@Override
		protected List<HashMap<String, String>> doInBackground(
				String... jsonData) {

			PlaceJSONParser placeJsonParser = new PlaceJSONParser();

			try {
				jObject = new JSONObject(jsonData[0]);

				/* Getting the parsed data as a List construct */
				places = placeJsonParser.parse(jObject);

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return places;
		}

		// Executed after the complete execution of doInBackground() method
		protected void onPostExecute(List<HashMap<String, String>> list) {

			// Clears all the existing markers
			map.clear();

			for (int i = 0; i < list.size(); i++) {

				// Creating a marker
				MarkerOptions markerOptions = new MarkerOptions();

				// Getting a place from the places list
				HashMap<String, String> hmPlace = list.get(i);

				// Getting latitude of the place
				double lat = Double.parseDouble(hmPlace.get("lat"));

				// Getting longitude of the place
				double lng = Double.parseDouble(hmPlace.get("lng"));

				// Getting name
				String name = hmPlace.get("place_name");

				// Getting vicinity
				String vicinity = hmPlace.get("vicinity");

				LatLng latLng = new LatLng(lat, lng);

				// Setting the position for the marker
				markerOptions.position(latLng);

				// Setting the title for the marker. // This will be displayed
				// on tapping the marker
				markerOptions.title(name + " : " + vicinity);

				// Placing a marker on the touched position
				map.addMarker(markerOptions);
			}

		}
	}

	public void onLocationChanged(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		LatLng latLng = new LatLng(latitude, longitude);
		map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		map.animateCamera(CameraUpdateFactory.zoomTo(12));

	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		//code to execute on 'Call Now' button click
		if (v.getId() == R.id.bCall) {
			Intent intent;
			String number = null;
			String name = null;
			JSONObject jObject;
			String details = null;
			int i = 0;
			int num = places.size();
		//	double dist;
			
			
			while (number == null && i < num) { //check the place details of each of the places in the list 'places'
				StringBuilder sb = new StringBuilder(
						"https://maps.googleapis.com/maps/api/place/details/json?");
				sb.append("placeid=" + places.get(i).get("place_id"));
				sb.append("&key=AIzaSyCsbHKS7n9KIIlp3C_Kzac_nmjsPxM2Kj0");  //Build the url to get data from
				try {
					details = new Download().execute(sb.toString()).get(); //get a string containing the place details
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 try {

					jObject = (JSONObject) (new JSONObject(details)) //create a JSONObject from the string and get "result" JSONObject from it
							.get("result");
				

					if (!jObject.isNull("formatted_phone_number")) {   //if the formatted_phone_number field is not null 
					number = jObject.getString("formatted_phone_number"); //set the number variable to the formatted_phone_number
						name = jObject.getString("name");

					}

				} catch (Exception e) {
					Log.d("Exception", e.toString());
				}

				i++; //increment the loop invariant to access the next place in the list
			}
			
			Toast.makeText(getBaseContext(), "Calling " + name + " on " + number, Toast.LENGTH_LONG).show();
			intent = new Intent(CallMainActivity.this, Call.class); //create a new intent to make the call
			intent.putExtra("number", number); //send the phone number to the new intent
			startActivity(intent);
		}

	}

}
