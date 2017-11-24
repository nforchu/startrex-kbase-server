package com.startrex.kbase.startrexkbase.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.startrex.kbase.startrexkbase.entities.Role;
import com.startrex.kbase.startrexkbase.entities.User;



public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),         
                mapToGrantedAuthorities(user.getProfile().getRoles()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles) {
    	
    	List<Role> authorities = new ArrayList<Role>(roles);
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority("ROLE_"+authority.getName()))
                .collect(Collectors.toList());
        
        
    }
}
