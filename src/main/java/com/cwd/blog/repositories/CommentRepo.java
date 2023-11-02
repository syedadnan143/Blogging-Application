package com.cwd.blog.repositories;

import com.cwd.blog.entities.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
