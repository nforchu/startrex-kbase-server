package com.startrex.kbase.startrexkbase.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.startrex.kbase.startrexkbase.dao.UserDAO;
import com.startrex.kbase.startrexkbase.dto.AuthUserDTO;
import com.startrex.kbase.startrexkbase.entities.User;
import com.startrex.kbase.startrexkbase.helpers.response.DBResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseMessage;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseStatus;
import com.startrex.kbase.startrexkbase.services.JwtAuthenticationResponse;
import com.startrex.kbase.startrexkbase.services.JwtTokenUtil;
import com.startrex.kbase.startrexkbase.services.JwtUserFactory;

@RestController
@RequestMapping("auth")
public class AuthenticationRestController { 
	
    @Value("${jwt.header}")
    private String tokenHeader;
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
    private AuthenticationManager authenticationManager; 
	
	//@Autowired
    //private UserDetailsService userDetailsService;
	
	@Autowired
	private UserDAO userDAO;

	@RequestMapping(method=RequestMethod.GET)
	public String get() {
		return "this is the token";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="check")
	public STResponseComponent login(@RequestBody AuthUserDTO authUserDTO) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		int responseCode = STResponseCode.OK;
		String responseStatus = STResponseStatus.OK;
		String responseMessage = STResponseMessage.SUCCESS;
		try {
			DBResponseComponent dbResponse = 
					userDAO.findByUsernameAndPassword(authUserDTO.getUsername(),
													  authUserDTO.getPassword());
			//UserDetails userDetails = userDetailsService.loadUserByUsername(authUserDTO.getUsername());
			
			if(dbResponse.responseCode == STResponseCode.OK) {
				User user = (User)dbResponse.responseMap.get("user");
				dbResponse = userDAO.
						 getHomepageData(user, user.getId());
				UserDetails userDetails = JwtUserFactory.create(user);
				final Authentication authentication  = 
						new UsernamePasswordAuthenticationToken(authUserDTO.getUsername(), 
																authUserDTO.getPassword());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				final String token = jwtTokenUtil.generateToken(userDetails);
				responseMap.put("user", user);
				responseMap.put("homepage", dbResponse.responseMap.get("data"));
				responseMap.put("tokenAccessor", new JwtAuthenticationResponse(token));
				responseMap.put("responseStatus", STResponseStatus.OK);
			}else {
				responseCode = dbResponse.responseCode;
				responseMap.put("responseStatus", STResponseStatus.NO_OBJECT_FOUND);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}		
		
		
		return STResponseComponent.factory(responseMap, responseStatus, responseCode, responseMessage);	
		//return "this is the token from post query";
	}
	
	

}
