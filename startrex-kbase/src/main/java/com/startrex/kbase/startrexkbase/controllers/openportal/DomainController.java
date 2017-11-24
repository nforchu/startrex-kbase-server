package com.startrex.kbase.startrexkbase.controllers.openportal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.startrex.kbase.startrexkbase.controllers.DomainRestController;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;

@Controller
@RequestMapping("openportal/domain")
public class DomainController {
	@Autowired
	private DomainRestController domainRestController;
	
	@RequestMapping(method=RequestMethod.GET, value="{domainId}/courses")
	public String getCourses(HttpServletRequest request, 
							 Model model,
							 Pageable pageable,
							 @PathVariable(value="domainId") String domainId) {
		try {
			STResponseComponent sTResponseComponent = 
								domainRestController.getCourses(domainId, pageable, request);
			if(sTResponseComponent.responseCode == STResponseCode.OK) {
				model.addAttribute("courses", sTResponseComponent.context.get("courses"));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return "courses";
	}

}
