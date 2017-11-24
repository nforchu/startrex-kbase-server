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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startrex.kbase.startrexkbase.helpers.enumeration.ObjectVisibilityType;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;

@Entity
@Table(name="audiovisuals")
public class Audiovisual {

	@Id
	@GeneratedValue
	@Column(name="audiovisual_id")
	private int id;
	private String title;
	private String link;
	private String provider;
	private short weight;
	private String code;
	@Enumerated(EnumType.STRING)
	private PolarResponseType published;	
	@Enumerated(EnumType.STRING)
	private ObjectVisibilityType visibility;
	@ManyToOne
	@JoinColumn(name="topic_id", updatable=false)
	private Topic topic;
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private PolarResponseType deleted;
	
	
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public short getWeight() {
		return weight;
	}
	public void setWeight(short weight) {
		this.weight = weight;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}	
	
	public PolarResponseType getDeleted() {
		return deleted;
	}
	public void setDeleted(PolarResponseType deleted) {
		this.deleted = deleted;
	}
	
	public static Audiovisual factory() {
		return new Audiovisual();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
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
		Audiovisual other = (Audiovisual) obj;
		if (id != other.id)
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Audiovisual [id=" + id + ", title=" + title + ", link=" + link + "]";
	}
	
	
}
