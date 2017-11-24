package com.startrex.kbase.startrexkbase.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.startrex.kbase.startrexkbase.entities.CourseCategory;

@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Integer>{
	
	Optional<CourseCategory> findOneById(int id);
	Page<CourseCategory> findAll(Pageable pageable);
	

}
