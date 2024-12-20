package com.leon.blog.interfaces;

import java.util.Optional;

import com.leon.blog.model.Comment;
import com.leon.blog.model.Post;

public interface PostInterface {

	public Optional<Post> getPost(String code);
	
	public Post createPost(Post post);
	
	public void deletePost(String code);

	public Iterable<LightPost> getPosts();
	
	public Post updatePost(String code, Post post);

	public Comment addCommentToPost(String code, Comment comment);
}
