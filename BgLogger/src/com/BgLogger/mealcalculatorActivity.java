package com.BgLogger;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.BgLogger.R;

public class mealcalculatorActivity extends ActionBarActivity {
	
	public static double totalglyload=0;
	public static double glyload=0;
	Button backbutton;
	Button calculatebutton;
	Button storebutton;
	Button resetbutton;
	EditText carb;
	EditText index;
	TextView results;
	
	
	@Override
	public void onCreate (Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculate);
		title(getString(R.string.meal_calculator));
		
		backbutton= (Button)findViewById(R.id.back);
		calculatebutton = (Button)findViewById(R.id.calculate);
		storebutton = (Button)findViewById(R.id.store);
		resetbutton = (Button)findViewById(R.id.reset);
		carb = (EditText)findViewById(R.id.carbs);
		index = (EditText)findViewById(R.id.index);
		results = (TextView)findViewById(R.id.result);
		
		carb.requestFocus();
		
		calculatebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                 
            	String carbinput = carb.getText().toString();
            	String indexinput = index.getText().toString();
            	
            	if (carbinput !=null && carbinput.length()>0 && indexinput !=null && indexinput.length() >0) 
            	{
            		double netcarbs = Double.parseDouble(carbinput);
            		double glyindex = Double.parseDouble(indexinput);
            		
            		
            		if (netcarbs == 0 || glyindex == 0)
            		{
            			glyload = 0;
            		}
            		
            		else 
            		{
            			glyload = glyindex/100*netcarbs;
            			
            		}
            		totalglyload=totalglyload+glyload;
            		results.setText("Total Glycemic Load =" + totalglyload);
            	}
            	
            	else
            	{
            	    results.setText("Please enter net carbs and glycemic index");	
            	}
            	}
            });

		resetbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                
            	
            	carb.setText("");
            	index.setText("");
            	results.setText("Glycemic Load=");
            	glyload=0;
            	totalglyload=0;
            	
            }
            });
		
		
		storebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                
            	
            	carb.setText("");
            	index.setText("");
            	results.setText("Glycemic Load=");
              
            	glyload=0;
            	
            }
            });
		
		
		backbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

        });
		
		
		
	}

}
