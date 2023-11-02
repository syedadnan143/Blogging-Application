package com.cwd.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.cwd.blog.entities.User;
import java.util.List;


public interface UserRepo extends JpaRepository<User, Integer>  {
  
	 Optional<User>findByEmail(String email);
}
