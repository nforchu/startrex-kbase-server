package com.startrex.kbase.startrexkbase.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.startrex.kbase.startrexkbase.dao.CourseCategoryDAO;
import com.startrex.kbase.startrexkbase.dao.CourseDAO;
import com.startrex.kbase.startrexkbase.dto.CourseDTO;
import com.startrex.kbase.startrexkbase.entities.Course;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;
@RestController
@RequestMapping("course")
public class CourseRestController {
	
	@Autowired 
	private CourseDAO courseDAO;
	
	@Autowired 
	private CourseCategoryDAO catDAO;
	
	Pageable pageable;
	
	
	@RequestMapping(method=RequestMethod.GET, value="{id}")
	@PreAuthorize("hasRole('ADMINISTER_COURSE')")
	public STResponseComponent get(HttpServletRequest request, @PathVariable(value="id") String id) {		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		CourseDTO courseDTO = CourseDTO.factory();
		try {
			int courseId = Integer.parseInt(id);
			DBResponseComponent dbResponse = courseDAO.get(courseId);
			DBResponseComponent dbResponse1 = catDAO.getAll(pageable);
			if(dbResponse.responseCode == STResponseCode.OK && dbResponse1.responseCode == STResponseCode.OK) {
				Course course = (Course)dbResponse.responseMap.get("course");
				BeanUtils.copyProperties(courseDTO, course);
				courseDTO.setId(Integer.toString(course.getId()));
				courseDTO.setCourseCategoryId(Integer.toString(course.getCourseCategory().getId()));
				responseMap.put("categories", dbResponse1.responseMap.get("categories"));
				responseMap.put("course", courseDTO);
			}else{
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
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

		return STResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);	
	}
	
	@RequestMapping(method=RequestMethod.GET, value="display/{id}")
	public STResponseComponent getInViewMode(HttpServletRequest request, Pageable pageable, @PathVariable(value="id") String id) {		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		CourseDTO courseDTO = CourseDTO.factory();
		try {
			int courseId = Integer.parseInt(id);
			DBResponseComponent dbResponse = courseDAO.get(courseId);
			DBResponseComponent dbResponse1 = courseDAO.getCourseList(pageable);
			if(dbResponse.responseCode == STResponseCode.OK && dbResponse1.responseCode == STResponseCode.OK) {
				Course course = (Course)dbResponse.responseMap.get("course");
				BeanUtils.copyProperties(courseDTO, course);
				courseDTO.setId(Integer.toString(course.getId()));
				courseDTO.setCourseCategoryId(Integer.toString(course.getCourseCategory().getId()));
				responseMap.put("courses", dbResponse1.responseMap.get("courses"));
				responseMap.put("course", courseDTO);
			}else{
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
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

		return STResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);	
	}
	
	@RequestMapping(method=RequestMethod.GET, value="get/all")
	public STResponseComponent getAll(Pageable pageable) {		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;

		try {
			DBResponseComponent dbResponse = courseDAO.findAll(pageable);
			if(dbResponse.responseCode == STResponseCode.OK) {
				responseMap.put("courses", dbResponse.responseMap.get("courses"));
			}else{
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			ex.printStackTrace();
		}		

		return STResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);	
	}
	
	@RequestMapping(method=RequestMethod.POST, value="add")
	@PreAuthorize("hasRole('ADMINISTER_COURSE')")
	public STResponseComponent add(@RequestBody CourseDTO courseDTO) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		Course course = Course.factory();
		try {	
			int catId = Integer.parseInt(courseDTO.getCourseCategoryId());
			int dId = Integer.parseInt(courseDTO.getDomainId());
			course.setId(Integer.parseInt(courseDTO.getId()));
			//copy the properties of the DTO to the Course Entity
			BeanUtils.copyProperties(course, courseDTO);
			DBResponseComponent dbResponse = courseDAO.save(course, catId, dId);
			if(dbResponse.responseCode == STResponseCode.OK) {
				BeanUtils.copyProperties(courseDTO, dbResponse.responseMap.get("course"));
				responseMap.put("course", courseDTO);//copy created object back to the DTO
			}else{
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
				responseMessage = dbResponse.responseMessage;
			}
		}catch(NumberFormatException ex){
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

		return STResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);	
	
	}
	
	@RequestMapping(method=RequestMethod.PATCH, value="update")
	@PreAuthorize("hasRole('ADMINISTER_COURSE')")
	public STResponseComponent update(@RequestBody CourseDTO courseDTO) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		Course course = Course.factory();
		try {	
			
			int catId = Integer.parseInt(courseDTO.getCourseCategoryId());
			BeanUtils.copyProperties(course, courseDTO);
		    
			DBResponseComponent dbResponse = courseDAO.update(course, catId);
			if(dbResponse.responseCode == STResponseCode.OK) {
				responseMap.put("course", dbResponse.responseMap.get("course"));
			}else{
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
				responseMessage = dbResponse.responseMessage;
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			ex.printStackTrace();
		}		

		return STResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);	
	
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="delete/{id}")
	@PreAuthorize("hasRole('ADMINISTER_COURSE')")
	public STResponseComponent remove(@PathVariable(value="id") String id) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			int courseId = Integer.parseInt(id);
			DBResponseComponent dbResponse = courseDAO.remove(courseId);
			if(dbResponse.responseCode == STResponseCode.OK) {
				responseMap.put("revertToken", id);
			}else{
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
				responseMessage = dbResponse.responseMessage;
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
				
		return STResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);
	}	
	
}
