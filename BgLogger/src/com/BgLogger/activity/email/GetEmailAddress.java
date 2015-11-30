package com.BgLogger.activity.email;

import java.util.ArrayList;
import java.util.List;

import com.BgLogger.ActionBarActivity;
import com.BgLogger.R;
import com.BgLogger.ReportDataManipulator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class GetEmailAddress extends ActionBarActivity implements OnClickListener {

	private String[] emailList;
	private ReportDataManipulator db;
	private List<String[]> names2;
	private Button send;
	private ListView listView;
	private ArrayList<String> emailAddresses = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_email);
		title(getString(R.string.contacts));
		send = (Button) findViewById(R.id.bGetEmail);
		send.setOnClickListener(this);

		getArray();

		// The checkbox for each item is specified by the layout
		// android.R.layout.simple_list_item_multiple_choice
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, emailList);

		// Getting the reference to the listview object of the layout
		listView = (ListView) findViewById(R.id.lvEmail);

		// Setting adapter to the listview
		listView.setAdapter(adapter);
	}

	private void getArray() {
		db = new ReportDataManipulator(this);
		names2 = db.selectEmail();

		emailList = new String[names2.size()];
		int x = 0;
		String stg;

		for (String[] name : names2) {
			stg = name[0] + "-" + name[1];
			emailList[x] = stg;
			x++;
		}
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId() == R.id.bGetEmail) {
			SparseBooleanArray checked = listView.getCheckedItemPositions();
			int length = listView.getCount();
			for (int i = 0; i < length; i++) {
				if (checked.get(i)) {
					emailAddresses.add((names2.get(i))[1]);
				}
			}
		}
		if (emailAddresses.size() > 0) {

			Intent intent = new Intent();
			intent.putStringArrayListExtra("emailArray", emailAddresses);

			setResult(RESULT_OK, intent);

			finish();// finishing activity
		}
		else{
			setResult(RESULT_CANCELED, null);
			finish();
		}

	}

}
