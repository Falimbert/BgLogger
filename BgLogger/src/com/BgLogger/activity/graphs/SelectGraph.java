package com.BgLogger.activity.graphs;

import java.util.ArrayList;
import java.util.Date;

import com.BgLogger.Menu;
import com.BgLogger.R;
import com.BgLogger.model.glucose.BloodGlucoseLogDao;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectGraph extends ListActivity {

	private String display[] = { "Fasting Blood Sugar", "Random Blood Sugar",
			"HBA1C" };
	private String test;
	private BloodGlucoseLogDao log_data;
	private Object[] data;
	private ArrayList<String> dates;
	private ArrayList<Double> readings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		//create the menu to select a graph
		setListAdapter(new ArrayAdapter<String>(SelectGraph.this,
				android.R.layout.simple_list_item_1, display));
		log_data = new BloodGlucoseLogDao(SelectGraph.this);

	}

	@SuppressWarnings("unchecked")
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub

		ArrayList<String> dates;
		ArrayList<Double> readings;
		super.onListItemClick(l, v, position, id);
		log_data.openToRead();

		//retrieve data from the database depending on the graph type selected
		switch (position) {
		case 0:
			data = log_data.getTestData("Fasting Blood Sugar");
			test = "Fasting Blood Sugar";
			break;
		case 1:
			data = log_data.getTestData("Random Blood Sugar");
			test = "Random Blood Sugar";
			break;
		case 2:
			data = log_data.getTestData("HBA1C");
			test = "HBA1C";
			break;

		}
		
		//close the connetion to the database
		log_data.close();
		//assign the received readings arraylist and dates arraylist
		readings = (ArrayList<Double>) data[0];
		dates = (ArrayList<String>) data[1];
		
		//create a new intent to draw a graph with data selected
		Intent i = new Intent(SelectGraph.this, DrawGraph.class);
		//bind the intent with data
		i.putExtra("readings", readings);
		i.putStringArrayListExtra("dates", dates);
		i.putExtra("test", test);
		startActivity(i);

	}

}
