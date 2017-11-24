package com.startrex.kbase.startrexkbase.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.startrex.kbase.startrexkbase.dao.DomainDAO;
import com.startrex.kbase.startrexkbase.dao.UserDAO;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;

@RestController
@RequestMapping("user")
public class UserRestController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private DomainDAO domainDAO;
	
	Pageable pageable;
	
	@RequestMapping(method=RequestMethod.GET, value="homepage")
	@PreAuthorize("hasRole('LIST_COURSES')")
	public STResponseComponent getHomePage(HttpServletRequest request, Pageable pageable) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		
		try {			
			DBResponseComponent dbResponse = userDAO.getHomepageData(null, 
											 Long.parseLong(request.getAttribute("userId").toString()));
			if(dbResponse.responseCode == responseCode) {	
				map.put("homepage", dbResponse.responseMap.get("data"));
			}else {
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
			}
			
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			ex.printStackTrace();
		}		
		return STResponseComponent.factory(map, responseStatus, responseCode, responseMessage);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="domains")
	public STResponseComponent getUserDomains(HttpServletRequest request) {		
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {			
			System.out.println("starting now");
			DBResponseComponent dbResponse = domainDAO.findByUserId(pageable, 
											 Long.parseLong(request.getAttribute("userId").toString()));
			if(dbResponse.responseCode == responseCode) {							
				map.put("domains", dbResponse.responseMap.get("domains"));
			}else {
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
			}
			
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			ex.printStackTrace();
		}		
		return STResponseComponent.factory(map, responseStatus, responseCode, responseMessage);
	}	
}
