package com.BgLogger.model.insulin;

public class InsulinType {
	private Long id;
	private String name;
	private String code;
	private String dsc;
	private int onsetStartMinutes;
	private int onsetEndMinutes;
	private int peakStartMinutes;
	private int peakEndMinutes;
	private int durationStartMinutes;
	private int durationEndMinutes;
	private boolean hasPeak;
	private int unitsPerMl;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the dsc
	 */
	public String getDsc() {
		return dsc;
	}
	/**
	 * @param dsc the dsc to set
	 */
	public void setDsc(String dsc) {
		this.dsc = dsc;
	}
	/**
	 * @return the onsetStartMinutes
	 */
	public int getOnsetStartMinutes() {
		return onsetStartMinutes;
	}
	/**
	 * @param onsetStartMinutes the onsetStartMinutes to set
	 */
	public void setOnsetStartMinutes(int onsetStartMinutes) {
		this.onsetStartMinutes = onsetStartMinutes;
	}
	/**
	 * @return the onsetEndMinutes
	 */
	public int getOnsetEndMinutes() {
		return onsetEndMinutes;
	}
	/**
	 * @param onsetEndMinutes the onsetEndMinutes to set
	 */
	public void setOnsetEndMinutes(int onsetEndMinutes) {
		this.onsetEndMinutes = onsetEndMinutes;
	}
	/**
	 * @return the peakStartMinutes
	 */
	public int getPeakStartMinutes() {
		return peakStartMinutes;
	}
	/**
	 * @param peakStartMinutes the peakStartMinutes to set
	 */
	public void setPeakStartMinutes(int peakStartMinutes) {
		this.peakStartMinutes = peakStartMinutes;
	}
	/**
	 * @return the peakEndMinutes
	 */
	public int getPeakEndMinutes() {
		return peakEndMinutes;
	}
	/**
	 * @param peakEndMinutes the peakEndMinutes to set
	 */
	public void setPeakEndMinutes(int peakEndMinutes) {
		this.peakEndMinutes = peakEndMinutes;
	}
	/**
	 * @return the durationStartMinutes
	 */
	public int getDurationStartMinutes() {
		return durationStartMinutes;
	}
	/**
	 * @param durationStartMinutes the durationStartMinutes to set
	 */
	public void setDurationStartMinutes(int durationStartMinutes) {
		this.durationStartMinutes = durationStartMinutes;
	}
	/**
	 * @return the durationEndMinutes
	 */
	public int getDurationEndMinutes() {
		return durationEndMinutes;
	}
	/**
	 * @param durationEndMinutes the durationEndMinutes to set
	 */
	public void setDurationEndMinutes(int durationEndMinutes) {
		this.durationEndMinutes = durationEndMinutes;
	}
	/**
	 * @return the hasPeak
	 */
	public boolean getHasPeak() {
		return hasPeak;
	}
	/**
	 * @param hasPeak the hasPeak to set
	 */
	public void setHasPeak(boolean hasPeak) {
		this.hasPeak = hasPeak;
	}
	/**
	 * @return the unitsPerMl
	 */
	public int getUnitsPerMl() {
		return unitsPerMl;
	}
	/**
	 * @param unitsPerMl the unitsPerMl to set
	 */
	public void setUnitsPerMl(int unitsPerMl) {
		this.unitsPerMl = unitsPerMl;
	}
}
