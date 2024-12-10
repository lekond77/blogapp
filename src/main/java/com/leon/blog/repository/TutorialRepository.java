package com.leon.blog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leon.blog.model.Tutorial;

@Repository
public interface TutorialRepository extends MongoRepository<Tutorial, String>{

}
