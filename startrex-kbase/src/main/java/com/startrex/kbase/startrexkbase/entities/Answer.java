package com.startrex.kbase.startrexkbase.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;

@Entity
@Table(name="answers")
@Where(clause="deleted='NO'")
public class Answer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="answer_id")
	private int id;
	private String value;
	@Enumerated(EnumType.STRING)
	private PolarResponseType correct;	
	private float score;
	@Column(updatable=false, name="created_at")
	private Date createdAt;
	@Column(name="updated_at")
	private Date updatedAt;
	@JsonIgnore
	private String deleted;
	@ManyToOne
	@JoinColumn(name="question_id", updatable=false)
	@JsonIgnore
	private Question question;	
		
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
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

	
	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	
	@Override
	public String toString() {
		return "Answer [id=" + id + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		if (id != other.id)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@PrePersist
	 void createdAt() {
	    this.createdAt = new Date();
	 }
	
	 @PreUpdate
	 void updatedAt() {
	    this.updatedAt = new Date();
	 }
	 
	 
	 public static Answer factory() {
		 return new Answer();
	 }
}
