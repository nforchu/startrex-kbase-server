package com.startrex.kbase.startrexkbase.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.startrex.kbase.startrexkbase.entities.CourseSession;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.repositories.CourseSessionRepository;

@Service
public class CourseSessionDAO {
	
	@Autowired
	private CourseSessionRepository csRep;
	
	public DBResponseComponent get(int id) {
		
		return DBResponseComponent.factory();
	}
	
	public DBResponseComponent getAllByCourse(int courseId) {
		
		return DBResponseComponent.factory();
	}
	
	public DBResponseComponent save(CourseSession session) {
		
		return DBResponseComponent.factory();
	}
	
	public DBResponseComponent remove(int id) {
		
		csRep.delete(id);
		return DBResponseComponent.factory();
	}
}
