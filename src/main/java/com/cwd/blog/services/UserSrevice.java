package com.cwd.blog.services;

import java.util.List;

import com.cwd.blog.entities.User;
import com.cwd.blog.payloads.UserDto;

public interface UserSrevice {
	UserDto registerNewUser(UserDto user);
	
	
  UserDto createUser(UserDto user);
  UserDto updateUser(UserDto user, Integer userId);
  UserDto getUserById(Integer userId);
  List<UserDto> getallusers();
  void deleteUser(Integer userId);
  
}
