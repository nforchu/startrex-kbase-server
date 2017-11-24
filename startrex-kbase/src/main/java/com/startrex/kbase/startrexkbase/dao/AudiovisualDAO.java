package com.startrex.kbase.startrexkbase.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.startrex.kbase.startrexkbase.entities.Audiovisual;
import com.startrex.kbase.startrexkbase.entities.Topic;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;
import com.startrex.kbase.startrexkbase.repositories.AudiovisualRepository;
import com.startrex.kbase.startrexkbase.repositories.TopicRepository;

@Service
public class AudiovisualDAO {

	@Autowired private AudiovisualRepository audiovisualRep;
	@Autowired private TopicRepository topicRep;
	
	public DBResponseComponent get(int id) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<Audiovisual> optional = audiovisualRep.findOneById(id);
			if(optional.isPresent()) {
				responseMap.put("audiovisual", optional.get());
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
			Optional<Audiovisual> optional = audiovisualRep.findTop1ByTopicIdOrderByWeightAsc(topicId);
			if(optional.isPresent() && optional.get() != null) {
				responseMap.put("audiovisual", optional.get());				
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
	
	public DBResponseComponent getAudiovisualList(Pageable pageable) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Page<Audiovisual> audiovisuals = audiovisualRep.getAudiovisualList(pageable);
			if(audiovisuals.getTotalElements() == 0) {
				responseCode = STResponseCode.NO_OBJECT_FOUND;
				responseStatus = STResponseStatus.NO_OBJECT_FOUND;;
			}else {				
				responseMap.put("audiovisuals", audiovisuals);
				System.out.println(audiovisuals);
			}
		}catch(Exception ex) {
			responseCode = STResponseCode.ERROR;
			responseStatus = STResponseStatus.ERROR;
			responseMessage = ex.getMessage();
		}
		return DBResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);

	}
	
	public DBResponseComponent save(Audiovisual audiovisual, int topicId) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			Optional<Topic> optional = topicRep.findOneById(topicId);
			if(optional.isPresent()) {
				audiovisual.setDeleted(PolarResponseType.NO);
				audiovisual.setTopic(optional.get());
				audiovisual = audiovisualRep.save(audiovisual);
				if(audiovisual != null) {
					responseMap.put("audiovisual", audiovisual);
					System.out.println("almost there");
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
	
	
public DBResponseComponent update(Audiovisual audiovisual) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			
			audiovisual.setDeleted(PolarResponseType.NO);		
			audiovisual = audiovisualRep.save(audiovisual);
			if(audiovisual != null) {
				responseMap.put("audiovisual", audiovisual);
			}else {
				responseCode = STResponseCode.OBJECT_NOT_SAVED;
				responseStatus = STResponseStatus.OBJECT_NOT_SAVED;
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
}
