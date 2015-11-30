package com.BgLogger.model.alarm;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class Alarm implements Serializable{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");
	
	private String testType;
	private Date alarmTime;
	private boolean enabled;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	public Date getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
	
	@SuppressLint("ParserError")
	public String getAlarmTimeFormatted() {
		return sdf.format(alarmTime);
	}

}
