package com.leon.blog.serviceInterface;

import java.util.Optional;

import com.leon.blog.model.Post;

public interface PostInterface {

	public Optional<Post> getPost(String code);
	
	public Post createPost(Post post);
	
	public void deletePost(String code);

	public Iterable<Post> getPosts();
	
	public Post updatePost(String code, Post post);
}
