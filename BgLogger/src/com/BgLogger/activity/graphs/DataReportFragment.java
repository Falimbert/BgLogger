package com.BgLogger.activity.graphs;

import java.util.ArrayList;

import com.BgLogger.R;
import com.BgLogger.model.glucose.BloodGlucoseLogDao;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DataReportFragment extends ListFragment{

	private String display[] = { "Fasting Blood Sugar", "Random Blood Sugar",
			"HBA1C" };
	private String test;
	private BloodGlucoseLogDao log_data;
	private Object[] data;
	private ArrayList<String> dates;
	private ArrayList<Double> readings;
	private View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        rootView = inflater.inflate(R.layout.common_list, container, false);
   //     ListView list = (ListView)rootView.findViewById()
        
        TextView tv = (TextView)rootView.findViewById(R.id.tvCommontv);
        tv.setText("Select the test below to see your progress");
		//create the menu to select a graph
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, display));
		log_data = new BloodGlucoseLogDao(getActivity());
		return rootView;

	}

	@SuppressWarnings("unchecked")
	public void onListItemClick(ListView l, View v, int position, long id) {
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
		Intent i = new Intent(getActivity(), DrawGraph.class);
		//bind the intent with data
		i.putExtra("readings", readings);
		i.putStringArrayListExtra("dates", dates);
		i.putExtra("test", test);
		startActivity(i);

	}


}
