package com.startrex.kbase.startrexkbase.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startrex.kbase.startrexkbase.helpers.enumeration.ObjectVisibilityType;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;

@Entity
@Table(name="topics")
@Where(clause="deleted='NO'")
public class Topic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="topic_id")
	private int id;
	private String title;
	@Column(name="description", columnDefinition="text")
	private String description;
	@Column(name="prerequisite", columnDefinition="text")
	private String prerequisite;
	private int weight;
	@Enumerated(EnumType.STRING)
	private ObjectVisibilityType visibility;
	@Enumerated(EnumType.STRING)
	@Column(name="has_video")
	private PolarResponseType hasVideo;
	@Enumerated(EnumType.STRING)
	@Column(name="has_quiz")
	private PolarResponseType hasQuiz;
	@Enumerated(EnumType.STRING)
	@Column(name="has_text")
	private PolarResponseType hasText;
	@Enumerated(EnumType.STRING)
	private PolarResponseType published;
	@Enumerated(EnumType.STRING)
	private PolarResponseType deleted;
	@Column(updatable=false)
	private Date created_at;
	private Date updated_at;
	@OneToMany(mappedBy="topic")
	@JsonIgnore
	private Collection<Question> questions;
	@OneToMany(mappedBy="topic")
	@JsonIgnore
	private Collection<Audiovisual> audiovisauls;
	@OneToMany(mappedBy="topic")
	@JsonIgnore
	private Collection<TopicSection> topicSections;
	@ManyToOne
	@JoinColumn(name="course_id", updatable=false)
	private Course course;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
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
	public PolarResponseType getPublished() {
		return published;
	}
	public void setPublished(PolarResponseType published) {
		this.published = published;
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
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	@Override
	public String toString() {
		return "Topic [id=" + id + ", title=" + title + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Topic other = (Topic) obj;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	@PrePersist
	 void createdAt() {
	    this.created_at = new Date();
	 }
	
	 @PreUpdate
	 void updatedAt() {
	    this.updated_at = new Date();
	 }
	
	public static Topic factory() {
		return new Topic();
	}
	
}
