package com.example.bugrap.model;

/**
 * 
 * @author nikolaigorokhov
 *
 */
public class Project {
	private int id;
	private String name;
	
	public Project(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Project && ((Project)obj).getId() == id;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
