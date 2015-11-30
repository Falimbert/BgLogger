package com.BgLogger.activity.insulin;

import java.util.Calendar;

import com.BgLogger.ActionBarActivity;
import com.BgLogger.R;
import com.BgLogger.model.insulin.InsulinLog;
import com.BgLogger.model.insulin.InsulinLogDao;
import com.BgLogger.model.insulin.InsulinTypeDao;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

public class AddInsulinLogActivity extends ActionBarActivity{
	private final View.OnClickListener cancelButtonOnClickListener = new View.OnClickListener() {
    	public void onClick(View v) {
    		Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
    	}
	};
	
	private final Button.OnClickListener submitButtonOnClickListener = new Button.OnClickListener() {
    	public void onClick(View v) {
    		InsulinLog log = new InsulinLog();
    		insulinLogDao.openToWrite();
    		
    		Spinner insulinTypeSpinner = (Spinner)findViewById(R.id.InsulinTypeSpinner);
    		Cursor cursor = (Cursor)insulinTypeSpinner.getSelectedItem();
    		Long insulinTypeId = cursor.getLong(cursor.getColumnIndex(InsulinTypeDao.ID_FIELD_NAME));

    		log.setInsulinTypeId(insulinTypeId);
 
    		EditText readingEditText = (EditText)findViewById(R.id.InsulinDosageEditText);
    		log.setDosage(Integer.parseInt((readingEditText.getText().toString())));
    		
    		DatePicker datePicker = (DatePicker)findViewById(R.id.InsulinLogDate);
    		int year = datePicker.getYear();
    		int day = datePicker.getDayOfMonth();
    		int month = datePicker.getMonth();
    		
    		TimePicker timePicker = (TimePicker)findViewById(R.id.InsulinLogTime);
    		int hour = timePicker.getCurrentHour();
    		int minute = timePicker.getCurrentMinute();
    		
    		Calendar calendar = Calendar.getInstance();
    		calendar.set(Calendar.YEAR, year);
    		calendar.set(Calendar.MONTH, month);
    		calendar.set(Calendar.DAY_OF_MONTH, day);
    		calendar.set(Calendar.HOUR, hour);
    		calendar.set(Calendar.MINUTE, minute);
    		
    		log.setLogTime(calendar.getTime());
    		
    		insulinLogDao.insert(log);
    		
    		Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
    	}
	};
	
	private InsulinLogDao insulinLogDao;
	private InsulinTypeDao insulinTypeDao;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_insulin_log);
        title(getString(R.string.new_insulin_record));
        
        insulinLogDao = new InsulinLogDao(this);
        insulinTypeDao = new InsulinTypeDao(this);
        
        Button cancelButton = (Button)findViewById(R.id.InsulinLogCancelButton);
        Button submitButton = (Button)findViewById(R.id.InsulinLogSubmitButton);
        
        cancelButton.setOnClickListener(cancelButtonOnClickListener);
        submitButton.setOnClickListener(submitButtonOnClickListener);
        
        try{
	        insulinTypeDao.openToRead();
	        Cursor cursor = insulinTypeDao.queueAll();
	        
	        String [] field = new String [] {InsulinTypeDao.NAME_FIELD_NAME};
	        int [] viewId = new int [] {R.id.InsulinTypeTextView};
	        
	        @SuppressWarnings("deprecation")
			SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.insulin_type_spinner_template, cursor, field, viewId);
	        simpleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        
	        Spinner insulinTypeSpinner = (Spinner)findViewById(R.id.InsulinTypeSpinner);
	        insulinTypeSpinner.setAdapter(simpleCursorAdapter);
        } finally {
        	insulinTypeDao.close();
        }
	}
}
