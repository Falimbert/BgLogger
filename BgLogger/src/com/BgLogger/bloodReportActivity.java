package com.BgLogger;

import android.app.Activity;
import com.BgLogger.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.database.*;
import android.widget.*;


/**
 * @creator  Hansika
 * @editor 	 Limas Baginta,
 * Created:	 27/08/2015, 
 * Modified: 29/11/2015
 */
 
public class bloodReportActivity extends Activity {
	private FoodDBAdapter blooddatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bloodreport);

		ListView bloodData = (ListView) findViewById(R.id.BloodlistView);

		blooddatabase = new FoodDBAdapter(this);
		blooddatabase.openToWrite();
		Cursor results = blooddatabase.queueAll();

		String[] from = new String[] { FoodDBAdapter.KEY_DATE,
				FoodDBAdapter.KEY_CARBS, FoodDBAdapter.KEY_NAME,
				FoodDBAdapter.KEY_LOAD };
		int[] to = new int[] { R.id.text1, R.id.text2, R.id.text3, R.id.text4 };

		SimpleCursorAdapter displayAdapter = (SimpleCursorAdapter) new SimpleCursorAdapter(
				this, R.layout.row, results, from, to);
		bloodData.setAdapter(displayAdapter);

		Button bloodBackbutton = (Button) findViewById(R.id.bloodBackbutton);
		bloodBackbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent bloodintent = new Intent();
				setResult(RESULT_OK, bloodintent);
				finish();
			}
		});

	}
}
