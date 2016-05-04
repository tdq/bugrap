package com.example.bugrap.model;

import java.io.Serializable;

/**
 * 
 * @author nikolaigorokhov
 *
 */
public class Task implements Serializable {
	private int id;
	private Version version;
	private int priority;
	private Type type;
	private String summary;
	private User user;
	private String lastModified;
	private String reported;
	private Status status;
	private String comments;
	private Project project;
	
	/**
	 * 
	 */
	public Task() {
		
	}
	
	/**
	 * 
	 * @param id
	 * @param version
	 * @param priority
	 * @param type
	 * @param summary
	 * @param user
	 * @param lastModified
	 * @param reported
	 */
	public Task(int id, 
				Version version, 
				int priority, 
				Type type, 
				String summary, 
				User user, 
				String lastModified, 
				String reported) {
		this.id = id;
		this.version = version;
		this.priority = priority;
		this.type = type;
		this.summary = summary;
		this.user = user;
		this.lastModified = lastModified;
		this.reported = reported;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getReported() {
		return reported;
	}

	public void setReported(String reported) {
		this.reported = reported;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Task && ((Task)obj).getId() == id;
	}
}
