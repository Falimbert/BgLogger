package com.BgLogger;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ViewNotes extends ActionBarActivity {
	TextView selection;
	public int idToModify;
	ReportDataManipulatorNotes dm;

	List<String[]> list = new ArrayList<String[]>();
	List<String[]> names2 = null;
	String[] stg1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noteview);
		title(getString(R.string.notes));
		
		dm = new ReportDataManipulatorNotes(this);
		names2 = dm.selectAll();

		stg1 = new String[names2.size()];
		int x = 0;
		String stg;

		for (String[] name : names2) {
			stg = name[1];
			stg1[x] = stg;
			x++;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stg1);
		ListView lv = (ListView)findViewById(R.id.noteslist);
		lv.setAdapter(adapter);
		selection = (TextView) findViewById(R.id.selection);

		Button buttonbackcheck = (Button) findViewById(R.id.buttonbackcheck);
		buttonbackcheck.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent checkbackintent = new Intent();
				setResult(RESULT_OK, checkbackintent);
				finish();
			}
		});

	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		selection.setText(stg1[position]);
	}

}
