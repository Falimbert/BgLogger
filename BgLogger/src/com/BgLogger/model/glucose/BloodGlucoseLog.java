package com.BgLogger.model.glucose;

import android.annotation.SuppressLint;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BloodGlucoseLog {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");
	
	private String bloodGlucoseMeasurementUnit;
	private BloodGlucoseMeasurementUnit BloodGlucoseMeasurementUnit;
	private String bloodGlucoseType;
	private BloodGlucoseType BloodGlucoseType;
	private BigDecimal reading;
	private Date logTime;
	
	/**
	 * @return the bloodGlucoseMeasurementUnitId
	 */
	public String getBloodGlucoseMeasurementUnitString() {
		return bloodGlucoseMeasurementUnit;
	}
	/**
	 * @param bloodGlucoseMeasurementUnitId the bloodGlucoseMeasurementUnitId to set
	 */
	public void setBloodGlucoseMeasurementUnitString(String bloodGlucoseMeasurementUnit) {
		this.bloodGlucoseMeasurementUnit = bloodGlucoseMeasurementUnit;
	}
	/**
	 * @return the bloodGlucoseMeasurementUnit
	 */
	public BloodGlucoseMeasurementUnit getBloodGlucoseMeasurementUnit() {
		return BloodGlucoseMeasurementUnit;
	}
	/**
	 * @return the bloodGlucoseTypeId
	 */
	public String getBloodGlucoseTypeString() {
		return bloodGlucoseType;
	}
	/**
	 * @param bloodGlucoseTypeId the bloodGlucoseTypeId to set
	 */
	public void setBloodGlucoseTypeString(String bloodGlucoseType) {
		this.bloodGlucoseType = bloodGlucoseType;
	}
	/**
	 * @return the bloodGlucoseType
	 */
	public BloodGlucoseType getBloodGlucoseType() {
		return BloodGlucoseType;
	}
	/**
	 * @return the reading
	 */
	public BigDecimal getReading() {
		return reading;
	}
	/**
	 * @param reading the reading to set
	 */
	public void setReading(BigDecimal reading) {
		this.reading = reading;
	}
	/**
	 * @return the log_time
	 */
	public Date getLogTime() {
		return logTime;
	}
	/**
	 * @param log_time the log_time to set
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	@SuppressLint("ParserError")
	public String getLogTimeFormatted() {
		return sdf.format(logTime);
	}
}
