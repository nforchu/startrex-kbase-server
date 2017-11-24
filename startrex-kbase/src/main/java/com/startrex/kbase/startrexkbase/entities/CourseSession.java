package com.startrex.kbase.startrexkbase.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.startrex.kbase.startrexkbase.helpers.enumeration.StandardObjectStatusType;

@Entity
@Table(name="course_sessions")
public class CourseSession {
	@Id
	@GeneratedValue
	@Column(name="course_session_id")
	private int id;
	@Column(columnDefinition="text")
	private String remarks;
	@Enumerated(EnumType.STRING)
	private StandardObjectStatusType status;
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;
	@OneToMany(mappedBy="courseSession")
	private Collection<Assessment> assessments;
}
