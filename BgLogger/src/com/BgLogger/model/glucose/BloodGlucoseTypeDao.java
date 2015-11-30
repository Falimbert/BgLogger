package com.BgLogger.model.glucose;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;

import com.BgLogger.model.GenericDao;
import com.BgLogger.model.insulin.InsulinType;

/**
 * @author		Scott Leonard
 * Created:		07/01/2012
 * @editor 	 	Limas Baginta,
 * Modified: 	29/11/2015
 */
public class BloodGlucoseTypeDao extends GenericDao{
	private static final String TABLE_NAME = "blood_glucose_type";
	private static final String CREATE_SCRIPT = "CREATE TABLE IF NOT EXISTS " 
			+ TABLE_NAME + " ("
			+ "_id integer primary key autoincrement" 
			+ ",name varchar primary key not null"
			+ ",dsc varchar not null"
			+ ");";

	public BloodGlucoseTypeDao(Context context) {
		super(context,TABLE_NAME,CREATE_SCRIPT);
		addInitialRecords();
	}

	public BloodGlucoseTypeDao openToRead() throws SQLException {
		return (BloodGlucoseTypeDao)super.openToRead();
	}

	public BloodGlucoseTypeDao openToWrite() throws SQLException {
		return (BloodGlucoseTypeDao)super.openToWrite();
	}

	public long insert(BloodGlucoseType bloodGlucoseType) {
		ContentValues entityMap = new ContentValues();

		entityMap.put("_id", bloodGlucoseType.getId());
		entityMap.put("name", bloodGlucoseType.getName());
		entityMap.put("dsc", bloodGlucoseType.getDsc());
		
		return super.insert(TABLE_NAME, entityMap);
	}
	
	private void addInitialRecords(){
		openToWrite();
		deleteAll();
		
		BloodGlucoseType bloodGlucoseType = new BloodGlucoseType();
		bloodGlucoseType.setDsc("Fasting for eight hours");
		bloodGlucoseType.setName("Fasting Blood Sugar");
		
		insert(bloodGlucoseType);
		
		
		bloodGlucoseType = new BloodGlucoseType();
		bloodGlucoseType.setDsc("Two hours after the meal");
		bloodGlucoseType.setName("Random Blood Sugar");
		
		insert(bloodGlucoseType);
		
		bloodGlucoseType = new BloodGlucoseType();
		bloodGlucoseType.setDsc("To show overall diabetes control");
		bloodGlucoseType.setName("HBA1C");
		
		insert(bloodGlucoseType);
		close();
	}
}