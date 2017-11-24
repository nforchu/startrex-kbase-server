package com.startrex.kbase.startrexkbase.dto;

import java.io.Serializable;
import java.util.Date;

import com.startrex.kbase.startrexkbase.helpers.enumeration.ObjectVisibilityType;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;


public class CourseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;		
	private String title;
	private String description; 
	private String prerequisite;
	private String audience;
	private PolarResponseType published;	
	private ObjectVisibilityType visibility;
	private PolarResponseType hasVideo;
	private PolarResponseType hasText;
	private PolarResponseType hasQuiz;
	private PolarResponseType deleted;
	private Date created_at;
	private Date updated_at;	
	private String domainId;
	private String courseCategoryId;
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
	public void setPrerequisite(String prerequisite) {
		this.prerequisite = prerequisite;
	}
	public String getAudience() {
		return audience;
	}
	public void setAudience(String audience) {
		this.audience = audience;
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
	public PolarResponseType getHasText() {
		return hasText;
	}
	public void setHasText(PolarResponseType hasText) {
		this.hasText = hasText;
	}
	public PolarResponseType getHasQuiz() {
		return hasQuiz;
	}
	public void setHasQuiz(PolarResponseType hasQuiz) {
		this.hasQuiz = hasQuiz;
	}
	public PolarResponseType getDeleted() {
		return deleted;
	}
	public void setDeleted(PolarResponseType deleted) {
		this.deleted = deleted;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getCourseCategoryId() {
		return courseCategoryId;
	}
	public void setCourseCategoryId(String courseCategoryId) {
		this.courseCategoryId = courseCategoryId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public static CourseDTO factory() {
		return new CourseDTO();
	}
		
}
