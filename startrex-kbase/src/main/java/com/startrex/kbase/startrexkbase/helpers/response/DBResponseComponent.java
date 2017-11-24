package com.startrex.kbase.startrexkbase.helpers.response;

import java.util.Map;

public class DBResponseComponent {
	public Map<String, Object> responseMap;
	public String responseStatus;
	public int responseCode;
	public  String responseMessage;	
	
	public DBResponseComponent() {
		this.responseMap = null;
		this.responseCode = STResponseCode.OK;
		this.responseStatus = STResponseStatus.OK;
		this.responseMessage = STResponseMessage.SUCCESS;
	}

	public DBResponseComponent(Map<String, Object> responseMap, String responseStatus, int responseCode, String responseMessage) {
		this.responseMap = responseMap;
		this.responseStatus = responseStatus;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	
	public static DBResponseComponent factory() {
		return new DBResponseComponent();
	}

	public static DBResponseComponent factory(Map<String, Object> responseMap) {
		DBResponseComponent rc = new DBResponseComponent();
		rc.responseMap = responseMap;
		return rc;
	}
	
	public static DBResponseComponent factory(Map<String, Object> responseMap, String responseStatus) {
		DBResponseComponent rc = new DBResponseComponent();
		rc.responseMap = responseMap;
		rc.responseStatus = responseStatus;
		return rc;
	}
	
	public static DBResponseComponent factory(Map<String, Object> responseMap, String responseStatus, int responseCode) {
		DBResponseComponent rc = new DBResponseComponent();
		rc.responseMap = responseMap;
		rc.responseStatus = responseStatus;
		rc.responseCode = responseCode;
		return rc;
	}
	
	public static DBResponseComponent factory(String responseStatus, int responseCode, String responseMessage) {
		DBResponseComponent rc = new DBResponseComponent();
		rc.responseStatus = responseStatus;
		rc.responseCode = responseCode;
		rc.responseMessage = responseMessage;
		return rc;
	}
	
	public static DBResponseComponent factory(Map<String, Object> responseMap, String responseStatus, int responseCode, String responseMessage) {
		DBResponseComponent rc = new DBResponseComponent();
		rc.responseMap = responseMap;
		rc.responseStatus = responseStatus;
		rc.responseCode = responseCode;
		rc.responseMessage = responseMessage;
		return rc;
	}
	
	
	
	
}
