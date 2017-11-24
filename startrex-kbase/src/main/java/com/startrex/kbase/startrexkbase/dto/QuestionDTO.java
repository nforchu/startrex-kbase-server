package com.startrex.kbase.startrexkbase.dto;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.startrex.kbase.startrexkbase.helpers.enumeration.QuestionType;
import com.startrex.kbase.startrexkbase.helpers.enumeration.QuestionVisibilityType;

public class QuestionDTO {
	private String id;	
	private String question;
	private QuestionVisibilityType visibility;
	private QuestionType type;
	private String responseCount;
	private Date createdAt;
	private Date updatedAt;
	private String totalScore;
	private String difficulty;
	private String topicId;
	private Set<Object> responses;
	private Set<Object> components;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public QuestionVisibilityType getVisibility() {
		return visibility;
	}
	public void setVisibility(QuestionVisibilityType visibility) {
		this.visibility = visibility;
	}
	public QuestionType getType() {
		return type;
	}
	public void setType(QuestionType type) {
		this.type = type;
	}
	public String getResponseCount() {
		return responseCount;
	}
	public void setResponseCount(String responseCount) {
		this.responseCount = responseCount;
	}
	
	public Date getCreatedAt() {
		return createdAt; 
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
		
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public Collection<Object> getResponses() {
		return responses;
	}
	public void setResponses(Set<Object> responses) {
		this.responses = responses;
	}
	  
	public Set<Object> getComponents() {
		return components;
	}
	public void setComponents(Set<Object> components) {
		this.components = components;
	}
	
	public static QuestionDTO factory() {
		return new QuestionDTO();
	}
	@Override
	public String toString() {
		return "QuestionDTO [id=" + id + ", question=" + ", visibility=" + visibility + ", type=" + type
				+ ", responseCount=" + responseCount + "]";
	}
	
		
}
