package com.startrex.kbase.startrexkbase.entities;

import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.startrex.kbase.startrexkbase.helpers.enumeration.DomainMemberType;

@Entity
@Table(name="domain_members")
public class DomainMember {

	@Id
	@GeneratedValue
	@Column(name="domain_member_id")
	private int id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="member_type")
	private DomainMemberType memberType;
	
	@ManyToOne
	@JoinColumn(name="domain_id")
	@JsonIgnore
	private Domain domain;	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;
	
	@ManyToMany(mappedBy="domainMembers")
	@JsonIgnore
	private Set<DomainRole> domainRoles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DomainMemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(DomainMemberType memberType) {
		this.memberType = memberType;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<DomainRole> getDomainRoles() {
		return domainRoles;
	}

	public void setDomainRoles(Set<DomainRole> domainRoles) {
		this.domainRoles = domainRoles;
	}
	
	
	
	
}
