package com.BgLogger;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SaveNote extends ActionBarActivity implements OnClickListener {  
    private ReportDataManipulatorNotes dh;    
    static final int DIALOG_ID = 0;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savenote);
        title(getString(R.string.new_note));
        View add = findViewById(R.id.Button01add);
        add.setOnClickListener(this);
        
   
        
        Button buttonsaveback = (Button) findViewById(R.id.Button01home);
        buttonsaveback.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
        Intent conchomebackintent = new Intent();
        setResult(RESULT_OK, conchomebackintent);
        finish();
        }
        });
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.Button01home:
                Intent i = new Intent(this, contactReportActivity.class);
                startActivity(i);
            break;
            case R.id.Button01add:
            	
            	//String date_add = DateFormat.getDateInstance().format(myDate);
            	
                //View editText1 = (EditText) findViewById(R.id.datein);
                View editText2 = (EditText) findViewById(R.id.note);
                
                

                
               // String myFormat = "yyyy-MM-dd";
               // SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                //String myEditText1 = sdf.format(editText1);
                
                
                //String myEditText1 = "2012-07-24";
                
                //String myEditText1=((TextView) editText1).getText().toString();
                
                String myEditText2=((TextView) editText2).getText().toString();
              
                if (!(myEditText2.equals(""))){
                	this.dh = new ReportDataManipulatorNotes(this);
                    this.dh.insert(myEditText2);
                    showDialog(DIALOG_ID);
                }
               
            break;
        }
    }  
    protected final Dialog onCreateDialog(final int id) {
        Dialog dialog = null;
        switch(id) {
            case DIALOG_ID:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Information saved successfully! Add Another Info?").setCancelable(false).setPositiveButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        	SaveNote.this.finish();
                        }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                        }
                });
                AlertDialog alert = builder.create();
                dialog = alert;
                break;

             default:
        }
        return dialog;
    }
}