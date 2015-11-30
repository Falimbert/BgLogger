package com.BgLogger;

import com.BgLogger.activity.echannel.RenderPage;
import com.BgLogger.activity.glucose.AddGlucoseLogActivity;
import com.BgLogger.activity.graphs.SelectGraph;
import com.BgLogger.activity.reminders.AlarmList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
//import android.view.MenuInflater;
//import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {

	String display[] = { "Data Entry", "Data Report", "Echanneling", "Email",
			"Preferences", "Reminders" };

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		//get a full screen view for the menu
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//setup a list
		setListAdapter(new ArrayAdapter<String>(Menu.this,
				android.R.layout.simple_list_item_1, display));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Intent i = null;
		super.onListItemClick(l, v, position, id);

		//start up the corresponding intent depending on what menu item has been clicked
		switch (position) {
		case 0:
			i = new Intent(Menu.this, DataEntry.class);
			break;
		case 1:
			i = new Intent(Menu.this, SelectGraph.class);
			break;
		case 2:
			i = new Intent(Menu.this, RenderPage.class);
			break;
	/*	case 3:
			i = new Intent(Menu.this, CreateResultPDF.class);
			break;*/
		case 5:
			i = new Intent(Menu.this, AlarmList.class);
		}
		startActivity(i);
	}

}
