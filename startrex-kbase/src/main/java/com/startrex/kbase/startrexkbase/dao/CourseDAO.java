package com.startrex.kbase.startrexkbase.dao;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.startrex.kbase.startrexkbase.entities.Course;
import com.startrex.kbase.startrexkbase.entities.CourseCategory;
import com.startrex.kbase.startrexkbase.entities.Domain;
import com.startrex.kbase.startrexkbase.entities.Topic;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;
import com.startrex.kbase.startrexkbase.repositories.CourseCategoryRepository;
import com.startrex.kbase.startrexkbase.repositories.CourseRepository;
import com.startrex.kbase.startrexkbase.repositories.DomainRepository;

@Service
public class CourseDAO {

	@Autowired
	private CourseRepository courseRep;
	@Autowired 
	private CourseCategoryRepository courseCatRep;
	@Autowired
	private DomainRepository domainRep;
	
	
	public DBResponseComponent get(int id) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<Course> optional = courseRep.findOneById(id);
			if(optional.isPresent()) {
				responseMap.put("course", optional.get());
			}else {
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);
	}
	
public DBResponseComponent getCourseList(Pageable pageable) {

		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			
			Page<Course> courses = courseRep.getCourseList(pageable);
			if(courses.getTotalElements() == 0) {
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;;
			}else {				
				responseMap.put("courses", courses);
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);

	}
	
	public DBResponseComponent findAll(Pageable pageable) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Page<Course> courses = courseRep.findAll(pageable);
			if(courses.getNumberOfElements() > 0) {
				
				responseMap.put("courses", courses.getContent());
			}else {
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);
	}
	
	
	
	public DBResponseComponent getAllByDomain(int domainId) {
		
		return DBResponseComponent.factory();
	}
	
	
	
	public DBResponseComponent save(Course course, int categoryId, int domainId) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<CourseCategory> optional1 = courseCatRep.findOneById(categoryId);
			Optional<Domain> optional2 = domainRep.findOneById(domainId);
			if(optional1.isPresent() && optional2.isPresent()) {
				course.setDeleted(PolarResponseType.NO);
				course.setCourseCategory(optional1.get());
				course.setDomain(optional2.get());
				course = courseRep.save(course);
				if(course != null) {
					responseMap.put("course", course);
				
				}else {
					responseCode = STResponseCode.OBJECT_NOT_SAVED;
					responseStatus = STResponseStatus.OBJECT_NOT_SAVED;
					responseMessage = STResponseMessage.FAILED;
				}
			}else {
				responseCode = STResponseCode.PARENT_OBJECT_NOT_FOUND;
				responseStatus = STResponseStatus.PARENT_OBJECT_NOT_FOUND;
				responseMessage = STResponseMessage.FAILED;
			}
			
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);
	
	}
	
	
	
	
public DBResponseComponent update(Course course, int catId) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {			
			Optional<CourseCategory> optional1 = courseCatRep.findOneById(catId);
			if(optional1.isPresent()) {
				course.setDeleted(PolarResponseType.NO);
				course.setCourseCategory(optional1.get());
				course = courseRep.save(course);
				if(course != null) {
					responseMap.put("course", course);
				}else {
					responseCode = STResponseCode.OBJECT_NOT_SAVED;
					responseStatus = STResponseStatus.OBJECT_NOT_SAVED;
					responseMessage = STResponseMessage.FAILED;
					
				}
			}else {
				responseCode = STResponseCode.PARENT_OBJECT_NOT_FOUND;
				responseStatus = STResponseStatus.PARENT_OBJECT_NOT_FOUND;
				responseMessage = STResponseMessage.FAILED;
				
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			ex.printStackTrace();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);
	
	}
	
	
	public DBResponseComponent remove(int id) {
		
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<Course> optional = courseRep.findOneById(id);
			if(optional.isPresent()) {
				Course course = optional.get();
				course.setDeleted(PolarResponseType.YES);
				courseRep.save(course);				
			}else {			
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;
			}
			
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}		
		return DBResponseComponent.factory(responseStatus, responseCode, responseMessage);
	}
}
