package com.leon.blog.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leon.blog.enumeration.ContentType;
import com.leon.blog.interfaces.LightPost;
import com.leon.blog.interfaces.PostInterface;
import com.leon.blog.model.Comment;
import com.leon.blog.model.Content;
import com.leon.blog.model.ContentBlock;
import com.leon.blog.model.Post;
import com.leon.blog.service.FileService;

@RestController
public class PostController {

	@Autowired
	private PostInterface postInterface;

	@Autowired
	FileService fileService;

	@GetMapping("/posts")
	public Iterable<LightPost> getPosts() {

		return postInterface.getPosts();
	}

	@GetMapping("/post/{code}")
	public Post getPost(@PathVariable final String code) {

		Optional<Post> optionalPost = postInterface.getPost(code);
		return optionalPost.isPresent() ? optionalPost.get() : null;
	}

	@PostMapping("/post")
	public Post createPost(@RequestPart("post") Post post,
			@RequestPart(value = "files", required = false) MultipartFile[] files,
			@RequestPart(value = "fileIndicesJson", required = false) String fileIndicesJson) {
		post.setDate(new Date());

		int[] fileIndices = parseJsonFile(fileIndicesJson);

		if (fileIndices.length > 0) {

			this.storeFile(post, files, fileIndices);
		}

		return postInterface.createPost(post);
	}

	@DeleteMapping("/post/{code}")
	public void deletePost(@PathVariable final String code) {
		postInterface.deletePost(code);
	}

	@PutMapping(value = "/post/{code}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Post updatePost(@PathVariable final String code, @RequestPart("post") Post post,
			@RequestPart(value = "files", required = false) MultipartFile[] files,
			@RequestPart(value = "fileIndicesJson", required = false) String fileIndicesJson) {

		int[] fileIndices = parseJsonFile(fileIndicesJson);

		if (fileIndices.length > 0) {

			this.storeFile(post, files, fileIndices);
		}

		return postInterface.updatePost(code, post);
	}
	
	@PutMapping("/post/comment/{code}")
	public Comment addCommentToPost(@PathVariable String code, @RequestBody Comment comment) {
		if(comment != null) {
			return postInterface.addCommentToPost(code, comment);
		}
		return null;
	}

	@GetMapping("/files/{fileName:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
		Resource file = fileService.loadFileAsResource(fileName);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream")).body(file);
	}

	private void storeFile(Post post, MultipartFile[] files, int[] fileIndices) {

		if (files != null && files.length > 0) {
			int fileIndex = 0;

			List<ContentBlock> contentBlocks = post.getContentBlock();

			if (!contentBlocks.isEmpty()) {
				for (ContentBlock contentBlock : contentBlocks) {
					List<Content> contents = contentBlock.getContents();

					for (int i = 0; i < contents.size(); i++) {
						Content content = contents.get(i);
						if (content.getType() == ContentType.image && fileIndex < files.length) {
							if (fileIndices[fileIndex] == i) {
								MultipartFile file = files[fileIndex++];

								String path = fileService.storeFile(file);
								content.setValue(path);
							}
						}
					}
				}
			}
		}
	}

	private int[] parseJsonFile(String fileIndicesJson) {
		ObjectMapper objectMapper = new ObjectMapper();
		int[] fileIndices = {};

		if (fileIndicesJson != null) {
			try {
				fileIndices = objectMapper.readValue(fileIndicesJson, int[].class);
			} catch (IOException ex) {
				throw new RuntimeException("Failed to parse file indices json");
			}
		}
		return fileIndices;
	}

}
