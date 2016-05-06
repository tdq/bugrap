package com.example.bugrap.model;

import java.io.Serializable;

public class User implements Serializable, Comparable<User> {
	private int id;
	private String name;
	
	public User() {
		
	}
	
	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return obj instanceof User && ((User)obj).getId() == id;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(User o) {
		return name.compareTo(o.name);
	}
}
