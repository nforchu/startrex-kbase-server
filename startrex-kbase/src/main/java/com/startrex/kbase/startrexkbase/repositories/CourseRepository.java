package com.startrex.kbase.startrexkbase.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.startrex.kbase.startrexkbase.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{
	Optional<Course> findOneById(int id);
	Page<Course>  findByDomainId(int domainId, Pageable pageable);
	@Query("SELECT c.id, c.title FROM Course c WHERE c.deleted = 'NO'")
	Page<Course> getCourseList(Pageable pageable); 
	Page<Course> findAll(Pageable pageable);
}
