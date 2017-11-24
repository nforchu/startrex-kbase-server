package com.startrex.kbase.startrexkbase.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;
import com.startrex.kbase.startrexkbase.helpers.enumeration.QuestionType;
import com.startrex.kbase.startrexkbase.helpers.enumeration.QuestionVisibilityType;

@Entity
@Table(name="questions")
@Where(clause="deleted='NO'")
//@NamedQuery(name="Question.findByTopicId", 
//query="select q from Question q where q.topic_id = ?1")
public class Question implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="question_id")
	private int id;
	@Column(name="question", columnDefinition="text", nullable=false)
	private String question;
	@Enumerated(EnumType.STRING)
	private QuestionVisibilityType visibility;
	@Enumerated(EnumType.STRING)
	private QuestionType type;
	@Column(name="response_count")
	private String responseCount;
	private String difficulty;
	private float totalScore;
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private PolarResponseType deleted;
	@Column(updatable=false, name="created_at")
	private Date createdAt;
	@Column(updatable=false, name="updated_at")
	private Date updatedAt;
	@OneToMany(mappedBy="question", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)	
	private Set<Answer> answers;
	@ManyToOne
	@JoinColumn(name="topic_id")
	@JsonIgnore
	private Topic topic;
	@ManyToMany
	@JsonIgnore
	private Collection<Assessment> assessments;
	@OneToMany(mappedBy="question", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
	private Set<QuestionComponent> components;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public PolarResponseType getDeleted() {
		return deleted;
	}

	public void setDeleted(PolarResponseType deleted) {
		this.deleted = deleted;
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

	
	
	public float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}
	

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public Collection<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}
	
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	public Set<QuestionComponent> getComponents() {
		return components;
	}

	public void setComponents(Set<QuestionComponent> components) {
		this.components = components;
	}

	
	@Override
	public String toString() {
		return "Question [id=" + id + ", visibility=" + visibility + ", type=" + type + ", responseCount="
				+ responseCount + ", totalScore=" + totalScore + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Question other = (Question) obj;
		if (id != other.id)
			return false;
		if (type != other.type)
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
	 
	 
	 public static Question factory() {
		 return new Question();
	 }
	
}
