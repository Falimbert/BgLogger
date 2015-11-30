package com.BgLogger.model.insulin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsulinLog {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	
	private Long id;
	private Long insulinTypeId;
	private InsulinType insulinType;
	private int dosage;
	private Date logTime;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the insulinTypeId
	 */
	public Long getInsulinTypeId() {
		return insulinTypeId;
	}
	/**
	 * @param insulinTypeId the insulinTypeId to set
	 */
	public void setInsulinTypeId(Long insulinTypeId) {
		this.insulinTypeId = insulinTypeId;
	}
	/**
	 * @return the insulinType
	 */
	public InsulinType getInsulinType() {
		return insulinType;
	}
	/**
	 * @param insulinType the insulinType to set
	 */
	public void setInsulinType(InsulinType insulinType) {
		this.insulinType = insulinType;
	}
	/**
	 * @return the dosage
	 */
	public int getDosage() {
		return dosage;
	}
	/**
	 * @param dosage the dosage to set
	 */
	public void setDosage(int dosage) {
		this.dosage = dosage;
	}
	/**
	 * @return the logTime
	 */
	public Date getLogTime() {
		return logTime;
	}
	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	
	public String getLogTimeFormatted(){
		if(logTime != null)
		return sdf.format(logTime);
		else return "";
	}
}
