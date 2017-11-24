package com.startrex.kbase.startrexkbase.controllers.openportal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.startrex.kbase.startrexkbase.controllers.AuthenticationRestController;
import com.startrex.kbase.startrexkbase.dto.AuthUserDTO;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;
import com.startrex.kbase.startrexkbase.services.JwtAuthenticationResponse;

@Controller
@RequestMapping("openportal")
public class AuthenticationController {
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	AuthenticationRestController authRestController;

	@RequestMapping(method=RequestMethod.GET, value="login")
	public String getLoginForm(HttpServletRequest request) {
		if(request.getSession(false) == null){
			return "login";
		}
		
		return "redirect:" + "/openportal/course/all";
		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			if(request.getSession(false)  != null){	
				request.getSession().invalidate();
				for(Cookie cookie: request.getCookies()) {
		        	if(cookie.getName().equals("STKBTokenCookie")) {
		        		cookie.setPath("/");
		        		cookie.setMaxAge(0);
		        		response.addCookie(cookie);
		        	}		        	
			    }
			}			
			
		}catch(IllegalStateException ex) {
			logger.info("trying to invalidate a non valid session");
			ex.printStackTrace();			
		}		
		return "redirect:" + "/openportal/login"; 
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="login")
	public String validateCredentials(HttpServletRequest request,
									  HttpServletResponse response, 
									  Model model, 
									  RedirectAttributes redirectAttributes) {
		AuthUserDTO authUserDTO = AuthUserDTO.factory();
		authUserDTO.setUsername(request.getParameter("username"));
		authUserDTO.setPassword(request.getParameter("password").toString());
		
		STResponseComponent stResponseComponent = authRestController.login(authUserDTO);
		if(stResponseComponent.responseCode == STResponseCode.OK) {
			JwtAuthenticationResponse tokenResponse = 
					(JwtAuthenticationResponse)stResponseComponent.context.get("tokenAccessor");
			
			request.getSession(true)
			       .setAttribute("connectedUser", 
			    		         stResponseComponent.context.get("user"));
			request.getSession(false).setAttribute("STKBUserToken", tokenResponse.getToken());
			System.out.println("home page is " + stResponseComponent.context.get("homepage"));
			/*Cookie STKBTokenCookie  =new Cookie("STKBTokenCookie", tokenResponse.getToken());
			STKBTokenCookie.setMaxAge(600);
			STKBTokenCookie.setPath("/");
			response.addCookie(STKBTokenCookie);*/
			Map<String, Object> homepage = (HashMap)stResponseComponent.context.get("homepage");
			if(homepage.get("domains") != null) {				
				
				redirectAttributes.addFlashAttribute("domains", homepage.get("domains"));
				return "redirect:" + "/openportal/user/domains";
			}else if(homepage.get("courses") != null) {	
				
				redirectAttributes.addFlashAttribute("courses", homepage.get("courses"));
				return "redirect:" + "/openportal/user/domain/courses";				
			}
			return "redirect:" + "/openportal/course/all";	
			
		}
		model.addAttribute("loginErrorMessage", "Invalid username or password");
		return "login";
	}
}
