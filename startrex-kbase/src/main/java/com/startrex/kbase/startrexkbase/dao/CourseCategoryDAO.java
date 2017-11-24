package com.startrex.kbase.startrexkbase.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.startrex.kbase.startrexkbase.entities.CourseCategory;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;
import com.startrex.kbase.startrexkbase.repositories.CourseCategoryRepository;


@Service
public class CourseCategoryDAO {
	
	@Autowired
	private CourseCategoryRepository courseCatRep;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public DBResponseComponent get(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {			
			Optional<CourseCategory> optional = courseCatRep.findOneById(id);
			if(optional.isPresent()) {
				map.put("category", optional.get());			
			}else {
				map.put("category", null);
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;				
			}
		}catch(DataAccessException ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();			
		}
		return DBResponseComponent.factory(map, responseStatus, responseCode, responseMessage);
	}
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	public DBResponseComponent getAll(Pageable pageable) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Page<CourseCategory> categories = courseCatRep.findAll(pageable);
			System.out.println(categories + "--------");
			System.out.println(categories.getTotalElements() + " -------------");
			if(categories.getTotalElements() == 0) {
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;
				System.out.println("+++++++++++=444444444444");
			}else {				
				responseMap.put("categories", categories);
				System.out.println("+++++++++++=555555555555555");
			}			
		}catch(DataAccessException ex) {
			System.out.println("+++++++++++=666666666666");
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();			
		}
		
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);
	}
	
	
	/**
	 * 
	 * @param courseCategory
	 * @return
	 */
	public DBResponseComponent save(CourseCategory courseCategory) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {			
			CourseCategory cc = courseCatRep.save(courseCategory);
			if(cc != null) {
				responseMap.put("category", cc);
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
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public DBResponseComponent remove(int id) {
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<CourseCategory> optional = courseCatRep.findOneById(id);
			if(optional.isPresent()) {
				CourseCategory cc = optional.get();
				cc.setDeleted(PolarResponseType.YES);
				courseCatRep.save(cc);				
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
