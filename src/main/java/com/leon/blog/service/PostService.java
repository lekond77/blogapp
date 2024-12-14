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
	
	@Autowired
	private CounterService counterService;
	
	@Override
	public Optional<Post> getPost(String code) {
		return postRepository.findByCode(code);
	}

	@Override
	public Post createPost(Post post) {	
		post.setCode(this.counterService.getNextSequenceId());
		return postRepository.insert(post);
	}

	@Override
	public void deletePost(String code) {
		postRepository.deleteByCode(code);
	}

	@Override
	public Iterable<Post> getPosts() {
		return postRepository.findAll();
	}

	@Override
	public Post updatePost(String code, Post post) {
		
		Post updatedPost = postRepository.findByCode(code).orElse(null);
		
		if (updatedPost != null) {

			if (post.getTitle() != null) {
				updatedPost.setTitle(post.getTitle());
			}

			if (post.getDescription() != null) {
				updatedPost.setContent(post.getDescription());
			}
			
			if(post.getContentBlock() != null) {
				updatedPost.setContentBlock(post.getContentBlock());
			}
			return postRepository.save(updatedPost);
		}
		return post;
	}

}
