package com.BgLogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

//import com.BgLogger.FoodDBAdapter.SQLiteHelper;

public class ExerciseDBAdapter {

	// variables declaration

	public static final String DB_Name = "BGLOGGER_DATABASE_3.db";
	public static final String Exercise_Schedule = "Exercise_Schedule";
	public static final int DB_Version = 1;
	public static final String KEY_ID = "_id";
	public static final String KEY_WorkoutDATE = "WorkoutDATE";
	public static final String KEY_WorkoutDurationMinutes = "WorkoutDurationMinutes";
	public static final String KEY_WorkoutNAME = "WorkoutNAME";

	private static final String DBSCRIPT = "CREATE TABLE IF NOT EXISTS Exercise_Schedule ("
			+ "_id integer primary key,"
			+ "WorkoutDATE String not null,"
			+ "WorkoutDurationMinutes integer not null,"
			+ "WorkoutNAME String not null);";

	private static final String DBSCRIPT2 = "CREATE TABLE IF NOT EXISTS Medication_Schedule ("
			+ "_id integer primary key,"
			+ "MedicationDATE datetime not null, "
			+
			// "MedicationTime String not null," +
			"MedicationDosage String not null,"
			+ "MedicationName String not null,"
			+ "MedicationUnit String not null,"
			+ "MedicationMethodOfDelivery String not null);";

	private Context context;
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;

	public ExerciseDBAdapter(Context c) {
		context = c;
	}

	public void onOpen(SQLiteDatabase db) {
		db.execSQL(DBSCRIPT);
	}

	public ExerciseDBAdapter openToRead() throws android.database.SQLException {
		dbHelper = new SQLiteHelper(context, DB_Name, null, DB_Version);
		db = dbHelper.getReadableDatabase();
		return this;
	}

	public ExerciseDBAdapter openToWrite() throws android.database.SQLException {
		dbHelper = new SQLiteHelper(context, DB_Name, null, DB_Version);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long insert(String WorkoutDATE, int WorkoutDurationMinutes,
			String WorkoutNAME) {
		ContentValues content = new ContentValues();
		content.put(KEY_WorkoutDATE, WorkoutDATE);
		content.put(KEY_WorkoutDurationMinutes, WorkoutDurationMinutes);
		content.put(KEY_WorkoutNAME, WorkoutNAME);

		return db.insert(Exercise_Schedule, null, content);

	}

	// delete entry by name with this method

	public void delete_byName(String Name) {
		String deleteSql = "DELETE FROM Exercise_Schedule WHERE WorkoutNAME="
				+ "'" + Name + "'";
		db.rawQuery(deleteSql, null).moveToFirst();

	}

	// retrieve all data from food table with this method

	public Cursor queueAll() {

		Cursor cursor = db.rawQuery(
				"select rowid _id, * from Exercise_Schedule", null);
		return cursor;

	}

	public class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DBSCRIPT);
			db.execSQL(DBSCRIPT2);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {

		}
	}
}
