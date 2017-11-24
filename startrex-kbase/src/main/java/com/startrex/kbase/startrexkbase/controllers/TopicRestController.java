package com.startrex.kbase.startrexkbase.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.startrex.kbase.startrexkbase.dao.TopicDAO;
import com.startrex.kbase.startrexkbase.dto.TopicDTO;
import com.startrex.kbase.startrexkbase.entities.Topic;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;

@RestController
@RequestMapping("topic")
public class TopicRestController {
	
	@Autowired
	private TopicDAO topicDAO;
	Pageable pageable;
	@RequestMapping(method=RequestMethod.GET, value="{id}")
	public STResponseComponent get(HttpServletRequest request,
			                       @PathVariable(value="id") String id) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		TopicDTO topicDTO = TopicDTO.factory();
		try {
			int topicId = Integer.parseInt(id);
			DBResponseComponent dbResponse = topicDAO.get(topicId);
			DBResponseComponent dbResponse1 = topicDAO.getTopicList(pageable);
			Topic topic = (Topic)dbResponse.responseMap.get("topic");
			BeanUtils.copyProperties(topic, topicDTO);
			topicDTO.setId(Integer.toString(topic.getId()));
			topicDTO.setWeight(Integer.toString(topic.getWeight()));
			if(dbResponse.responseCode == STResponseCode.OK && dbResponse1.responseCode == STResponseCode.OK) {
				responseMap.put("topic", topicDTO);
				responseMap.put("topics", dbResponse1.responseMap.get("topics"));
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
	
	@RequestMapping(method=RequestMethod.GET, value="get/{courseId}")
	public STResponseComponent getFirstByCourse(HttpServletRequest request, @PathVariable(value="courseId") String courseId) {
		System.out.println("what is not going right here");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		TopicDTO topicDTO = TopicDTO.factory();
		try {
			DBResponseComponent dbResponse = topicDAO.findTopOneByCourse(Integer.parseInt(courseId));
			DBResponseComponent dbResponse1 = topicDAO.getTopicList(pageable);
			System.out.println(dbResponse.responseCode  + " -- " +dbResponse1.responseCode);
			if(dbResponse.responseCode == STResponseCode.OK && dbResponse1.responseCode == STResponseCode.OK) {
				Topic topic = (Topic)dbResponse.responseMap.get("topic");
				BeanUtils.copyProperties(topic, topicDTO);
				topicDTO.setId(Integer.toString(topic.getId()));
				topicDTO.setCourseId(Integer.toString(topic.getCourse().getId()));
				topicDTO.setWeight(Integer.toString(topic.getWeight()));
				responseMap.put("topic", topicDTO);
				System.out.println("=== " + topicDTO + " good");
				responseMap.put("topics", dbResponse1.responseMap.get("topics"));
			}else{
				System.out.println("all went bad");
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
				responseMessage = dbResponse.responseMessage;
			}
		}catch(NumberFormatException ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			System.out.println("====================Number formating failed======================");
			ex.printStackTrace();
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR; 
			responseMessage = ex.getMessage();
			System.out.println("====================Excption thrown in Controller======================");
			ex.printStackTrace();
		}		

		return STResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);	
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="add")
	public STResponseComponent add(@RequestBody TopicDTO topicDTO) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		Topic topic = Topic.factory();
		try {	
			int courseId = Integer.parseInt(topicDTO.getCourseId());
			//copy the properties of the DTO to the Topic Entity
			BeanUtils.copyProperties(topicDTO, topic);
			topic.setWeight(Integer.parseInt(topicDTO.getWeight()));
			System.out.println(topicDTO.getWeight() + " ========= add");
			DBResponseComponent dbResponse = topicDAO.save(topic, courseId);
			if(dbResponse.responseCode == STResponseCode.OK) {
				topic = (Topic)dbResponse.responseMap.get("topic");
				topicDTO.setId(Integer.toString(topic.getId()));
				BeanUtils.copyProperties(topic, topicDTO);
				responseMap.put("topic", topicDTO);//copy created object back to the DTO
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
	public STResponseComponent update(@RequestBody TopicDTO topicDTO) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Topic topic = Topic.factory();
			//copy the properties of the DTO to the Topic Entity
			BeanUtils.copyProperties(topicDTO, topic);
			topic.setId(Integer.parseInt(topicDTO.getId()));
			topic.setWeight(Integer.parseInt(topicDTO.getWeight()));			
			DBResponseComponent dbResponse = topicDAO.update(topic);
			if(dbResponse.responseCode == STResponseCode.OK) {
				topic = (Topic)dbResponse.responseMap.get("topic");				
				//BeanUtils.copyProperties(topic, topicDTO);
				responseMap.put("topic", topicDTO);//copy created object back to the DTO
				//responseMap.put("topic", dbResponse.responseMap.get("topic"));
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
	public STResponseComponent remove(@PathVariable(value="id") String id) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			int topicId = Integer.parseInt(id);
			DBResponseComponent dbResponse = topicDAO.remove(topicId);
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
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}
				
		return STResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);

	}
}
