package com.BgLogger.model.glucose;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;

import com.BgLogger.model.GenericDao;

/**
 * @author		Scott Leonard
 * Created:		07/01/2012
 * @editor 	 	Limas Baginta,
 * Modified: 	29/11/2015
 */
 
public class BloodGlucoseMeasurementUnitDao extends GenericDao {
	private static final String TABLE_NAME = "blood_glucose_measurement_unit";
	private static final String CREATE_SCRIPT = "CREATE TABLE IF NOT EXISTS " 
			+ TABLE_NAME + " ("
			+ "_id integer primary key autoincrement" 
			+ ",name varchar primary key not null"
			+ ",dsc varchar not null"
			+ ");";
	
	public BloodGlucoseMeasurementUnitDao(Context context) {
		super(context, TABLE_NAME, CREATE_SCRIPT);
		addInitialRecords();
	}

	public BloodGlucoseMeasurementUnitDao openToRead() throws SQLException {
		return (BloodGlucoseMeasurementUnitDao)super.openToRead();
	}

	public BloodGlucoseMeasurementUnitDao openToWrite() throws SQLException {
		return (BloodGlucoseMeasurementUnitDao)super.openToWrite();
	}

	public long insert(BloodGlucoseMeasurementUnit bloodGlucoseMeasurementUnit) {
		ContentValues entityMap = new ContentValues();

		entityMap.put("_id", bloodGlucoseMeasurementUnit.getId());
		entityMap.put("name", bloodGlucoseMeasurementUnit.getName());
		entityMap.put("dsc", bloodGlucoseMeasurementUnit.getDsc());
		
		return super.insert(TABLE_NAME, entityMap);
	}
	
	private void addInitialRecords(){
		openToWrite();
		deleteAll();
		
		BloodGlucoseMeasurementUnit bgmu = new BloodGlucoseMeasurementUnit();
		bgmu.setDsc("American Units");
		bgmu.setName("mg/dl");
		
		insert(bgmu);
		
		bgmu = new BloodGlucoseMeasurementUnit();
		bgmu.setDsc("European Units");
		bgmu.setName("mmol/l");
		
		insert(bgmu);
		
		bgmu = new BloodGlucoseMeasurementUnit();
		bgmu.setDsc("HBA1C Units");
		bgmu.setName("%");
		
		insert(bgmu);
		
		close();
	}
}
