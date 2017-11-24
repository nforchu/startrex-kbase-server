package com.startrex.kbase.startrexkbase.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.startrex.kbase.startrexkbase.entities.User;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    //@Autowired
    //private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestHeader = request.getHeader(this.tokenHeader);
  
      
        /*if(request.getSession(false) != null){
        	System.out.println("There is a valid session");
        }else {
        	System.out.println("NO SESSSION EXISTS");
        }*/
        if(requestHeader == null && request.getSession(false) !=null && request.getSession(false).getAttribute("STKBUserToken") != null) {
        	System.out.println(request.getSession(false).getAttribute("STKBUserToken").toString());
        	requestHeader = "Bearer " + request.getSession(false).getAttribute("STKBUserToken").toString();
        }
        Map<String, String> credentials = new HashMap<String, String>();
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        String username = null;
        String rolesString    = null;
        String authToken = null;
        String userId = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
        	
            authToken = requestHeader.substring(7);
            try {
              
                credentials = jwtTokenUtil.getCredentialsFromToken(authToken);
                userId = credentials.get("id");
                username = credentials.get("username");
                rolesString = credentials.get("roles");
                               
                String[] rolesArray = rolesString.split(":");
                
                for(String role: rolesArray) {              
                	authorities.add(new SimpleGrantedAuthority(role));
                }
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
                e.printStackTrace();
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }

        logger.info("checking authentication for user " + username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	
            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            //UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        	User user = User.factory();   
        	user.setUsername(username);
            
            //authorities.forEach(auth->System.out.println(auth));
            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
            // the database compellingly. Again it's up to you ;) 
            if (jwtTokenUtil.validateToken(authToken, username)) {
            	
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //HttpServletRequest req = (HttpServletRequest) request;
        //MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(req);
        //System.out.println("====== mutating request headers++++++");
        /////mutableRequest.putHeader("Authorizatoin", "Bearer " + authToken);
        
        logger.info("User id is " +userId);
        request.setAttribute("userId", userId);        
        chain.doFilter(request, response);
    }
}