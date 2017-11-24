package com.startrex.kbase.startrexkbase.helpers.response;

import java.util.Map;

public class STResponseComponent {
	
	public Map<String, Object> context;
	public String responseStatus;
	public int responseCode;
	public String responseMessage;
	
	public STResponseComponent() {
		this.context = null;
		this.responseCode = STResponseCode.OK;
		this.responseStatus = STResponseStatus.OK;
		this.responseMessage = "SUCCESS";
	}

	public STResponseComponent(Map<String, Object> map, String responseStatus, int responseCode, String responseMessage) {
		this.context = map;
		this.responseStatus = responseStatus;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	
	public STResponseComponent(String responseStatus, int responseCode, String responseMessage) {
		super();
		this.context = null;
		this.responseStatus = responseStatus;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	
	
	public static STResponseComponent factory() {
		return new STResponseComponent();
	}
	
	public static STResponseComponent factory(Map<String, Object> map) {
		STResponseComponent rc = new STResponseComponent();
		rc.context = map;
		return rc;
	}
	
	public static STResponseComponent factory(String message) {
		STResponseComponent rc = new STResponseComponent();
		rc.responseMessage = message;
		return rc;
	}
	
	public static STResponseComponent factory(Map<String, Object> map, String message) {
		STResponseComponent rc = new STResponseComponent();
		rc.context = map;
		rc.responseMessage = message;
		return rc;
	}
	
	public static STResponseComponent factory(Map<String, Object> map, String responseStatus, int responseCode, String responseMessag) {
		return new STResponseComponent(map, responseStatus, responseCode, responseMessag);
	}
	
	public static STResponseComponent factory(String responseStatus, int responseCode, String responseMessag) {
		return new STResponseComponent(responseStatus, responseCode, responseMessag);
	}

	
}
