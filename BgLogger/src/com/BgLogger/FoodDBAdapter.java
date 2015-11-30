package com.BgLogger;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import com.BgLogger.R;

//class to use SQLite Database
public class FoodDBAdapter {
	
	//variables declaration
	
	public static final String DB_Name = "BGLOGGER_DATABASE2.db";
	public static final String Food_Table = "Food_Table";
	public static final int DB_Version = 1;
	public static final String KEY_ID = "_id";
	public static final String KEY_DATE = "DATE";
	public static final String KEY_CARBS = "CARBS";
	public static final String KEY_NAME = "FOOD_NAME";
	public static final String KEY_LOAD = "GLYCEMIC_LOAD";
	
	private static final String DBSCRIPT = 
			"CREATE TABLE FOOD_TABLE (" +
			"_id integer primary key," +
		    "DATE datetime not null, "+
			"CARBS integer not null," +
			"FOOD_NAME String not null," +
			"GLYCEMIC_LOAD INTEGER NOT NULL);";
			
				
	private SQLiteHelper Helper;
	
	private SQLiteDatabase sqLiteDatabase;
	
	private Context context;
	
	public FoodDBAdapter (Context c)  {
		
	context = c;	
	}
	
	//open to read method opens database to read
	
	public FoodDBAdapter openToRead() throws android.database.SQLException {
		
	Helper = new SQLiteHelper (context, DB_Name, null, DB_Version);
	
	sqLiteDatabase = Helper.getReadableDatabase();
	
	return this;
	}
	
	//open to write method opens database to write
	
	public FoodDBAdapter openToWrite() throws android.database.SQLException {
		
	Helper = new SQLiteHelper (context, DB_Name, null, DB_Version);
	sqLiteDatabase = Helper.getWritableDatabase();
	return this;
	
		
	}
	
	public void close () {
		
		Helper.close();
		
	}
	
	//insert method inserts new data into Food_Table
	
	public long insert (String date, int carbs, String name, int load) {
		ContentValues content =new ContentValues();

		content.put(KEY_DATE, date);
		content.put(KEY_CARBS, carbs);
		content.put(KEY_NAME, name);
		content.put(KEY_LOAD, load);
		
		
		return sqLiteDatabase.insert(Food_Table, null, content);
		
	}
	
	//delete entry by name with this method
	
	public void delete_byName(String Name){
		String deleteSql = "DELETE FROM FOOD_TABLE WHERE Food_Name=" +
				"'" + Name + "'";
		sqLiteDatabase.rawQuery(deleteSql, null).moveToFirst();

		}
	
	//retrieve all data from food table with this method
	
   public Cursor queueAll () {
	   
	   
	   Cursor cursor = sqLiteDatabase.rawQuery("select rowid _id, * from Food_Table", null);
	   return cursor;
	   
   }
   
///////////////////////MAYRA CODE STARTS HERE///////////////////////////////
public Cursor SUMtotal () {
	   Cursor cursor = sqLiteDatabase.rawQuery("select rowid _id, DATE, CARBS, GLYCEMIC_LOAD, sum(CARBS) as CARBS, sum(GLYCEMIC_LOAD) as GLYCEMIC_LOAD, strftime('%Y-%m-%d', DATE) as DATE from Food_Table group by DATE", null);
	   return cursor;
   }
///////////////////////MAYRA CODE ENDS HERE///////////////////////////////

   public class SQLiteHelper extends SQLiteOpenHelper {   
	   
	   public SQLiteHelper (Context context, String name, CursorFactory factory, int version) {
		   super (context, name, factory, version);
	   }
	   
	   @Override
	   public void onCreate (SQLiteDatabase db) {
		   db.execSQL(DBSCRIPT);
	   }
	   
	   @Override
	   public void onUpgrade (SQLiteDatabase db, int oldversion, int newversion) {
		   
		   
	   }
   }
}
