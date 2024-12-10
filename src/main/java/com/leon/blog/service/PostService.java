package com.leon.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leon.blog.model.Post;
import com.leon.blog.repository.PostRepository;
import com.leon.blog.serviceInterface.PostInterface;

@Service
public class PostService implements PostInterface{

	@Autowired
	private PostRepository postRepository;
	
	@Override
	public Optional<Post> getPost(String id) {
		return postRepository.findById(id);
	}

	@Override
	public Post createPost(Post post) {	
		return postRepository.insert(post);
	}

	@Override
	public void deletePost(String id) {
		postRepository.deleteById(id);
	}

	@Override
	public Iterable<Post> getPosts() {
		return postRepository.findAll();
	}

	@Override
	public Post updatePost(String id, Post post) {
		
		Post updatedPost = postRepository.findById(id).orElse(null);
		
		if (updatedPost != null) {

			if (post.getName() != null) {
				updatedPost.setName(post.getName());
			}

			if (post.getContent() != null) {
				updatedPost.setContent(post.getContent());
			}
			return postRepository.save(updatedPost);
		}
		return post;
	}

}
