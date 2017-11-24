package com.startrex.kbase.startrexkbase.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.startrex.kbase.startrexkbase.dao.DomainDAO;
import com.startrex.kbase.startrexkbase.entities.Course;
import com.startrex.kbase.startrexkbase.entities.Domain;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;

@RestController
@RequestMapping("domain")
public class DomainRestController {
	
	@Autowired
	private DomainDAO domainDAO;
	
	Pageable pageable;
	
	@RequestMapping(method=RequestMethod.GET, value="get/{domainId}")
	public STResponseComponent get(@PathVariable(value="domainId") String domainId) {
		//map containing various objects to be passed as part of the STReponseComponent to the calling client
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		
		try {
	
			DBResponseComponent dbResponse = domainDAO.get(Integer.parseInt(domainId));
			if(dbResponse.responseCode == responseCode) {
				map.put("domain", dbResponse.responseMap.get("domain"));
			}else if(dbResponse.responseCode == STResponseCode.NO_OBJECT_FOUND) {
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;
			}
			
		}catch(NumberFormatException ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			ex.printStackTrace();
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			ex.printStackTrace();
		}
		
		return STResponseComponent.factory(map, responseStatus, responseCode, responseMessage);	

	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="all")
	public STResponseComponent getAll(Pageable pageable, HttpServletRequest request) {		
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {			
			DBResponseComponent dbResponse = domainDAO.getAll(pageable);
			if(dbResponse.responseCode == responseCode) {				
				@SuppressWarnings("unchecked")
				Page<Domain> page = (Page<Domain>) dbResponse.responseMap.get("domain");				
				map.put("domains", page);
			}else {
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
			}
			
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}		
		return STResponseComponent.factory(map, responseStatus, responseCode, responseMessage);
	}	
	
	@RequestMapping(method=RequestMethod.GET, value="allforuser")
	public STResponseComponent getUserDomains(HttpServletRequest request) {		
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {			
			DBResponseComponent dbResponse = domainDAO.findByUserId(pageable, 
											 Long.parseLong(request.getAttribute("userId").toString()));
			if(dbResponse.responseCode == responseCode) {							
				map.put("domains", dbResponse.responseMap.get("domain"));
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
	
	
	
	@RequestMapping(method=RequestMethod.GET, value="{domainId}/courses")
	@PreAuthorize("hasRole('LIST_COURSES')")
	public STResponseComponent getCourses(@PathVariable(value="domainId") String domainId,
										  Pageable pageable,
										  HttpServletRequest request) {
		System.out.println(request.getAttribute("userId") + " ---- value from request object");
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {			
			int dId = Integer.parseInt(domainId);
			DBResponseComponent dbResponse = domainDAO.getCourses(dId, pageable);
			if(dbResponse.responseCode == responseCode) {				
				@SuppressWarnings("unchecked")
				Page<Course> page = (Page<Course>) dbResponse.responseMap.get("courses");				
				map.put("courses", page);
			}else {
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
			}
			
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}		
		return STResponseComponent.factory(map, responseStatus, responseCode, responseMessage);
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST, value="add")
	public STResponseComponent add(@RequestBody Domain domain) {
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		
		try {
			DBResponseComponent dbResponse = domainDAO.save(domain);
			if(dbResponse.responseCode == responseCode) {
				map.put("domain", dbResponse.responseMap.get("domain"));
			}else {
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
				responseMessage = STResponseMessage.FAILED;
			}
			
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}
		
		return STResponseComponent.factory(map, responseStatus, responseCode, responseMessage);			
	}
	
	@RequestMapping(method=RequestMethod.PATCH, value="update")
	public STResponseComponent update(@RequestBody Domain domain) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		
		try {
			DBResponseComponent dbResponse = domainDAO.save(domain);
			if(dbResponse.responseCode == responseCode) {
				map.put("domain", dbResponse.responseMap.get("domain"));
			}else {
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}
		return STResponseComponent.factory(map, responseStatus, responseCode, responseMessage);

	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public STResponseComponent remove(@PathVariable(value="id") String id) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		
		try {
			int domainId = Integer.parseInt(id);
			DBResponseComponent dbResponse = domainDAO.remove(domainId);
			if(dbResponse.responseCode == responseCode){
				responseMap.put("revertToken", id);
			}else {
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
				responseMessage = STResponseMessage.FAILED;
			}
		}catch(NumberFormatException ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}		
		return STResponseComponent.factory(responseStatus, responseCode, responseMessage);

	}
	
}
