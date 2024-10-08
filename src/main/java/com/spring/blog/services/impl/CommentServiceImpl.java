package com.spring.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.blog.entities.Comment;
import com.spring.blog.entities.Post;
import com.spring.blog.exceptions.ResourceNotFoundException;
import com.spring.blog.payloads.CommentDto;
import com.spring.blog.repositories.CommentRepo;
import com.spring.blog.repositories.PostRepo;
import com.spring.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {



	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		
		
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		
		Comment comment = this.mapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment saveComment = this.commentRepo.save(comment);
		
		
		return this.mapper.map(saveComment, CommentDto.class);
		
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id",commentId));
		
		this.commentRepo.delete(comment);

	}

}
