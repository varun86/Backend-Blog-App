package com.spring.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
