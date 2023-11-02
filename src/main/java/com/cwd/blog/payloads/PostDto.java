package com.cwd.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.events.Comment;

import com.cwd.blog.entities.Category;
import com.cwd.blog.entities.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	
	private CategoryDto category;

	
	private UserDto user;
	
	private Set<CommentDto> comments = new HashSet<>();
}
