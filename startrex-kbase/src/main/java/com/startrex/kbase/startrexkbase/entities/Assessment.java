package com.startrex.kbase.startrexkbase.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.startrex.kbase.startrexkbase.helpers.enumeration.StandardObjectStatusType;

@Entity
@Table(name="assessment")
public class Assessment {
	@Id
	@GeneratedValue
	@Column(name="assessment_id")
	private int id;
	@Enumerated(EnumType.STRING)
	private StandardObjectStatusType status;
	@Column(columnDefinition="text COMMENT 'any remarks concerning the assess. e.g guidelines'")
	private String remarks;
	@ManyToOne
	@JoinColumn(name="course_session_id")
	private CourseSession courseSession;
	@ManyToMany
	@JoinTable(name = "assessemnts_questions", joinColumns = @JoinColumn(name = "assessment_id", referencedColumnName = "assessment_id"), inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "question_id"))
	private Collection<Question> questions;
	
}
