package com.cwd.blog.services;

import com.cwd.blog.payloads.CommentDto;

public interface CommentService {
    
	CommentDto createComment(CommentDto commentDto, Integer postId);
	
	void deleteComment(Integer commentId);
}
