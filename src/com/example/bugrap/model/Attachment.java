package com.example.bugrap.model;

/**
 * 
 * @author nikolaigorokhov
 *
 */
public class Attachment {
	private int id;
	private int commentId;
	private String fileName;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
