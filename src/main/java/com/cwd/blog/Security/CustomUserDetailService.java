package com.cwd.blog.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cwd.blog.entities.User;
import com.cwd.blog.exceptions.ResoueceNotFountException;
import com.cwd.blog.repositories.UserRepo;
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
	private UserRepo userRepo;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user from database by username
		User user =this.userRepo.findByEmail(username).orElseThrow(()->
		new ResoueceNotFountException("user", "email: "+username, 0));
		
		
		
		return user;
	}

}
