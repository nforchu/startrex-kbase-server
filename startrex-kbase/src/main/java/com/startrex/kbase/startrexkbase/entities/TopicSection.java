package com.startrex.kbase.startrexkbase.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="topic_section")
public class TopicSection {
	
	@Id
	@GeneratedValue
	@Column(name="topic_section_id")
	private int id;
	private String title;
	@ManyToOne
	@JoinColumn(name="topic_id")
	private Topic topic;
	@OneToMany(mappedBy="topicSection")
	private Collection<SectionData> sectionData;

}
