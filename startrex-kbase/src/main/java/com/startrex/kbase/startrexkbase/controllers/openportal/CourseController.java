package com.startrex.kbase.startrexkbase.controllers.openportal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.startrex.kbase.startrexkbase.controllers.CourseRestController;
import com.startrex.kbase.startrexkbase.controllers.TopicRestController;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;

@Controller
@RequestMapping("openportal/course")
public class CourseController {
	
	@Autowired
	CourseRestController courseRestController;
	
	@Autowired
	TopicRestController topicRestController;

	@RequestMapping(method=RequestMethod.GET, value= "{id}")
	public String getCourse(HttpServletRequest request, 
							Model model,
							Pageable pageable,
							@PathVariable(value="id") String id) {
		
		try {
			STResponseComponent sTResponseComponent = courseRestController.getInViewMode(request, pageable, id);
			if(sTResponseComponent.responseCode == STResponseCode.OK) {
				model.addAttribute("course", sTResponseComponent.context.get("course"));
				model.addAttribute("courses", sTResponseComponent.context.get("courses"));
				System.out.println("wow  " +  sTResponseComponent.context.get("courses"));
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return "course";
	}
	
	@RequestMapping(method=RequestMethod.GET, value= "{id}/content")
	public String getCourseContent(HttpServletRequest request, 
							Model model,
							Pageable pageable,
							@PathVariable(value="id") String id) {
		
		try {
			STResponseComponent sTResponseComponent = topicRestController.getFirstByCourse(request, id);
			if(sTResponseComponent.responseCode == STResponseCode.OK) {
				model.addAttribute("chapter", sTResponseComponent.context.get("topic"));
				model.addAttribute("chapters", sTResponseComponent.context.get("topics"));				
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return "chapter";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="all")
	public String getAll(HttpServletRequest request, 
							  Model model,
		                      Pageable pageable) {
		try {
			STResponseComponent responseMap = courseRestController.getAll(pageable);			
			model.addAttribute("courses", responseMap.context.get("courses"));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return "courses"; 
	}

}
