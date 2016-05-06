package com.example.bugrap.model;

import java.io.Serializable;

/**
 * 
 * @author nikolaigorokhov
 *
 */
public class Version implements Serializable, Comparable<Version> {
	private int id;
	private Project project;
	private String name;
	
	public Version() {
		
	}
	
	public Version(int id, Project project, String name) {
		this.id = id;
		this.project = project;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Version && ((Version)obj).getId() == id;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Version o) {
		return this.name.compareTo(o.name);
	}
}
