package com.startrex.kbase.startrexkbase.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.startrex.kbase.startrexkbase.entities.Course;
import com.startrex.kbase.startrexkbase.entities.Domain;
import com.startrex.kbase.startrexkbase.entities.User;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;
import com.startrex.kbase.startrexkbase.repositories.CourseRepository;
import com.startrex.kbase.startrexkbase.repositories.DomainRepository;
import com.startrex.kbase.startrexkbase.repositories.UserRepository;

@Service
public class DomainDAO {

	@Autowired
	private DomainRepository domainRep;
	@Autowired
	private CourseRepository courseRep;
	
	@Autowired
	private UserRepository userRep;
	
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
			Optional<Domain> optional = domainRep.findOneById(id);
			if(optional.isPresent()) {
				map.put("domain", optional.get());			
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
			Page<Domain> domains = domainRep.findAll(pageable);
			if(domains.getTotalElements() > 0) {
				responseMap.put("domains", domains);
			}else {								
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;
			}			
		}catch(DataAccessException ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();			
		}
		
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);
	}
	
	public DBResponseComponent findByUserId(Pageable pageable, long userId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<User> optional = userRep.findOneById(userId);
			
			//Page<Domain> domains = domainRep.findBydomainMembers(pageable, userId);
			//if(domains.getTotalElements() > 0) {
			Set<Domain> domains = new HashSet<Domain>();
			if(optional.isPresent()) {				
				User user = optional.get();
				System.out.println(user.getDomainMembers().size());
				user.getDomainMembers().forEach(member -> {
					domains.add(member.getDomain());
				});				
				responseMap.put("domains", domains);
			}else {								
				responseCode = STResponseCode.ERROR;
				responseStatus = STResponseStatus.ERROR;
				responseStatus = STResponseStatus.ERROR;
			}			
		}catch(DataAccessException ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();		
			ex.printStackTrace();
		}
		
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);
	}
	
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	public DBResponseComponent getCourses(int domainId, Pageable pageable) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Page<Course> courses = courseRep.findByDomainId(domainId, pageable);
			System.out.println("------------------------" + domainId + "==================");
			if(courses.getTotalElements() > 0) {
				responseMap.put("courses", courses);
			}else {								
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;
			}			
			
		}catch(DataAccessException ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			ex.printStackTrace();
		}
		
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);
	}
	
	
	/**
	 * 
	 * @param courseCategory
	 * @return
	 */
	public DBResponseComponent save(Domain domain) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			System.out.println("+++++++++++++++++"+ domain.getId()+ "++++++++++++++++++++");
			domain.setDeleted(PolarResponseType.NO);
			Domain d = domainRep.save(domain);
			if(d != null) {
				responseMap.put("domain", d);
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
			Optional<Domain> optional = domainRep.findOneById(id);
			if(optional.isPresent()) {
				Domain domain = optional.get();
				domain.setDeleted(PolarResponseType.YES);
				domainRep.save(domain);				
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
