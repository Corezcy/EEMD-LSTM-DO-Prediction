/**
 * 
 */
package com.DO.Prediction.client.utils;

import android.provider.BaseColumns;

/**
 * @author TANGYONG
 * @version 1.0 2010-05-21
 */
public class ForecastEntity {
	// field name
	public static final String DAYOFWEEK = "dayOfWeek";
	public static final String LOW = "low";
	public static final String HIGHT = "hight";
	public static final String ICON = "icon";
	public static final String CONDITION = "condition";
	public static final String WIDGET_ID = "widgetId";

	// projection
	public static final String[] forecastProjection = new String[]{
		BaseColumns._ID,
		DAYOFWEEK, 
		LOW,
		HIGHT,
		ICON,
		CONDITION,
		WIDGET_ID		
	};
	
	// field
	private Integer id;
	private String dayOfWeek;
	private Integer low;
	private Integer hight;
	private String icon;
	private String condition;
	private Integer widgetId;
	

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
	 * @return the dayOfWeek
	 */
	public String getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek
	 *            the dayOfWeek to set
	 */
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * @return the low
	 */
	public Integer getLow() {
		return low;
	}

	/**
	 * @param low
	 *            the low to set
	 */
	public void setLow(Integer low) {
		this.low = low;
	}

	/**
	 * @return the hight
	 */
	public Integer getHight() {
		return hight;
	}

	/**
	 * @param hight
	 *            the hight to set
	 */
	public void setHight(Integer hight) {
		this.hight = hight;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition
	 *            the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @param widgetId the widgetId to set
	 */
	public void setWidgetId(Integer widgetId) {
		this.widgetId = widgetId;
	}

	/**
	 * @return the widgetId
	 */
	public Integer getWidgetId() {
		return widgetId;
	}

}
