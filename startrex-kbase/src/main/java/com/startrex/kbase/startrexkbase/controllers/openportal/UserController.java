package com.startrex.kbase.startrexkbase.controllers.openportal;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.startrex.kbase.startrexkbase.controllers.UserRestController;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;

@Controller
@RequestMapping("openportal/user")
public class UserController { 
	
	@Autowired
	private UserRestController userRestController;
	
	@RequestMapping(method=RequestMethod.GET, value="domains")
	public String getUserDomains(HttpServletRequest request, 
							     Model model,
							     @ModelAttribute("domains") final HashSet<Object> domains) {
		if(domains.size() < 1) {			
			try{
				STResponseComponent sTResponseComponent = 
									userRestController.getUserDomains(request);
				if(sTResponseComponent.responseCode == STResponseCode.OK) {
					model.addAttribute("domains", sTResponseComponent.context.get("domains"));					
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
					
		}
		return "domains";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="domain/courses")
	public String getUserDomainCourses(HttpServletRequest request, 
									   Model model,
									   RedirectAttributes redirectAttributes,
			 						   @ModelAttribute("courses") final HashSet<Object> courses,
			 						   Pageable pageable) {
		if(courses.size() < 1) {			
			try{
				STResponseComponent sTResponseComponent = 
									userRestController.getHomePage(request, pageable);
				
				Map<String, Object> homepage = (HashMap)sTResponseComponent.context.get("homepage");
				if(homepage.get("domains") != null) {				
					
					redirectAttributes.addFlashAttribute("domains", homepage.get("domains"));
					return "redirect:" + "/openportal/user/domains";
				}
				model.addAttribute("courses", homepage.get("courses"));
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
					
		}
		return "courses";
	}

}
