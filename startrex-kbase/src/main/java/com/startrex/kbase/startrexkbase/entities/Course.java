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
@Table(name="courses")
@Where(clause="deleted='NO'")
public class Course implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="COURSE_ID")
	private int id;	
	
	private String title;
	
	@Column(name="description", columnDefinition="text")
	private String description;
	
	@Column(name="prerequisite", columnDefinition="text")
	private String prerequisite;
	
	@Column(name="audience", columnDefinition="text")
	private String audience;
	
	@Enumerated(EnumType.STRING)
	private PolarResponseType published;
	
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
	@JsonIgnore
	private PolarResponseType deleted;
	
	@Column(updatable=false)
	private Date created_at;
	
	private Date updated_at;	
	
	@ManyToOne
	@JoinColumn(name="domain_id", updatable=false)
	@JsonIgnore
	private Domain domain;
	
	@ManyToOne
	@JoinColumn(name="course_category_id")
	@JsonIgnore
	
	private CourseCategory courseCategory;
	@OneToMany(mappedBy="course")
	@JsonIgnore
	
	private Collection<CourseSession> courseSesssions;
	@OneToMany(mappedBy="course")
	@JsonIgnore
	private Collection<Topic> topics;
	
		
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
	
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public CourseCategory getCourseCategory() {
		return courseCategory;
	}
	public void setCourseCategory(CourseCategory courseCategory) {
		this.courseCategory = courseCategory;
	}
	
	public Collection<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Collection<Topic> topics) {
		this.topics = topics;
	}
	
	public void addTopic(Topic topic) {
		topics.add(topic);
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
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
		Course other = (Course) obj;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
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
	 
	 
	 public static Course factory() {
		 return new Course();
	 }
	
}
