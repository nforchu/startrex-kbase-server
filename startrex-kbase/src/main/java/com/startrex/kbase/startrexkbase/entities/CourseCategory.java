package com.startrex.kbase.startrexkbase.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;


@Entity
@Table
@Where(clause="deleted='NO'")
public class CourseCategory implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="course_category_id")
	private int id;
	@Column(name="name", nullable=false)
	private String name;
	@Column(name="description", columnDefinition="text")
	private String description;	
	@Enumerated(EnumType.STRING)
	@Column(name="deleted", nullable=false)
	@JsonIgnore
	private PolarResponseType deleted;
	@OneToMany(mappedBy="courseCategory")
	@JsonIgnore
	private Collection<Course> courses;
	private Date updated_at;
	private Date created_at;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
		
	public PolarResponseType getDeleted() {
		return deleted;
	}
	public void setDeleted(PolarResponseType deleted) {
		this.deleted = deleted;
	}	
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
		
	public Collection<Course> getCourses() {
		return courses;
	}
	
	public void addCourse(Course course) {
		this.courses.add(course);
	}
	
	@Override
	public String toString() {
		return "CourseCategory [id=" + id + ", name=" + name + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CourseCategory other = (CourseCategory) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
	
}
