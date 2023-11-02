package com.cwd.blog.services.impl;

import com.cwd.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwd.blog.entities.Post;
import com.cwd.blog.exceptions.ResoueceNotFountException;
import com.cwd.blog.payloads.CommentDto;
import com.cwd.blog.repositories.CommentRepo;
import com.cwd.blog.repositories.PostRepo;
import com.cwd.blog.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
	    Post post = this.postRepo.findById(postId).orElseThrow(() ->
	        new ResoueceNotFountException("Post", "post id", postId));

	    Comment comment = this.modelMapper.map(commentDto, Comment.class);
	    comment.setPost(post);


	    // Save the comment and store the result in a variable
	    Comment savedComment =  this.commentRepo.save(comment);

	    return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
	com.cwd.blog.entities.Comment com =this.commentRepo.findById(commentId).orElseThrow(()->
	       new ResoueceNotFountException("Comment", "CommentId", commentId));
       this.commentRepo.delete(com);
	}

}
