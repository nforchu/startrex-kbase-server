package com.startrex.kbase.startrexkbase.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="domain_roles")
public class DomainRole { 

	@Id
	@GeneratedValue
	@Column(name="domain_role_id")
	private int id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@ManyToMany	
	@JoinTable(name = "domain_controls", 
			   joinColumns = @JoinColumn(name = "domain_role_id", referencedColumnName = "domain_role_id"), 
			   inverseJoinColumns = @JoinColumn(name = "domain_member_id", referencedColumnName = "domain_member_id"))
	@JsonIgnore
	private Set<DomainMember> domainMembers;

	public int getId() { 
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<DomainMember> getDomainMembers() {
		return domainMembers;
	}

	public void setDomainMembers(Set<DomainMember> domainMembers) {
		this.domainMembers = domainMembers;
	}
		
			
	
}
