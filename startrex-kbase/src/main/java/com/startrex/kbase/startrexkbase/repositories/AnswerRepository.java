package com.startrex.kbase.startrexkbase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.startrex.kbase.startrexkbase.entities.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer>{
	//Optional<Answer> findOneById();
	//Collection<Answer> findByQuestionId(int id);
}
