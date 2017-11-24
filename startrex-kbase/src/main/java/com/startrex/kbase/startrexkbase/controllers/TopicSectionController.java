package com.startrex.kbase.startrexkbase.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.startrex.kbase.startrexkbase.entities.TopicSection;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;

@RestController
@RequestMapping("section")
public class TopicSectionController {
	@RequestMapping(method=RequestMethod.GET, value="{id}")
	public STResponseComponent get() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		return STResponseComponent.factory(map, "Retrival success");	
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public STResponseComponent add(@RequestBody TopicSection section) {
		
		return STResponseComponent.factory();
		
	}
	
	@RequestMapping(method=RequestMethod.PATCH)
	public STResponseComponent update(@RequestBody TopicSection section) {
		
		return STResponseComponent.factory();
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public STResponseComponent remove(@RequestBody TopicSection section) {
		
		return STResponseComponent.factory();
	}
	
	
}
