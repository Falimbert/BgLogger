package com.BgLogger.activity.reminders;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.BgLogger.ActionBarActivity;
import com.BgLogger.R;
import com.BgLogger.activity.glucose.AddGlucoseLogActivity;
import com.BgLogger.model.alarm.Alarm;
import com.BgLogger.model.alarm.AlarmDao;
import com.BgLogger.model.glucose.BloodGlucoseLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddNewAlarm extends ActionBarActivity implements OnItemSelectedListener {

	private Button cancelButton, submitButton;
	private DatePicker datePicker;
	private TimePicker timePicker;
	private GregorianCalendar calendar;
	private Spinner test_type;
	private String test;
	private AlarmDao alarmData;

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy/MM/dd hh:mm aa");
	private Bundle basket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_alarm);
	
		cancelButton = (Button) findViewById(R.id.CancelButton);
		submitButton = (Button) findViewById(R.id.SubmitButton);
		datePicker = (DatePicker) findViewById(R.id.BloodGlucoseLogDate);

		timePicker = (TimePicker) findViewById(R.id.BloodGlucoseLogTime);
		timePicker.setIs24HourView(false);

		cancelButton.setOnClickListener(cancelButtonOnClickListener);
		submitButton.setOnClickListener(submitButtonOnClickListener);
		calendar = new GregorianCalendar();

		// create the spinner for test type
		test_type = (Spinner) findViewById(R.id.GlucoseTypeSpinner);
		ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(
				AddNewAlarm.this, R.array.test_types,
				android.R.layout.simple_list_item_1);

		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		test_type.setAdapter(adp);
		test_type.setOnItemSelectedListener(this);

		basket = getIntent().getExtras();

		if (basket != null) {
			title(getString(R.string.edit_reminder));
			String date = basket.getString("date");
			String test = basket.getString("test");

			ArrayAdapter myAdapter = (ArrayAdapter) test_type.getAdapter();
			int position = myAdapter.getPosition(test);
			test_type.setSelection(position);
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(sdf.parse(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datePicker.updateDate(cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
			timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
		}
		else{
			title(getString(R.string.add_new_reminder));
		}
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		switch (parent.getId()) {

		// set the test type when a test type item has been selected from the
		// spinner
		case R.id.GlucoseTypeSpinner:
			test = (String) parent.getItemAtPosition(pos);
			break;
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	private final Button.OnClickListener cancelButtonOnClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			setResult(RESULT_OK, intent);
			finish();
		}
	};

	private final Button.OnClickListener submitButtonOnClickListener = new Button.OnClickListener() {

		public void onClick(View v) {

			alarmData = new AlarmDao(AddNewAlarm.this);
			alarmData.openToWrite();

			int year = datePicker.getYear();
			int day = datePicker.getDayOfMonth();
			int month = datePicker.getMonth();

			int hour = timePicker.getCurrentHour();
			int minute = timePicker.getCurrentMinute();

			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minute);

			Alarm alarm = new Alarm();
			alarm.setAlarmTime(calendar.getTime());
			alarm.setTestType(test);
			alarm.setEnabled(true);
			Intent intent = new Intent();
			if (basket == null) {
				alarmData.insertAlarm(alarm);
				alarmData.close();
				// create = new AlarmCreateDelete(AddNewAlarm.this);
				AlarmManagerBroadcastReceiver.scheduleAlarms(test, calendar,
						AddNewAlarm.this);


			}
			else{

				intent.putExtra("data", alarm);
				intent.putExtra("id", basket.getInt("id"));
			}
			
			setResult(RESULT_OK, intent);

			// create a toast to be displayed when the reminder is set
			Toast.makeText(
					AddNewAlarm.this,
					"Alarm Scheduled for " + test + " on "
							+ calendar.getTime().toString(), Toast.LENGTH_LONG)
					.show();
			finish();

		}
	};

}
