package com.BgLogger.model.glucose;
/**
 * @author		Scott Leonard
 * Created:		07/01/2012
 * @editor 	 	Limas Baginta,
 * Modified: 	29/11/2015
 */
 
public class BloodGlucoseType {
	private Long id;
	private String name;
	private String dsc;
	
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
}
