package com.startrex.kbase.startrexkbase.controllers.openportal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.startrex.kbase.startrexkbase.controllers.TopicRestController;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;

@Controller
@RequestMapping("openportal/chapter")
public class ChapterController {
	
	@Autowired
	private TopicRestController topicRestController;
	
	@RequestMapping(method=RequestMethod.GET, value="{id}/{title}")
	public String get(HttpServletRequest request,
					  Model model,
					  @PathVariable(value="id") String id,
					  @PathVariable(value="title") String title) {
		try {
			STResponseComponent sTResponseComponent = topicRestController.get(request, id);
			if(sTResponseComponent.responseCode == STResponseCode.OK) {
				model.addAttribute("chapter", sTResponseComponent.context.get("topic"));
				model.addAttribute("chapters", sTResponseComponent.context.get("topics"));				
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return "chapter";		
	}

}
