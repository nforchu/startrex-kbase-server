package com.startrex.kbase.startrexkbase.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.startrex.kbase.startrexkbase.entities.Question;
import com.startrex.kbase.startrexkbase.entities.Topic;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
	Optional<Question> findOneById(int id);
	Page<Question> findByTopicId(int id, Pageable pageable); 
	@Query("SELECT q.id, q.question FROM Question q WHERE q.deleted = 'NO' AND q.topic =1")
	Page<Question> getQuestionList(Pageable pageable);
	Optional<Question> findTop1ByTopicId(int courseId); 
}
