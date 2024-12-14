package com.leon.blog.model;

import org.springframework.web.multipart.MultipartFile;

import com.leon.blog.enumeration.ContentType;

public class Content {
	
	private ContentType type;
	private String value;
	
	public ContentType getType() {
		return type;
	}
	
	public void setType(ContentType type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
