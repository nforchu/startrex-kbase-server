package com.startrex.kbase.startrexkbase.entities;

import java.util.Collection; 
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startrex.kbase.startrexkbase.helpers.enumeration.PolarResponseType;



@Entity
@Table(name="domains")
@Where(clause="deleted='NO'")
public class Domain {

	@Id
	@GeneratedValue
	@Column(name="domain_id")
	private int id;
	
	@OneToMany(mappedBy="domain")
	@JsonIgnore
	private List<Course> courses;
	
	private String name;
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private PolarResponseType deleted;
	
	private Date created_at;
	
	private Date updated_at;
	
	@ManyToMany	
	@JoinTable(name = "domains_admins", joinColumns = @JoinColumn(name = "domain_id", referencedColumnName = "domain_id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
	@JsonIgnore
	private Collection<User> admins;
	
	@OneToMany(mappedBy="domain")
	@JsonIgnore
	private Set<DomainMember> domainMembers;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
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
		
	public Set<DomainMember> getDomainMembers() {
		return domainMembers;
	}

	public void setDomainMembers(Set<DomainMember> domainMembers) {
		this.domainMembers = domainMembers;
	}

	@Override
	public String toString() {
		return "Domain [id=" + id + ", name=" + name + "]";
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
		Domain other = (Domain) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public static Domain factory() {
		return new Domain();
	}
	
	
}
