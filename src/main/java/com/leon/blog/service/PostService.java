package com.leon.blog.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leon.blog.interfaces.LightPost;
import com.leon.blog.interfaces.PostInterface;
import com.leon.blog.model.Comment;
import com.leon.blog.model.Post;
import com.leon.blog.repository.PostRepository;

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
	public Iterable<LightPost> getPosts() {
		return postRepository.findByOrderByDateDesc();
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

	@Override
	public Comment addCommentToPost(String code, Comment comment) {
		
		Post updatedPost = postRepository.findByCode(code).orElse(null);
		
		if(updatedPost != null) {
			comment.setDate(new Date());
			
			updatedPost.getComments().add(comment);
			postRepository.save(updatedPost);
			
			return comment;
		}
		return null;
		
	}

}
