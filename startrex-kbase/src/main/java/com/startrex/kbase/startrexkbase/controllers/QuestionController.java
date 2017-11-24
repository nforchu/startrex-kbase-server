package com.startrex.kbase.startrexkbase.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.startrex.kbase.startrexkbase.dao.QuestionDAO;
import com.startrex.kbase.startrexkbase.dto.AudiovisualDTO;
import com.startrex.kbase.startrexkbase.dto.QuestionDTO;
import com.startrex.kbase.startrexkbase.entities.Answer;
import com.startrex.kbase.startrexkbase.entities.Audiovisual;
import com.startrex.kbase.startrexkbase.entities.Question;
import com.startrex.kbase.startrexkbase.entities.QuestionComponent;
import com.startrex.kbase.startrexkbase.helpers.enumeration.ContentType;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;

@RestController
@RequestMapping("question")
public class QuestionController {
	@Autowired
	private QuestionDAO questionDAO;
	Pageable pageable;
	
	
	@RequestMapping(method=RequestMethod.GET, value="get/{id}")
	public STResponseComponent get(@PathVariable(value="id") String id) {		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			int questionId = Integer.parseInt(id);
			DBResponseComponent dbResponse = questionDAO.get(questionId);
			DBResponseComponent dbResponse1 = questionDAO.getQuestionList(pageable, 1);
			if(dbResponse.responseCode == STResponseCode.OK && dbResponse1.responseCode == STResponseCode.OK) {
				responseMap.put("questions", dbResponse1.responseMap.get("questions"));
				responseMap.put("question", dbResponse.responseMap.get("question"));
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
	
	
	@RequestMapping(method=RequestMethod.GET, value="first/{topicId}")
	public STResponseComponent getFirstByTopic(@PathVariable(value="topicId") String topicId) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		QuestionDTO questionDTO = QuestionDTO.factory();
		try {			
			DBResponseComponent dbResponse = questionDAO.findTopOneByTopic(Integer.parseInt(topicId));
			DBResponseComponent dbResponse1 = questionDAO.getQuestionList(pageable, Integer.parseInt(topicId));
			//DBResponseComponent dbResponse1 = catDAO.getAll(pageable);
			if(dbResponse.responseCode == STResponseCode.OK && dbResponse1.responseCode == STResponseCode.OK) {
				//Question question = (Question)dbResponse.responseMap.get("question");		
				//BeanUtils.copyProperties(questionDTO, question);
				//questionDTO.setId(Integer.toString(question.getId()));
				//questionDTO.setTopicId(Integer.toString(question.getTopic().getId()));

				responseMap.put("questions", dbResponse1.responseMap.get("questions"));
				responseMap.put("question", dbResponse.responseMap.get("question"));
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
	
	@RequestMapping(method=RequestMethod.GET, value="topic/{id}")
	public STResponseComponent getByTopic(@PathVariable(value="id") String id, Pageable pageable) {		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			int topicId = Integer.parseInt(id);
			DBResponseComponent dbResponse = questionDAO.getByTopic(topicId, pageable);
			if(dbResponse.responseCode == STResponseCode.OK) {
				responseMap.put("questions", dbResponse.responseMap.get("questions"));
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
	
	@RequestMapping(method=RequestMethod.POST, value="add")
	public STResponseComponent add(@RequestBody QuestionDTO questionDTO) {
		System.out.println(questionDTO.getComponents());
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		Question question = Question.factory();
		try {				
			System.out.println(questionDTO.getTotalScore()+"---------TotalScore");
			//question.setResponseCount(Integer.parseInt(questionDTO.getResponseCount()));//responseCount was not copied by BeansUtil because it cannot auto convert from string to int so copy it
			BeanUtils.copyProperties(questionDTO, question);//copy the properties of the DTO to the Course Entity			
			question.setTotalScore(Float.parseFloat(questionDTO.getTotalScore()));
			Set<Answer> ans = new HashSet<Answer>();
			for(Object o : (HashSet<Object>)questionDTO.getResponses()) {	
				@SuppressWarnings("unchecked")
				Map<String, String> map = (HashMap<String, String>)o;
				Answer answer = Answer.factory();
				System.out.println((Object)map.get("score"));
				//System.out.println(score);
				answer.setCorrect(PolarResponseType.valueOf(map.get("correct")));
				answer.setValue(map.get("answer"));
				//double a = Double.parseDouble((String) map.get("score"));
				answer.setScore(Float.valueOf(String.valueOf(map.get("score"))));
				answer.setQuestion(question);				
				ans.add(answer);
			}
			question.setAnswers(ans);
		
			Set<QuestionComponent> components = new HashSet<QuestionComponent>();
			for(Object o : (HashSet<Object>)questionDTO.getComponents()) {	
				@SuppressWarnings("unchecked")
				Map<String, String> map = (HashMap<String, String>)o;
				QuestionComponent component = QuestionComponent.factory();				
				component.setValue(map.get("value"));
				component.setContentType(ContentType.valueOf(map.get("contentType")));
				component.setMode(map.get("mode"));
				component.setWeight(Integer.valueOf(String.valueOf(map.get("weight"))));
				component.setQuestion(question);
				component.setDeleted("NO");
				components.add(component);
			}
			question.setComponents(components);
			DBResponseComponent dbResponse = questionDAO.save(question, Integer.parseInt(questionDTO.getTopicId()));
			if(dbResponse.responseCode == STResponseCode.OK) {
				Question q = (Question)dbResponse.responseMap.get("question");
				questionDTO.setId(Integer.toString(q.getId()));//copy Id from Entity to DTO since BeanUtils does not copy the Id
				BeanUtils.copyProperties(q, questionDTO);
				responseMap.put("question", questionDTO);//copy created object back to the DTO
				Set<Object> as = new HashSet<Object>();
				for(Answer answer : q.getAnswers()) {	
					as.add(answer);					
				}
				questionDTO.setId(Integer.toString(q.getId()));
				questionDTO.setResponses(as);
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
	public STResponseComponent update(@RequestBody QuestionDTO questionDTO) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		Question question = Question.factory();
		try {				
			//question.setResponseCount(Integer.parseInt(questionDTO.getResponseCount()));//responseCount was not copied by BeansUtil because it cannot auto convert from string to int so copy it
			BeanUtils.copyProperties(questionDTO, question);//copy the non collection properties of the DTO to the Course Entity			

			Set<Answer> ans = new HashSet<Answer>();
			for(Object o : (HashSet<Object>)questionDTO.getResponses()) {	
				@SuppressWarnings("unchecked")
				Map<String, String> map = (HashMap<String, String>)o;
				Answer answer = Answer.factory();
				answer.setCorrect(PolarResponseType.valueOf(map.get("correct")));
				answer.setValue(map.get("value"));
				answer.setScore(Float.parseFloat(map.get("score").toString()));
				answer.setQuestion(question);				
				ans.add(answer);
			}
			question.setAnswers(ans);
		
			Set<QuestionComponent> components = new HashSet<QuestionComponent>();
			for(Object o : (HashSet<Object>)questionDTO.getComponents()) {	
				@SuppressWarnings("unchecked")
				Map<String, String> map = (HashMap<String, String>)o;
				QuestionComponent component = QuestionComponent.factory();				
				component.setValue(map.get("value").toString());
				component.setContentType(ContentType.valueOf(map.get("contentType")));
				component.setMode(map.get("mode").toString());
				component.setWeight(Integer.parseInt(map.get("weight").toString()));
				component.setQuestion(question);
				component.setDeleted("NO");
				components.add(component);
			}
			question.setComponents(components);
			DBResponseComponent dbResponse = questionDAO.update(question);
			if(dbResponse.responseCode == STResponseCode.OK) {
				responseMap.put("questoin", dbResponse.responseMap.get("question"));
			}else{
				responseCode = dbResponse.responseCode;
				responseStatus = dbResponse.responseStatus;
				responseMessage = dbResponse.responseMessage;
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
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
			int questionId = Integer.parseInt(id);
			DBResponseComponent dbResponse = questionDAO.remove(questionId);
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
