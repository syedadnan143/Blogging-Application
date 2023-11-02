package com.cwd.blog.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cwd.blog.config.AppConstants;
import com.cwd.blog.entities.Role;
import com.cwd.blog.entities.User;
import com.cwd.blog.payloads.UserDto;
import com.cwd.blog.repositories.RoleRepo;
import com.cwd.blog.repositories.UserRepo;
import com.cwd.blog.services.UserSrevice;
import com.cwd.blog.exceptions.*;
@Service
public class UserServiceImpl implements UserSrevice {
	@Autowired
  private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.dtoToUser(userDto);
	    User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResoueceNotFountException("User", "Id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updateUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updateUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResoueceNotFountException("User", "Id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getallusers() {
		// TODO Auto-generated method stub
		
	List<User> users =	this.userRepo.findAll();
	 List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
	User user =  this.userRepo.findById(userId).orElseThrow(()-> new ResoueceNotFountException("User", "Id", userId));
    this.userRepo.delete(user);
	}
	
	private User dtoToUser(UserDto userDto) {
		User user =this.modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
		
		
	}
    
	private UserDto userToDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		
		User user = this.modelMapper.map(userDto, User.class);
		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		// roles
		
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}
}
