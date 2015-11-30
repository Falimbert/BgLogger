package com.BgLogger.activity.glucose;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.BgLogger.ActionBarActivity;
import com.BgLogger.R;
import com.BgLogger.activity.reminders.AlarmManagerBroadcastReceiver;
import com.BgLogger.model.alarm.Alarm;
import com.BgLogger.model.alarm.AlarmDao;
import com.BgLogger.model.glucose.BloodGlucoseLog;
import com.BgLogger.model.glucose.BloodGlucoseLogDao;
import com.BgLogger.model.glucose.BloodGlucoseMeasurementUnitDao;
import com.BgLogger.model.glucose.BloodGlucoseTypeDao;

public class AddGlucoseLogActivity extends ActionBarActivity implements
		OnItemSelectedListener {

	Button cancelButton, submitButton, addReminder, noReminder;
	DatePicker datePicker;
	Spinner test_type;
	String test;
	Spinner measurement_unit;
	String unit;
	int testPosition;
	EditText readingEditText;
	TimePicker timePicker;
	GregorianCalendar calendar, alarmTime;
	Dialog dialog1;

	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;
	//AlarmCreateDelete alarmCreateDelete;
	private BloodGlucoseLogDao bloodGlucoseLogDao;
	private AlarmDao dataAccess;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_glucose_log);
		title(getString(R.string.add_test_result));
		
		bloodGlucoseLogDao = new BloodGlucoseLogDao(this);
	
		cancelButton = (Button) findViewById(R.id.CancelButton);
		submitButton = (Button) findViewById(R.id.SubmitButton);
		datePicker = (DatePicker) findViewById(R.id.BloodGlucoseLogDate);
		readingEditText = (EditText) findViewById(R.id.ResultEditText);
		timePicker = (TimePicker) findViewById(R.id.BloodGlucoseLogTime);
		timePicker.setIs24HourView(false);

		cancelButton.setOnClickListener(cancelButtonOnClickListener);
		submitButton.setOnClickListener(submitButtonOnClickListener);
		calendar = new GregorianCalendar();

		// create the spinner for test type
		test_type = (Spinner) findViewById(R.id.GlucoseTypeSpinner);
		ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(
				AddGlucoseLogActivity.this, R.array.test_types,
				android.R.layout.simple_list_item_1);

		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		test_type.setAdapter(adp);
		test_type.setOnItemSelectedListener(this);

		//create the spinner for measurement unit
		measurement_unit = (Spinner) findViewById(R.id.GlucoseUnitSpinner);
		ArrayAdapter<CharSequence> adp1 = ArrayAdapter.createFromResource(
				AddGlucoseLogActivity.this, R.array.test_units,
				android.R.layout.simple_list_item_1);

		adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		measurement_unit.setAdapter(adp1);
		measurement_unit.setOnItemSelectedListener(this);

	}
	
	
	

	//on spinne item selected
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		switch (parent.getId()) {
		
		//set the test type when a test type item has been selected from the spinner
		case R.id.GlucoseTypeSpinner:
			test = (String) parent.getItemAtPosition(pos);
			break;
		//set the test unit when a measurement unit has been selected from the spinner
		case R.id.GlucoseUnitSpinner:
			unit = (String) parent.getItemAtPosition(pos);
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

			BloodGlucoseLog bgl = new BloodGlucoseLog();
			bloodGlucoseLogDao.openToWrite();
			bgl.setBloodGlucoseMeasurementUnitString(unit);
			bgl.setBloodGlucoseTypeString(test);

			bgl.setReading(BigDecimal.valueOf(Double
					.parseDouble((readingEditText.getText().toString()))));

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

			bgl.setLogTime(calendar.getTime());

			bloodGlucoseLogDao.insertBloodGlucoseLog(bgl);
			
			//pop up a dialog box to set up a reminder if the test is either fasting blood sugar or HBA1C 
			if (test.equals("Fasting Blood Sugar") || test.equals("HBA1C")) {
				onCreateDialog1();
				dialog1.show();
			}

		}
	};

	public Dialog onCreateDialog1() {

		Context context = AddGlucoseLogActivity.this;
		dialog1 = new Dialog(context);
		dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);  //request dialog box with no title

		dialog1.setContentView(R.layout.reminder_dialog);

		addReminder = (Button) dialog1.findViewById(R.id.bYReminder);  //button to add reminder
		noReminder = (Button) dialog1.findViewById(R.id.bNReminder); //button to dismiss dialog without reminder

		addReminder.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				dialog1.dismiss();
				//AlarmCreateDelete create;
			//	AlarmManagerBroadcastReceiver create;
				alarmTime = calendar;      //initialize the alarmTime calendar object

				if (test.equals("Fasting Blood Sugar")) {
					alarmTime.add(Calendar.SECOND, 10); //if the test is fasting blood sugar, increment the alarmTime object by seven days
				//	create = new AlarmCreateDelete(AddGlucoseLogActivity.this);
					AlarmManagerBroadcastReceiver.scheduleAlarms("Fasting Blood Sugar",alarmTime, AddGlucoseLogActivity.this);

				} else if (test.equals("HBA1C")) {
					alarmTime.add(Calendar.MONTH, 3);  //else if the test is HBA1C, increment the alarmTime object by three months  
				//	create = new AlarmCreateDelete(AddGlucoseLogActivity.this);
					AlarmManagerBroadcastReceiver.scheduleAlarms("HBA1C",alarmTime, AddGlucoseLogActivity.this);


				}
				
				
				Alarm alarm = new Alarm();
				alarm.setAlarmTime(alarmTime.getTime());
				alarm.setTestType(test);
				alarm.setEnabled(true);
				String d = alarm.getAlarmTimeFormatted();
				
				dataAccess = new AlarmDao(AddGlucoseLogActivity.this);
				dataAccess.openToWrite();   //open the database to write alarm details
				dataAccess.insertAlarm(alarm); 
				dataAccess.close();
	
				
				// create a toast to be displayed when the reminder is set
				Toast.makeText(
						AddGlucoseLogActivity.this,
						"Alarm Scheduled for " + test + " on "
								+ alarmTime.getTime().toString(), Toast.LENGTH_LONG)
						.show();

			}

		});

		noReminder.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog1.dismiss();

			}
		});

		return dialog1;
	}

	

}
