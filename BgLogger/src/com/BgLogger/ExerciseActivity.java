package com.BgLogger;


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

import com.BgLogger.R;
import com.BgLogger.R.array;
import com.BgLogger.R.id;
import com.BgLogger.R.layout;



//activity to create custom log

public class ExerciseActivity extends ActionBarActivity {
	

	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;
	private ExerciseDBAdapter ExercisedbAdapter;
  
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_log);
        title(getString(R.string.new_exercise_record));

        final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.exercise_array, android.R.layout.simple_spinner_item);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	
		//final ExerciseDBAdapter ExercisedbAdapter;
                                                   
		ExercisedbAdapter = new ExerciseDBAdapter(this);
		ExercisedbAdapter.openToWrite();
		cursor = ExercisedbAdapter.queueAll();

		
		String [] from = new String [] {ExerciseDBAdapter.KEY_WorkoutDATE, ExerciseDBAdapter.KEY_WorkoutDurationMinutes, ExerciseDBAdapter.KEY_WorkoutNAME};
        int [] to = new int [] { R.id.text1, R.id.text2, R.id.text3, R.id.text4};
        
        cursorAdapter = new SimpleCursorAdapter (this, R.layout.row, cursor, from, to);
        
        
        Button backbutton = (Button) findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

        });
        
        
 
        
    Button btnAddWorkout = (Button) findViewById(R.id.buttonAddWorkout);
	btnAddWorkout.setOnClickListener(new View.OnClickListener(){
	public void onClick(View v) {
		
		EditText editTextHours;
		EditText editTextMinutes;
		TextView txtMsgbox;
		Integer Hours;
		Integer Minutes;
		Integer TotalMinutes;
		
		Minutes = 0;
		Hours = 0;
		
		
		
    	editTextHours = (EditText)findViewById(R.id.editTextHours);
		editTextMinutes = (EditText)findViewById(R.id.editTextMinutes);
		txtMsgbox = (TextView)findViewById(R.id.txtMsgBox);
		
		

		Spinner spinnerExercise = (Spinner)findViewById(R.id.spinner1);
		String hoursinput = editTextHours.getText().toString();
        String minutesinput = editTextMinutes.getText().toString();
        String SpinnerExerciseText = spinnerExercise.getSelectedItem().toString();
        
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerExercise);
		int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
		String CurrentDate;
        
        CurrentDate = String.valueOf(month)+"/"+String.valueOf(day)+"/"+String.valueOf(year);
		if ((minutesinput != null && minutesinput.length()>0) || (hoursinput != null && hoursinput.length() >0)) 
    	{
			if (hoursinput != null && hoursinput.length() >0){
	        	Hours = Integer.parseInt(hoursinput);
	        } 
			
			if (minutesinput != null && minutesinput.length()>0){
				Minutes = Integer.parseInt(hoursinput);
	        } 
			
			TotalMinutes = (Hours * 60) + (Minutes); 
				
			ExercisedbAdapter.insert(CurrentDate, TotalMinutes, SpinnerExerciseText);
			//cursor.requery();
			txtMsgbox.setText("The following workout has been added:" + System.getProperty("line.separator") +
			"Date: " + String.valueOf(CurrentDate) + System.getProperty("line.separator") +
			"Duration in minutes: " + String.valueOf(TotalMinutes) + System.getProperty("line.separator") + 
			"Type of exercise: " + String.valueOf(SpinnerExerciseText));
			 Handler handler = new Handler(); 
			    handler.postDelayed(new Runnable() { 
			         public void run() { 
			        	 Intent intent = new Intent();
			             setResult(RESULT_OK, intent);
			             finish(); 
			         } 
			    }, 2000); 
			
    	
    	}
    	else
    	{
    		txtMsgbox.setText("Please enter duration of exercise.");
    	}

	}
});

};

    @Override
    protected void onDestroy() {
    	
    	super.onDestroy();
    	ExercisedbAdapter.close();
    }
}