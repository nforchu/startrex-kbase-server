package com.startrex.kbase.startrexkbase.services;

import org.springframework.stereotype.Service;


/**
 * Created by Princewill Nforchu on 04.11.2017
 */
@Service
public class JwtUserDetailsServiceImpl  {

	//private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	/**User user;
    @Autowired 
    private UserRepository userRepository; 
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        this.user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {            
            
            return JwtUserFactory.create(user);
        }
    }
    
    
    Collection<? extends GrantedAuthority> getAuthorities(){
    	Set <GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    	user.getProfile().getRoles().forEach(role -> {
    		authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
    	});
    	return authorities;
    	
    }
    
    
    
    public String getRoles(){
    	StringBuffer rolesString = new StringBuffer();
    
    	user.getProfile().getRoles().forEach(role -> {
    		rolesString.append(":" + role.getName());
    	});
    	return rolesString.toString();
    }*/
    

}
