/**
 * 
 */
package com.DO.Prediction.client.utils;

import java.util.ArrayList;

/**
 * @author TANGYONG
 * @version 1.0 2010-05-21
 */
public class WidgetEntity {	
	// field name
	public static final String UPDATE_MILIS = "updateMilis";
	public static final String CITY = "city";
	public static final String POSTALCODE = "postalCode";
	public static final String FORECASTDATE = "forecastDate";
	public static final String CONDITION = "condition";
	public static final String TEMPF = "tempF";
	public static final String TEMPC = "tempC";
	public static final String HUMIDITY = "humidity";
	public static final String ICON = "icon";
	public static final String WINDCONDITION = "windCondition";
	public static final String LAST_UPDATE_TIME = "lastUpdateTime";
	public static final String IS_CONFIGURED = "isConfigured";
	
	// projection
	public static final String[] widgetProjection = new String[]{
		UPDATE_MILIS,
		CITY,
		POSTALCODE,
		FORECASTDATE,
		CONDITION,
		TEMPF,
		TEMPC,
		HUMIDITY,
		ICON,
		WINDCONDITION,
		LAST_UPDATE_TIME,
		IS_CONFIGURED
		
	};
	
	// field
	private ArrayList<ForecastEntity> details = new ArrayList<ForecastEntity>();
	private Integer id;
	private Integer updateMilis;
	private String city;
	private String postalCode;
	private Long forecastDate;
	private String condition;
	private Integer tempF;
	private Integer tempC;
	private String humidity;
	private String icon;
	private String windCondition;
	private Long lastUpdateTime;
	private Integer isConfigured;
	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the updateMilis
	 */
	public Integer getUpdateMilis() {
		return updateMilis;
	}

	/**
	 * @param updateMilis the updateMilis to set
	 */
	public void setUpdateMilis(Integer updateMilis) {
		this.updateMilis = updateMilis;
	}

	public ArrayList<ForecastEntity> getDetails() {
		return details;
	}

	public void setDetails(ArrayList<ForecastEntity> details) {
		this.details = details;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getTempF() {
		return tempF;
	}

	public void setTempF(Integer tempF) {
		this.tempF = tempF;
	}

	public Integer getTempC() {
		return tempC;
	}

	public void setTempC(Integer tempC) {
		this.tempC = tempC;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getWindCondition() {
		return windCondition;
	}

	public void setWindCondition(String windCondition) {
		this.windCondition = windCondition;
	}

	/**
	 * @param isConfigured the isConfigured to set
	 */
	public void setIsConfigured(Integer isConfigured) {
		this.isConfigured = isConfigured;
	}

	/**
	 * @return the isConfigured
	 */
	public Integer getIsConfigured() {
		return isConfigured;
	}

	/**
	 * @param forecastDate the forecastDate to set
	 */
	public void setForecastDate(Long forecastDate) {
		this.forecastDate = forecastDate;
	}

	/**
	 * @return the forecastDate
	 */
	public Long getForecastDate() {
		return forecastDate;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public Long getLastUpdateTime() {
		return lastUpdateTime;
	}

}
