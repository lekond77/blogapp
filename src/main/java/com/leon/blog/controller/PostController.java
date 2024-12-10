package com.leon.blog.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.leon.blog.model.Post;
import com.leon.blog.serviceInterface.PostInterface;

@RestController
public class PostController {

	@Autowired
	private PostInterface postInterface;

	@GetMapping("/posts")
	public Iterable<Post> getPosts() {

		return postInterface.getPosts();
	}

	@GetMapping("/post/{id}")
	public Post getPost(@PathVariable final String id) {

		Optional<Post> optionalPost = postInterface.getPost(id);
		return optionalPost.isPresent() ? optionalPost.get() : null;
	}

	@PostMapping("/post")
	public Post createPost(@RequestBody Post post) {
		post.setDate(new Date());
		return postInterface.createPost(post);
	}

	@DeleteMapping("/post/{id}")
	public void deletePost(@PathVariable final String id) {
		postInterface.deletePost(id);
	}

	@PutMapping("/post/{id}")
	public Post updatePost(@PathVariable final String id, @RequestBody Post post) {
	
		return postInterface.updatePost(id, post);
	}
}
