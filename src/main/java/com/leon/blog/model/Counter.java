package com.leon.blog.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "counter")
public class Counter {
	private String id;
	private int seq;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}

}
