package com.leon.blog.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "posts")
public class Post {

	@Id
	private String id;

    @Indexed(unique = true)
	private String code;

	private String title;

	@Field("contenu")
	private String description;
	private Date date;
	
	private List<ContentBlock> contentBlock;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<ContentBlock> getContentBlock() {
		return contentBlock;
	}

	public void setContentBlock(List<ContentBlock> contentBlocks) {
		this.contentBlock = contentBlocks;
	}

	
}
