package com.startrex.kbase.startrexkbase.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.startrex.kbase.startrexkbase.entities.Domain;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Integer>{
	Optional<Domain> findOneById(int id);
	//Page<Domain> findBydomainMemberId(Pageable pageable, long userId);
	Page<Domain> findAll(Pageable pageable);
}
