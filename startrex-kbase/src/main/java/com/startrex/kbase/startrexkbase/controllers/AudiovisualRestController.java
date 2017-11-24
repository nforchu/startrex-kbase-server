package com.startrex.kbase.startrexkbase.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.startrex.kbase.startrexkbase.dao.AudiovisualDAO;
import com.startrex.kbase.startrexkbase.dto.AudiovisualDTO;
import com.startrex.kbase.startrexkbase.entities.Audiovisual;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;

@RestController
@RequestMapping("video")
public class AudiovisualRestController {
	
	@Autowired private AudiovisualDAO audiovisualDAO;
	Pageable pageable;
	
	@RequestMapping(method=RequestMethod.GET, value="get/{id}")
	public STResponseComponent get(HttpServletRequest request, 
								   @PathVariable(value="id") String id) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		AudiovisualDTO audiovisualDTO = AudiovisualDTO.factory();
		try {			
			DBResponseComponent dbResponse = audiovisualDAO.get(Integer.parseInt(id));
			DBResponseComponent dbResponse1 = audiovisualDAO.getAudiovisualList(pageable);
			if(dbResponse.responseCode == STResponseCode.OK && dbResponse1.responseCode == STResponseCode.OK) {
				Audiovisual audiovisual = (Audiovisual)dbResponse.responseMap.get("audiovisual");
				BeanUtils.copyProperties(audiovisualDTO, audiovisual);
				audiovisualDTO.setId(Integer.toString(audiovisual.getId()));
				audiovisualDTO.setTopicId(Integer.toString(audiovisual.getTopic().getId()));
				responseMap.put("audiovisuals", dbResponse1.responseMap.get("audiovisuals"));
				responseMap.put("audiovisual", audiovisualDTO);
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
	
	
	@RequestMapping(method=RequestMethod.GET, value="first/{topicId}")
	public STResponseComponent getFirstByTopic(HttpServletRequest request, 
											   @PathVariable(value="topicId") String topicId) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		AudiovisualDTO audiovisualDTO = AudiovisualDTO.factory();
		try {			
			DBResponseComponent dbResponse = audiovisualDAO.findTopOneByTopic(Integer.parseInt(topicId));
			DBResponseComponent dbResponse1 = audiovisualDAO.getAudiovisualList(pageable);
			//DBResponseComponent dbResponse1 = catDAO.getAll(pageable);
			if(dbResponse.responseCode == STResponseCode.OK && dbResponse1.responseCode == STResponseCode.OK) {
				Audiovisual audiovisual = (Audiovisual)dbResponse.responseMap.get("audiovisual");		
				BeanUtils.copyProperties(audiovisualDTO, audiovisual);
				audiovisualDTO.setId(Integer.toString(audiovisual.getId()));
				audiovisualDTO.setTopicId(Integer.toString(audiovisual.getTopic().getId()));
				audiovisualDTO.setWeight(Short.toString(audiovisual.getWeight()));
				responseMap.put("audiovisuals", dbResponse1.responseMap.get("audiovisuals"));
				responseMap.put("audiovisual", audiovisualDTO);
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
	
	@RequestMapping(method=RequestMethod.POST, value="add")
	public STResponseComponent add(@RequestBody AudiovisualDTO audiovisualDTO) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		Audiovisual audiovisual = Audiovisual.factory();
		try {	
			System.out.println(audiovisualDTO.getWeight()+"====================");
			//copy the properties of the DTO to the Course Entity
			BeanUtils.copyProperties(audiovisual, audiovisualDTO);
			DBResponseComponent dbResponse = audiovisualDAO.save(audiovisual,
																 Integer.parseInt(audiovisualDTO.getTopicId()));
			if(dbResponse.responseCode == STResponseCode.OK) {
				BeanUtils.copyProperties(audiovisualDTO, dbResponse.responseMap.get("audiovisual"));
				responseMap.put("audiovisual", audiovisualDTO);//copy created object back to the DTO
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
	public STResponseComponent update(@RequestBody AudiovisualDTO audiovisualDTO) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		Audiovisual audiovisual = Audiovisual.factory();
		try {	
			System.out.println(audiovisualDTO.getWeight()+"====================");
			//copy the properties of the DTO to the Course Entity
			BeanUtils.copyProperties(audiovisual, audiovisualDTO);
			audiovisual.setId(Integer.parseInt(audiovisualDTO.getId()));
			audiovisual.setWeight(Short.parseShort(audiovisualDTO.getWeight()));
			DBResponseComponent dbResponse = audiovisualDAO.update(audiovisual);
			DBResponseComponent dbResponse1 = audiovisualDAO.getAudiovisualList(pageable);
			if(dbResponse.responseCode == STResponseCode.OK && dbResponse1.responseCode == STResponseCode.OK) {
				BeanUtils.copyProperties(audiovisualDTO, dbResponse.responseMap.get("audiovisual"));
				responseMap.put("audiovisual", audiovisualDTO);//copy created object back to the DTO
				responseMap.put("audiovisuals", dbResponse1.responseMap.get("audiovisuals"));
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
	
	@RequestMapping(method=RequestMethod.DELETE)
	public STResponseComponent remove(@RequestBody Audiovisual audiovisual) {
		
		return STResponseComponent.factory();
	}
	
}
