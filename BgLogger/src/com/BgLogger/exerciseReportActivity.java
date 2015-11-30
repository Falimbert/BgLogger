package com.BgLogger;
import android.app.Activity;
import com.BgLogger.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.database.*;
import android.widget.*;

public class exerciseReportActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.notesreport);
	        View button1Click = findViewById(R.id.button1);
	        button1Click.setOnClickListener(this);
	        View button2Click = findViewById(R.id.button2);
	        button2Click.setOnClickListener(this);  
	       
	        Button buttonsaveback = (Button) findViewById(R.id.buttonsaveback);
	        buttonsaveback.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	        Intent conchomebackintent = new Intent();
	        setResult(RESULT_OK, conchomebackintent);
	        finish();
	        }
	        });
	        
	   }
	   public void onClick(View v) {

	        switch(v.getId()){
	            case R.id.button1:
	                Intent i = new Intent(this, SaveNote.class);  
	                startActivity(i);
	            break;
	            case R.id.button2:
	                Intent i1 = new Intent(this, ViewNotes.class);  
	                startActivity(i1);
	            break;
	        }
	    }


}











