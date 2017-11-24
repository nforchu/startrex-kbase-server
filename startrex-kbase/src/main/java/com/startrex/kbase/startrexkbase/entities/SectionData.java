package com.startrex.kbase.startrexkbase.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.startrex.kbase.startrexkbase.helpers.enumeration.ContentType;

@Entity
@Table(name="section_data")
public class SectionData {

	@Id
	@GeneratedValue
	@Column(name="section_data_id")
	private int id;
	private short weight;
	@Enumerated(EnumType.STRING)
	private ContentType type;
	@Column(columnDefinition="text")
	private String content;
	
	@ManyToOne
	@JoinColumn(name="topic_section_id")
	private TopicSection topicSection;
	
}
