package com.leon.blog.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

public class ContentBlock {
	
	private String title;
	
	@Field("contents")
	private List<Content> contents;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contens) {
		this.contents = contens;
	}
	
	
}
