package com.spring.blog.services;

import java.util.List;

import com.spring.blog.entities.Post;
import com.spring.blog.payloads.PostDto;
import com.spring.blog.payloads.PostResponse;

public interface PostService {

	//create 
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update 
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postId);

	
	
	//get single post
	PostDto getPostById(Integer postId);
	
	
	
	//get All post
	PostResponse getAllPosts(Integer pageNumber,Integer pageSizer,String sortBy,String sortDir);
	
	
	
	//get all post by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	
	//get ALL posts by user
	
	List<PostDto> getPostsbyUser(Integer userId);
	
	//search posts
	
	List<PostDto> searchPosts(String keyword);
	
}
