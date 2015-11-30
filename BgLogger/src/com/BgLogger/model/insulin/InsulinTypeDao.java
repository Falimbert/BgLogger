package com.BgLogger.model.insulin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.BgLogger.model.GenericDao;

public class InsulinTypeDao extends GenericDao {
	public static final String ID_FIELD_NAME = "_id";
	public static final String NAME_FIELD_NAME = "name";
	
	private static final String TABLE_NAME = "insulin_type";
	private static final String CREATE_SCRIPT = "CREATE TABLE IF NOT EXISTS " 
			+ TABLE_NAME + " ("
			+ "_id integer primary key autoincrement" 
			+ ",name varchar not null"
			+ ",code varchar not null"
			+ ",onset_start_minutes integer not null"
			+ ",onset_end_minutes integer not null"
			+ ",peak_start_minutes integer not null"
			+ ",peak_end_minutes integer not null"
			+ ",duration_start_minutes integer not null"
			+ ",duration_end_minutes integer not null"
			+ ",has_peak boolean not null"
			+ ",units_per_ml integer not null"
			+ ",dsc varchar not null);";
	
	public InsulinTypeDao(Context context) {
		super(context, TABLE_NAME, CREATE_SCRIPT);
		addInitialRecords();
	}
	
	public InsulinTypeDao openToRead() throws SQLException {
		return (InsulinTypeDao)super.openToRead();
	}

	public InsulinTypeDao openToWrite() throws SQLException {
		return (InsulinTypeDao)super.openToWrite();
	}

	public long insert(InsulinType insulinType) {
		ContentValues entityMap = new ContentValues();

		entityMap.put("_id", insulinType.getId());
		entityMap.put("name", insulinType.getName());
		entityMap.put("dsc", insulinType.getDsc());
		entityMap.put("code", insulinType.getCode());
		entityMap.put("has_peak", insulinType.getHasPeak());
		entityMap.put("units_per_ml", insulinType.getUnitsPerMl());
		entityMap.put("onset_start_minutes",insulinType.getOnsetStartMinutes());
		entityMap.put("onset_end_minutes",insulinType.getOnsetEndMinutes());
		entityMap.put("peak_start_minutes",insulinType.getPeakStartMinutes());
		entityMap.put("peak_end_minutes",insulinType.getPeakEndMinutes());
		entityMap.put("duration_start_minutes",insulinType.getDurationStartMinutes());
		entityMap.put("duration_end_minutes",insulinType.getDurationEndMinutes());
		
		return super.insert(TABLE_NAME, entityMap);
	}
	
	public Cursor queueAll(){
		return super.queueAll("name asc");
	}
	
	private void addInitialRecords(){
		openToWrite();
		deleteAll();
		
		InsulinType insulinType = new InsulinType();
		insulinType.setDsc("Short Acting Synthetic Insulin");
		insulinType.setName("Humalog");
		insulinType.setCode("H");
		insulinType.setHasPeak(true);
		insulinType.setUnitsPerMl(100);
		insulinType.setOnsetStartMinutes(15);
		insulinType.setOnsetEndMinutes(30);
		insulinType.setPeakStartMinutes(30);
		insulinType.setPeakEndMinutes(90);
		insulinType.setDurationStartMinutes(180);
		insulinType.setDurationEndMinutes(300);
		
		insert(insulinType);
		
		insulinType = new InsulinType();
		insulinType.setDsc("Long Acting Synthetic Insulin");
		insulinType.setName("Lantus");
		insulinType.setCode("Lantus");
		insulinType.setHasPeak(false);
		insulinType.setUnitsPerMl(100);
		insulinType.setOnsetStartMinutes(60);
		insulinType.setOnsetEndMinutes(90);
		insulinType.setPeakStartMinutes(0);
		insulinType.setPeakEndMinutes(0);
		insulinType.setDurationStartMinutes(1200);
		insulinType.setDurationEndMinutes(1440);
		
		insert(insulinType);
		
		insulinType = new InsulinType();
		insulinType.setDsc("Long Acting Synthetic Insulin");
		insulinType.setName("Levemir");
		insulinType.setCode("Levemir");
		insulinType.setHasPeak(true);
		insulinType.setUnitsPerMl(100);
		insulinType.setOnsetStartMinutes(60);
		insulinType.setOnsetEndMinutes(120);
		insulinType.setPeakStartMinutes(360);
		insulinType.setPeakEndMinutes(480);
		insulinType.setDurationStartMinutes(1440);
		insulinType.setDurationEndMinutes(1440);
		
		insert(insulinType);
		close();
	}

}
