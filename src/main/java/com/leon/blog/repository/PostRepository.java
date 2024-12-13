package com.leon.blog.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leon.blog.model.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{
	
	Optional<Post> findByCode(String code);
	void deleteByCode(String code);
}
