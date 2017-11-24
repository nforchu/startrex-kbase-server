package com.startrex.kbase.startrexkbase.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startrex.kbase.startrexkbase.entities.Assessment;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.repositories.AssessmentRepository;

@Service
public class AssessmentDAO {

	@Autowired
	private AssessmentRepository assRep;
	
	public DBResponseComponent get(int id) {
		
		return DBResponseComponent.factory();
	}
	
	public DBResponseComponent getAllBySession(int sessionId) {
		
		return DBResponseComponent.factory();
	}
	
	public DBResponseComponent save(Assessment ass) {
		
		return DBResponseComponent.factory();
	}
	
	public DBResponseComponent remove(int id) {
		
		assRep.delete(id);
		return DBResponseComponent.factory();
	}
	
}
