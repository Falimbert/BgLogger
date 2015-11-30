package com.BgLogger;

import com.BgLogger.model.glucose.BloodGlucoseLogDao;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MedicineHistory extends ActionBarActivity {

	private ListView listView;
	private MedicineDBAdapter data;
	private Cursor allResultsCursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bloodreport);
		title(getString(R.string.medicine_history));

		// get the list view reference
		listView = (ListView) findViewById(R.id.BloodlistView);

		// create an array containing the column names of the database
		String[] names = { MedicineDBAdapter.KEY_MedicationDATE,
				MedicineDBAdapter.KEY_MedicationName };

		// create an array containing the id's of the text views where test list
		// is displayed
		int[] array = { R.id.tvTest, R.id.tvDate };

		data = new MedicineDBAdapter(MedicineHistory.this);
		data.openToRead();
		allResultsCursor = data.queueAll();

		// create an adapter with data in the returned cursor
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				MedicineHistory.this, R.layout.list_item_each,
				allResultsCursor, names, array);

		// add this adapter to the list view
		listView.setAdapter(adapter);

		data.close();

	}

}
