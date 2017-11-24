package com.startrex.kbase.startrexkbase.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.startrex.kbase.startrexkbase.entities.Course;
import com.startrex.kbase.startrexkbase.entities.Topic;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;
import com.startrex.kbase.startrexkbase.repositories.CourseRepository;
import com.startrex.kbase.startrexkbase.repositories.TopicRepository;

@Service
public class TopicDAO {
	
	@Autowired
	private TopicRepository topicRep;
	@Autowired
	private CourseRepository courseRep;
	
	public DBResponseComponent get(int id) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<Topic> optional = topicRep.findOneById(id);
			if(optional.isPresent()) {
				responseMap.put("topic", optional.get());
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

	public DBResponseComponent getTopicList(Pageable pageable) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Page<Topic> topics = topicRep.getTopicList(pageable);
			if(topics.getTotalElements() == 0) {
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;;
			}else {				
				responseMap.put("topics", topics);
				System.out.println(topics);
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);

	}
		
public DBResponseComponent findTopOneByCourse(int courseId) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<Topic> optional = topicRep.findTop1ByCourseIdOrderByWeightAsc(courseId);
			if(optional.isPresent() && optional.get() != null) {
				responseMap.put("topic", optional.get());
				System.out.println(optional.get() + "+++++++++++++");
			}else {		
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;				
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			System.out.println("====================Problem while making DB query======================");
			ex.printStackTrace();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);

	}
	public DBResponseComponent save(Topic topic, int courseId) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<Course> optional = courseRep.findOneById(courseId);
			if(optional.isPresent()) {
				topic.setDeleted(PolarResponseType.NO);
				topic.setCourse(optional.get());	
				
				if((topic = topicRep.save(topic)) != null) {
					responseMap.put("topic", topic);
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
	
	
	public DBResponseComponent update(Topic topic) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			
			topic.setDeleted(PolarResponseType.NO);			
			if((topic = topicRep.save(topic)) != null) {
				responseMap.put("topic", topic);
			}else {
				responseCode = STResponseCode.OBJECT_NOT_SAVED;
				responseStatus = STResponseStatus.OBJECT_NOT_SAVED;
				responseMessage = STResponseMessage.FAILED;
			}
			
			
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);
			
	}
	
	public DBResponseComponent remove(int id) {
		
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<Topic> optional = topicRep.findOneById(id);
			if(optional.isPresent()) {
				Topic topic = optional.get();
				topic.setDeleted(PolarResponseType.YES);
				topicRep.save(topic);				
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
