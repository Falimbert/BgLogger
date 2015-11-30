package com.BgLogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.BgLogger.R;
import com.BgLogger.R.array;
import com.BgLogger.R.id;
import com.BgLogger.R.layout;

//activity to create custom log

public class MedicineActivity extends ActionBarActivity {

	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;
	private MedicineDBAdapter MedicationdbAdapter;
	private GregorianCalendar calendar;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm aa");

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.medicine_log);
		title(getString(R.string.new_medication_record));

		final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.medicine_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		// final MedicationDBAdapter MedicationdbAdapter;

		final Spinner spinner2 = (Spinner) findViewById(R.id.Spinner01);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.diabetic_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);

		final Spinner spinner3 = (Spinner) findViewById(R.id.spinnerUnits);
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter
				.createFromResource(this, R.array.Units_array,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(adapter3);

		MedicationdbAdapter = new MedicineDBAdapter(this);
		MedicationdbAdapter.openToWrite();
		

		Button backbutton = (Button) findViewById(R.id.back);
		backbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}

		});

		Button btnAddMedication = (Button) findViewById(R.id.buttonAddMedication);
		btnAddMedication.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				EditText editTextDosage;
				TextView txtMsgbox;
			

				editTextDosage = (EditText) findViewById(R.id.editTextDosage);
				txtMsgbox = (TextView) findViewById(R.id.txtMsgBoxMed);

				String Dosageinput = editTextDosage.getText().toString();

				Spinner spinnerMedication = (Spinner) findViewById(R.id.spinner1);
				String SpinnerMedicationText = spinnerMedication
						.getSelectedItem().toString();

				Spinner spinnerMethod = (Spinner) findViewById(R.id.Spinner01);
				String SpinnerMethodText = spinnerMethod.getSelectedItem()
						.toString();

				Spinner spinnerUnits = (Spinner) findViewById(R.id.spinnerUnits);
				String SpinnerUnitsText = spinnerUnits.getSelectedItem()
						.toString();

				TimePicker timePicker = (TimePicker) findViewById(R.id.timePickerMedication);
				DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerMedication);

				int day = datePicker.getDayOfMonth();
				int month = datePicker.getMonth();
				int year = datePicker.getYear();
				int hour = timePicker.getCurrentHour();
				int minute = timePicker.getCurrentMinute();
				calendar = new GregorianCalendar();
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DAY_OF_MONTH, day);
				calendar.set(Calendar.HOUR, hour);
				calendar.set(Calendar.MINUTE, minute);
				
				String DosageStr;
				

				if (Dosageinput != null && Dosageinput.length() > 0) {

					DosageStr = Dosageinput;

					MedicationdbAdapter.insert(sdf.format(calendar.getTime()),
							DosageStr, SpinnerMedicationText, SpinnerUnitsText, SpinnerMethodText);
				
					txtMsgbox.setText("Medication has been added.");
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						public void run() {
							Intent intent = new Intent();
							setResult(RESULT_OK, intent);
							finish();
						}
					}, 2000);

				} else {
					txtMsgbox.setText("Please enter duration of Medication.");
				}

			}
		});

	};

	@Override
	protected void onDestroy() {

		super.onDestroy();
		MedicationdbAdapter.close();
	}
}