package com.leon.blog.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.leon.blog.interfaces.LightPost;
import com.leon.blog.model.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{
	
	//This will find code, title, description and date
	Iterable<LightPost> findByOrderByDateDesc();
	
	
	//This will find code, title, description and date : same with method above
	@Query(value= "{}", fields="{code:1, title:1, description:1, date:1}", sort="{date:-1}" )
	Iterable<LightPost> findCodeTitleDescriptionAndDate();
	
	
	Optional<Post> findByCode(String code);
	
	void deleteByCode(String code);
}
