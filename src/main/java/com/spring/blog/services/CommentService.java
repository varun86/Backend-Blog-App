package com.spring.blog.services;

import com.spring.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto  commentDto,Integer postId );
	
	void deleteComment(Integer commentId);
	
	
}
