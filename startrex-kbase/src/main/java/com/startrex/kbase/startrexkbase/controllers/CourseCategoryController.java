package com.startrex.kbase.startrexkbase.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.startrex.kbase.startrexkbase.dao.CourseCategoryDAO;
import com.startrex.kbase.startrexkbase.entities.CourseCategory;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;

/**
 * CourseCategoryController
 * Provides API methods for performing CRUD operations on a course category
 * 
 * All the methods return STReponseComponent
 */
@RestController
@RequestMapping("category")
public class CourseCategoryController {
    @Autowired
    CourseCategoryDAO categoryDao;
	
    /**
     * 
     * @param id
     * @return 
     */
	@RequestMapping(method=RequestMethod.GET, value="{id}")
	public STResponseComponent get(@PathVariable(value="id") String id) {
		//map containing various objects to be passed as part of the STReponseComponent to the calling client
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		
		try {
			int catId = Integer.parseInt(id);
			DBResponseComponent dbResponse = categoryDao.get(catId);
			if(dbResponse.responseCode == responseCode) {
				map.put("category", dbResponse.responseMap.get("category"));
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
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="all")
	public STResponseComponent getAll(Pageable pageable) {		
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {			
			DBResponseComponent dbResponse = categoryDao.getAll(pageable);
			if(dbResponse.responseCode == responseCode) {				
				@SuppressWarnings("unchecked")
				Page<CourseCategory> page = (Page<CourseCategory>) dbResponse.responseMap.get("categories");				
				map.put("categories", page);
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
	
	/**
	 * 
	 * @param courseCategory
	 * @return STResponseComponent
	 */
	@RequestMapping(method=RequestMethod.POST, value="add")
	public STResponseComponent add(@RequestBody CourseCategory courseCategory) {
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		
		try {
			courseCategory.setDeleted(PolarResponseType.NO);
			DBResponseComponent dbResponse = categoryDao.save(courseCategory);
			if(dbResponse.responseCode == responseCode) {
				map.put("category", dbResponse.responseMap.get("category"));
			}else {
				responseCode = STResponseCode.ERROR;
				responseStatus = STResponseStatus.ERROR;
				responseMessage = STResponseMessage.FAILED;
			}
			
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			ex.printStackTrace();
		}
		
		return STResponseComponent.factory(map, responseStatus, responseCode, responseMessage);		
	}	
	
	/**
	 * 
	 * @param courseCategory
	 * @return
	 */
	@RequestMapping(method=RequestMethod.PATCH, value="update")
	public STResponseComponent update(@RequestBody CourseCategory courseCategory) {
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		
		try {
			courseCategory.setDeleted(PolarResponseType.NO);
			DBResponseComponent dbResponse = categoryDao.save(courseCategory);
			if(dbResponse.responseCode == responseCode) {
				map.put("category", dbResponse.responseMap.get("category"));
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
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="delete/{id}")
	public STResponseComponent remove(@PathVariable(value="id") String id) {
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		
		try {
			int catId = Integer.parseInt(id);
			DBResponseComponent dbResponse = categoryDao.remove(catId);
			System.out.println(dbResponse.responseCode + "===============");
			if(dbResponse.responseCode != responseCode) {
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
