package com.BgLogger.model.alarm;

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

public class AlarmDao extends GenericDao {

	public static final String ALARM_TIME_FIELD_NAME = "time";
	public static final String TEST_TYPE_FIELD_NAME = "blood_glucose_type";
	public static final String ALARM_ENABLED_FIELD_NAME = "enabled";
	public static final String ID_FIELD_NAME = "_id";

	private static final String TABLE_NAME = "reminders";
	private static final String CREATE_SCRIPT = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " (" + "_id integer primary key autoincrement"
			+ ",time datetime not null" + ",enabled BOOLEAN not null"
			+ ",blood_glucose_type text not null);";

	public AlarmDao(Context context) {
		super(context, TABLE_NAME, CREATE_SCRIPT);
	}

	public AlarmDao openToRead() throws android.database.SQLException {
		return (AlarmDao) super.openToRead();
	}

	public AlarmDao openToWrite() throws android.database.SQLException {
		return (AlarmDao) super.openToWrite();
	}

	public Cursor queueAll() {
		return super.queueAll(null);
	}

	public Cursor queueSpecific(String columns[], String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return super.queueSpecific(columns, selection, selectionArgs, groupBy,
				having, orderBy);
	}

	public long insertAlarm(Alarm alarm) {
		ContentValues entityMap = new ContentValues();

		entityMap.put("blood_glucose_type", alarm.getTestType());
		entityMap.put("time", alarm.getAlarmTimeFormatted());
		entityMap.put("enabled", alarm.isEnabled());

		return super.insert(TABLE_NAME, entityMap);
	}

	public void update(Alarm alarm, int id, String[] whereArgs) {
		ContentValues entityMap = new ContentValues();

		entityMap.put("blood_glucose_type", alarm.getTestType());
		entityMap.put("time", alarm.getAlarmTimeFormatted());
		entityMap.put("enabled", alarm.isEnabled());

		String whereClause = ID_FIELD_NAME + "=" + id;

		super.update(TABLE_NAME, entityMap, whereClause, whereArgs);

	}

	public void deleteAll() {
		super.deleteAll();
	}

}
