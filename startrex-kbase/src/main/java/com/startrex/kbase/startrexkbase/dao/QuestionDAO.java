package com.startrex.kbase.startrexkbase.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.startrex.kbase.startrexkbase.entities.Audiovisual;
import com.startrex.kbase.startrexkbase.entities.Question;
import com.startrex.kbase.startrexkbase.entities.Topic;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;
import com.startrex.kbase.startrexkbase.repositories.QuestionRepository;
import com.startrex.kbase.startrexkbase.repositories.TopicRepository;

@Service
public class QuestionDAO {

	@Autowired
	private QuestionRepository questionRep;
	@Autowired
	private TopicRepository topicRep;
	
	public DBResponseComponent get(int id) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<Question> optional = questionRep.findOneById(id);
			if(optional.isPresent()) {
				responseMap.put("question", optional.get());
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
	
	public DBResponseComponent findTopOneByTopic(int topicId) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<Question> optional = questionRep.findTop1ByTopicId(topicId);
			if(optional.isPresent() && optional.get() != null) {
				responseMap.put("question", optional.get());				
			}else {		
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;				
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();			
			ex.printStackTrace();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);

	}
	
	public DBResponseComponent getQuestionList(Pageable pageable, int topicId) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Page<Question> questions = questionRep.getQuestionList(pageable);
			if(questions.getTotalElements() == 0) {
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;
			}else {				
				responseMap.put("questions", questions);
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			ex.printStackTrace();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);

	}
	
	public DBResponseComponent getByTopic(int id, Pageable pageable) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {			
			Page<Question> questions = questionRep.findByTopicId(id, pageable);
			if(questions.getTotalElements() > 0) {
				responseMap.put("questions", questions);
				System.out.println(questions);
			}else {
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
			ex.printStackTrace();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);

	}
	
	public DBResponseComponent save(Question question, int topicId) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<Topic> optional = topicRep.findOneById(topicId);
			if(optional.isPresent()) {
				question.setDeleted(PolarResponseType.NO);
				question.setTopic(optional.get());
				System.out.println("here--------------");
				if((question = questionRep.save(question)) != null) {
					responseMap.put("question", question);
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
	
	public DBResponseComponent update(Question question) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			
			question.setDeleted(PolarResponseType.NO);			
			if((question = questionRep.save(question)) != null) {
				responseMap.put("topic", question);
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
			Optional<Question> optional = questionRep.findOneById(id);
			if(optional.isPresent()) {
				Question question = optional.get();
				question.setDeleted(PolarResponseType.YES);
				questionRep.save(question);				
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
