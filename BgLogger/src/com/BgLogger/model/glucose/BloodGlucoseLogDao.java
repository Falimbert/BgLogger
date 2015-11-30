package com.BgLogger.model.glucose;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.BgLogger.model.GenericDao;

/**
 * @creator  Hansika
 * @editor 	 Limas Baginta,
 * Created:	 27/08/2015, 
 * Modified: 29/11/2015
 */
 
public class BloodGlucoseLogDao extends GenericDao {
	public static final String LOG_TIME_FIELD_NAME = "log_time";
	public static final String READING_FIELD_NAME = "reading";
	public static final String MEASUREMENT_UNIT_FIELD_NAME = "blood_glucose_measurement_unit";
	public static final String TEST_TYPE_FIELD_NAME = "blood_glucose_type";

	private static final String TABLE_NAME = "blood_glucose_log";
	private static final String CREATE_SCRIPT = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " (" + "_id integer primary key autoincrement"
			+ ",blood_glucose_type varchar not null"
			+ ",blood_glucose_measurement_unit varchar not null"
			+ ",reading numeric not null" + ",log_time datetime not null);";

	public BloodGlucoseLogDao(Context context) {
		super(context, TABLE_NAME, CREATE_SCRIPT);
	}

	public BloodGlucoseLogDao openToRead() throws android.database.SQLException {
		return (BloodGlucoseLogDao) super.openToRead();
	}

	public BloodGlucoseLogDao openToWrite()
			throws android.database.SQLException {
		return (BloodGlucoseLogDao) super.openToWrite();
	}

	public Cursor queueAll() {
		return super.queueAll("log_time desc");
	}

	@SuppressLint("SimpleDateFormat")
	// method to return glucose readings and checkup dates for displaying on a
	// graph
	public Object[] getTestData(String test) {

		// get the cursor containing all the rows of the table
		Cursor c = super.queueAll("log_time desc"); 
		
		//get the column indexes of the columns 
		int iTime = c.getColumnIndex(LOG_TIME_FIELD_NAME);
		int iReading = c.getColumnIndex(READING_FIELD_NAME);
		int iUnit = c.getColumnIndex(MEASUREMENT_UNIT_FIELD_NAME);
		int iTest = c.getColumnIndex(TEST_TYPE_FIELD_NAME);

		//create two aray lists seperately for test readings and test dates
		ArrayList<Double> readings = new ArrayList<Double>();
		ArrayList<String> times = new ArrayList<String>();

		int i = 1;
		double value = 0;
		String date = null;

		//read the elements of the cursor
		for (c.moveToFirst(); i <= 10 && !c.isAfterLast(); c.moveToNext()) {
			String a = c.getString(iTest);

			if ((c.getString(iTest)).equals(test)) {
				if ((c.getString(iUnit)).equals("mmol/l")) {
					value = (c.getDouble(iReading)) * 18.0182; // convert mmol/l readings to mg/dl readings
				} else
					value = c.getDouble(iReading);

				date = c.getString(iTime);

				i++;
				readings.add(value);  //add the current reading to the arraylist
				times.add(date); //add the current date to the arraylist 

			}

		}
		return new Object[] { readings, times }; //return an object containin the two arraylists

	}

	public Cursor queueSpecific(String columns[], String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return super.queueSpecific(columns, selection, selectionArgs, groupBy,
				having, orderBy);
	}

	public long insertBloodGlucoseLog(BloodGlucoseLog bloodGlucoseLog) {
		ContentValues entityMap = new ContentValues();

		// entityMap.put("_id", bloodGlucoseLog.getId());
		entityMap.put("blood_glucose_measurement_unit",
				bloodGlucoseLog.getBloodGlucoseMeasurementUnitString());
		entityMap.put("blood_glucose_type",
				bloodGlucoseLog.getBloodGlucoseTypeString());
		entityMap.put("log_time", bloodGlucoseLog.getLogTimeFormatted());
		entityMap.put("reading", bloodGlucoseLog.getReading().toString());

		return super.insert(TABLE_NAME, entityMap);

	}

	public void deleteAll() {
		super.deleteAll();
	}
}
