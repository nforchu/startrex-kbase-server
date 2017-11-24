package com.startrex.kbase.startrexkbase.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.startrex.kbase.startrexkbase.entities.Course;
import com.startrex.kbase.startrexkbase.entities.Topic;



@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer>{
	Optional<Topic> findOneById(int id);
	Page<Topic> findByCourse(Course course, Pageable pageable);
	@Query("SELECT t.id, t.title FROM Topic t WHERE t.deleted = 'NO' ORDER BY weight")
	Page<Topic> getTopicList(Pageable pageable); 
	Optional<Topic> findTop1ByCourseIdOrderByWeightAsc(int courseId);
}
