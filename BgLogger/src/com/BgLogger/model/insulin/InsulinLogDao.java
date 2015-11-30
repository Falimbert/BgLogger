package com.BgLogger.model.insulin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.BgLogger.model.GenericDao;

public class InsulinLogDao extends GenericDao {
	private static final String TABLE_NAME = "insulin_log";
	private static final String CREATE_SCRIPT = "CREATE TABLE IF NOT EXISTS " 
			+ TABLE_NAME + " ("
			+ "_id integer primary key autoincrement" 
			+ ",insulin_type_id integer not null"
			+ ",log_time datetime not null"
			+ ",dosage inger not null);";
	public static final String LOG_TIME_FIELD_NAME = "log_time";
	public static final String DOSAGE_FIELD_NAME = "dosage";
	
	public InsulinLogDao(Context context) {
		super(context, TABLE_NAME, CREATE_SCRIPT);
	}

	public InsulinLogDao openToRead() throws SQLException {
		return (InsulinLogDao)super.openToRead();
	}

	public InsulinLogDao openToWrite() throws SQLException {
		return (InsulinLogDao)super.openToWrite();
	}

	public long insert(InsulinLog insulinLog) {
		ContentValues entityMap = new ContentValues();

		entityMap.put("_id", insulinLog.getId());
		entityMap.put("insulin_type_id", insulinLog.getInsulinTypeId());
		entityMap.put("dosage", insulinLog.getDosage());
		entityMap.put("log_time", insulinLog.getLogTimeFormatted());
		
		return super.insert(TABLE_NAME, entityMap);
	}
	
	public Cursor queueAll(){
		return super.queueAll("log_time desc");
	}
}
