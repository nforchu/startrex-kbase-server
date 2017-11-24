package com.startrex.kbase.startrexkbase.dto;

import com.startrex.kbase.startrexkbase.helpers.enumeration.ObjectVisibilityType;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;

public class AudiovisualDTO {
	
	private String id;
	private String title;
	private String link;
	private String provider;
	private String weight;
	private String code;
	private PolarResponseType published;	
	private ObjectVisibilityType visibility;	
	private String topicId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
		
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public PolarResponseType getPublished() {
		return published;
	}
	public void setPublished(PolarResponseType published) {
		this.published = published;
	}
	public ObjectVisibilityType getVisibility() {
		return visibility;
	}
	public void setVisibility(ObjectVisibilityType visibility) {
		this.visibility = visibility;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	
	public static AudiovisualDTO factory() {
		return new AudiovisualDTO();
	}
}
