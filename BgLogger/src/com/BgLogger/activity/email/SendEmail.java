package com.BgLogger.activity.email;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

public class SendEmail extends Activity {

	String[] mailto;

	
	private String test;
	private String date;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//get the data passed to the intent
		test = getIntent().getStringExtra("test");
		date = getIntent().getStringExtra("date");
		mailto = new String[getIntent().getStringArrayListExtra("emails").size()];
		mailto = getIntent().getStringArrayListExtra("emails").toArray(mailto);
				
			
		File f = new File(Environment.getExternalStorageDirectory(),"pdf/"); 
     /*   if (!f.exists())
        {
            f.mkdirs();
        } */             
        
        File file=new File(f,test + ".pdf" );
        Uri uri =Uri.fromFile(file);
		Intent emailIntent = new Intent(Intent.ACTION_SEND); //android intent to send emails
		emailIntent.putExtra(Intent.EXTRA_EMAIL, mailto);  //set the recipient email address
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, test + " " + date); //set the subject
		////set the body of the email
		emailIntent.putExtra(Intent.EXTRA_TEXT, "Doctor,\nFollowing is the results for the " + test + " test done on " + date );
		//set the type of the attachment to pdf
		emailIntent.setType("application/pdf");
		emailIntent.putExtra(Intent.EXTRA_STREAM, uri); //attach the test result pdf

		startActivity(Intent.createChooser(emailIntent, "Send mail...")); //strat activiy
		finish();
	}

}
