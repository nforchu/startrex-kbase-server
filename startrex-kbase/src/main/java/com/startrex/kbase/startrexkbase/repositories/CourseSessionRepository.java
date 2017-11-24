package com.startrex.kbase.startrexkbase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.startrex.kbase.startrexkbase.entities.CourseSession;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSession, Integer>{

}
