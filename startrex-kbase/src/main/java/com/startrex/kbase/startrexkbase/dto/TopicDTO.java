package com.startrex.kbase.startrexkbase.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.startrex.kbase.startrexkbase.helpers.enumeration.ObjectVisibilityType;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;

public class TopicDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	private String title;
	private String description;
	private String prerequisite;
	private String weight;
	private PolarResponseType published;	
	private ObjectVisibilityType visibility;
	private PolarResponseType hasVideo;
	private PolarResponseType hasQuiz;
	private PolarResponseType hasText;
	private String courseId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrerequisite() {
		return prerequisite;
	}	
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public void setPrerequisite(String prerequisite) {
		this.prerequisite = prerequisite;
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
	public PolarResponseType getHasVideo() {
		return hasVideo;
	}
	public void setHasVideo(PolarResponseType hasVideo) {
		this.hasVideo = hasVideo;
	}
	public PolarResponseType getHasQuiz() {
		return hasQuiz;
	}
	public void setHasQuiz(PolarResponseType hasQuiz) {
		this.hasQuiz = hasQuiz;
	}
	public PolarResponseType getHasText() {
		return hasText;
	}
	public void setHasText(PolarResponseType hasText) {
		this.hasText = hasText;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public static TopicDTO factory() {
		return new TopicDTO();
	}

}
