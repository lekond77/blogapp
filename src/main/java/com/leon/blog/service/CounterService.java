package com.leon.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.leon.blog.model.Counter;

@Service
public class CounterService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public String getNextSequenceId() {
		
		Query query = new Query(Criteria.where("_id").is("postId"));
		Update update = new Update().inc("seq", 1);
		
		Counter counter = mongoTemplate.findAndModify(query, update, Counter.class);
		
		return String.format("%06d", counter.getSeq());
	}
}
