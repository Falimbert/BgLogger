package com.BgLogger;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.BgLogger.R;

import java.text.SimpleDateFormat;
import java.util.Date;

//activity to create custom food log

public class FoodlogActivity extends ActionBarActivity {
	
	//declare buttons and listview
	Button buttonAdd;
	Button backbutton;
	Button deletebutton;
	
	ListView foodlist;
	
	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;
	private FoodDBAdapter mydbAdapter;
  
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlog);
        title(getString(R.string.food_log));
        
        buttonAdd = (Button)findViewById(R.id.add);
        deletebutton = (Button)findViewById(R.id.delete);
        backbutton = (Button)findViewById(R.id.back);
        foodlist = (ListView)findViewById(R.id.foodlist);
        
        // get data from food table
        
        mydbAdapter = new FoodDBAdapter(this);
        mydbAdapter.openToWrite();
        cursor = mydbAdapter.queueAll();
      
        String [] from = new String [] {FoodDBAdapter.KEY_DATE, FoodDBAdapter.KEY_CARBS, FoodDBAdapter.KEY_NAME, 
        		FoodDBAdapter.KEY_LOAD};
        int [] to = new int [] { R.id.text1, R.id.text2, R.id.text3, R.id.text4};
        
        cursorAdapter = new SimpleCursorAdapter (this, R.layout.row, cursor, from, to);
        foodlist.setAdapter(cursorAdapter);
        
        Button backbutton = (Button) findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

        });
        
        
        buttonAdd.setOnClickListener(buttonAddOnClickListener);
        
        deletebutton.setOnClickListener(buttondeleteOnClickListener);
        
    
        
    }
    
    //alert dialogue to allow user to enter data in food table
    
    Button.OnClickListener buttonAddOnClickListener = new Button.OnClickListener() {
        
    	public void onClick(View v) {
			 
    		AlertDialog alertd = new AlertDialog.Builder(FoodlogActivity.this).create();
    		alertd.setTitle("Enter carbs, food name, and glycemic load");
    		
    		final EditText dialog_carbs = new EditText (FoodlogActivity.this);
    		dialog_carbs.setInputType(InputType.TYPE_CLASS_NUMBER);
    		final EditText dialog_name = new EditText (FoodlogActivity.this);
    		final EditText dialog_load = new EditText (FoodlogActivity.this);
    		dialog_load.setInputType(InputType.TYPE_CLASS_NUMBER);
    		
    		
    		LayoutParams dial_param = new LayoutParams (LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
    		dialog_carbs.setLayoutParams(dial_param);
    		dialog_name.setLayoutParams(dial_param);
    		dialog_load.setLayoutParams(dial_param);
    		
    		LinearLayout layout = new LinearLayout(FoodlogActivity.this);
    		layout.setOrientation(LinearLayout.VERTICAL);
    		layout.addView(dialog_carbs);
    		layout.addView(dialog_name);
    		layout.addView(dialog_load);
    		alertd.setView(layout);
    		
    		
    		alertd.setButton("Add", new DialogInterface.OnClickListener(){
    			
    			public void onClick (DialogInterface dia, int which){
    				
    			
    			
    			String data1 = dialog_carbs.getText().toString();
    			Date date = new Date();
    			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    			String today = dateFormat.format(date);
    			int num1 = Integer.parseInt(data1);
    			String data2 = dialog_name.getText().toString();
    			String data3 = dialog_load.getText().toString();
    			int num3 = Integer.parseInt(data3);
    			mydbAdapter.insert(today, num1, data2, num3);
    			cursor.requery();
    			
    			}
    		});
    		
			alertd.show();
		}
    	
    		
    	
    };
    
    //alert dialogue to delete entry by name
    

Button.OnClickListener buttondeleteOnClickListener = new Button.OnClickListener() {
    
	public void onClick(View v) {
		 
		AlertDialog alertd2 = new AlertDialog.Builder(FoodlogActivity.this).create();
		alertd2.setTitle("Enter name of food to delete");
		
		 
		final EditText dialog_name2 = new EditText (FoodlogActivity.this);
		 
		
		LayoutParams dial_param = new LayoutParams (LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		 
		dialog_name2.setLayoutParams(dial_param);
		 
		
		LinearLayout layout = new LinearLayout(FoodlogActivity.this);
		layout.setOrientation(LinearLayout.VERTICAL);
		 
		layout.addView(dialog_name2);
		 
		alertd2.setView(layout);
		
		
		alertd2.setButton("Delete", new DialogInterface.OnClickListener(){
			
			public void onClick (DialogInterface dia, int which){
				
			
			
			String data1 = dialog_name2.getText().toString();
			 
			mydbAdapter.delete_byName(data1);
			cursor.requery();
			
			}
		});
		
		alertd2.show();
	}
    
};
    
    
    
    
    @Override
    protected void onDestroy() {
    	
    	super.onDestroy();
    	mydbAdapter.close();
    }
}
