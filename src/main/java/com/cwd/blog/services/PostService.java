package com.cwd.blog.services;

import java.util.List;

import com.cwd.blog.entities.Post;
import com.cwd.blog.payloads.PostDto;
import com.cwd.blog.payloads.PostResponse;

public interface PostService {
     
	 // create
	
	PostDto createPost(PostDto postDto, Integer userId,Integer categoryId);
	
	// update
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// delete
	
	void deletePost(Integer postId);
	
	// get all post
	
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	// get Single post 
	
	PostDto getPostById(Integer postId);
	
	// get all post by category
	
	List<PostDto>getPostsByCategory(Integer categoryId);
	
	// get all post by user
	
	List<PostDto>getPostsByUser(Integer userId);
	
	// search posts
	List<PostDto> searchPosts(String keyword);
}
