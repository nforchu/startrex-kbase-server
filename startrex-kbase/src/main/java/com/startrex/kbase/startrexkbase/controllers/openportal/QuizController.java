package com.startrex.kbase.startrexkbase.controllers.openportal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("openportal/quiz")
public class QuizController {
	
	@RequestMapping(method=RequestMethod.GET, value="get/{topicId}")
	public String getQuiz(HttpServletRequest request,
			              @PathVariable(value="topicId") String topicId,
			              Model model) {
		
		
		
		return "quiz";
	}

}
