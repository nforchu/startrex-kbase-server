package com.startrex.kbase.startrexkbase.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startrex.kbase.startrexkbase.entities.Answer;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.repositories.AnswerRepository;

@Service
public class AnswerDAO {

	@Autowired
	private AnswerRepository answerRep;
	
	public DBResponseComponent get(int id) {
		
		return DBResponseComponent.factory();
	}
	
	public DBResponseComponent getAllByQuestion(int questionId) {
		
		return DBResponseComponent.factory();
	}
	
	public DBResponseComponent save(Answer answer) {
		
		return DBResponseComponent.factory();
	}
	
	public DBResponseComponent remove(int id) {
		
		answerRep.delete(id);
		return DBResponseComponent.factory();
	}
}
