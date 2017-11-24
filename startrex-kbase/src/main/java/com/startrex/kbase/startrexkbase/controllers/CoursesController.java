package com.startrex.kbase.startrexkbase.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;

@RestController
@RequestMapping("courses")
public class CoursesController {
	@RequestMapping(method=RequestMethod.GET)
	public STResponseComponent get() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("courses", new ArrayList());
		return STResponseComponent.factory(map, "Retrival success");	
	}
}
