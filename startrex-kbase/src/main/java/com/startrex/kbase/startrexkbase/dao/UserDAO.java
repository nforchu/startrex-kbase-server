package com.startrex.kbase.startrexkbase.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startrex.kbase.startrexkbase.entities.Domain;
import com.startrex.kbase.startrexkbase.entities.User;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;
import com.startrex.kbase.startrexkbase.repositories.CourseRepository;
import com.startrex.kbase.startrexkbase.repositories.UserRepository;

@Service
public class UserDAO {

	@Autowired
	private UserRepository userRep; 
	
	@Autowired
	private CourseRepository courseRep;
	
	public DBResponseComponent findByUsernameAndPassword(String username, String password) {		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK; 
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<User> optional = userRep.findOneByUsernameAndPassword(username, password);
			if(optional.isPresent()) {
				System.out.println(optional.get());
				responseMap.put("user", optional.get());
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

	public DBResponseComponent getHomepageData(User user, long userId) {		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK; 
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<User> optional = Optional.empty();
			if(user == null) {
				optional = userRep.findOneById(userId);
			}else {
				optional = Optional.of(user);
			}			
			if(optional.isPresent()) {
				user = optional.get();
				Map <String, Object> map = new HashMap<String, Object>();
				if(user.getDomainMembers().size() == 0) {//return all unrestricted courses
					map.put("openportal", null);
				}else if(user.getDomainMembers().size() == 1) {//return course for specific domain
								
					user.getDomainMembers().forEach(member -> {
						map.put("courses", member.getDomain().getCourses());						
					});
					responseMap.put("data", map);
				}else {//return all user domains
					Set<Domain> domains = new HashSet<Domain>();
					user.getDomainMembers().forEach(member -> {
						domains.add(member.getDomain());						
						
					});	
					map.put("domains", domains);
					responseMap.put("data", map);
				}
				System.out.println(optional.get());
				responseMap.put("user", optional.get());
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

}
