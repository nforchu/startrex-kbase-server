package com.startrex.kbase.startrexkbase.dto;

import java.util.Date;

import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;

public class ResponseDTO {
	private String id;
	private String value;
	private PolarResponseType correct;	
	private String score;
	private Date createdAt;
	private Date updatedAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public PolarResponseType getCorrect() {
		return correct;
	}
	public void setCorrect(PolarResponseType correct) {
		this.correct = correct;
	}
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
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
	@Override
	public String toString() {
		return "ResponseDTO [id=" + id + ", value=" + value + ", correct=" + correct + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
	
	
	public static ResponseDTO factory() {
		return new ResponseDTO();
	}
	
		
}
