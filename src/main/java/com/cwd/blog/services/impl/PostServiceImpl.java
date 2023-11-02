package com.cwd.blog.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
//import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
	
import com.cwd.blog.entities.Category;
import com.cwd.blog.entities.Post;
import com.cwd.blog.entities.User;
import com.cwd.blog.exceptions.ResoueceNotFountException;
import com.cwd.blog.payloads.PostDto;
import com.cwd.blog.payloads.PostResponse;
import com.cwd.blog.repositories.CategoryRepo;
import com.cwd.blog.repositories.PostRepo;
import com.cwd.blog.repositories.UserRepo;
import com.cwd.blog.services.PostService;
@Service
public class PostServiceImpl implements PostService {
     @Autowired
	private PostRepo postRepo;
     @Autowired
     private ModelMapper modelMapper;
     @Autowired
     private UserRepo userRepo;
     @Autowired
     private CategoryRepo categoryRepo;
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()->
		new ResoueceNotFountException("User","User id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->
		new ResoueceNotFountException("Category", "category id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResoueceNotFountException("post", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(post.getImageName());
		
		Post updatedPost =  this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResoueceNotFountException("post", "post id", postId));
        this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Sort sort  = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort= sort.by(sortBy).ascending();
		}
		else {
			sort = sort.by(sortBy).descending();
			
		}

		 Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post>pagePost= this.postRepo.findAll(p);
		List<Post> allPosts =  pagePost.getContent();
		
		List<PostDto>postDtos = allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalpages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(()->
		new ResoueceNotFountException("post", "post id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->
		new ResoueceNotFountException("Category", "category id", categoryId));
		List<Post>posts =  this.postRepo.findByCategory(cat);
		
		// here converting posts to post Dto
		
	 List<PostDto>postDtos =posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		// TODO Auto-generated method stub
		User user =  this.userRepo.findById(userId).orElseThrow(()->
		new ResoueceNotFountException("User", "user id", userId));
		List<Post>posts = this.postRepo.findByUser(user);
		   
		
	   List<PostDto>postDtos =posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		   List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		   List<PostDto>postDtos =posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}



}
