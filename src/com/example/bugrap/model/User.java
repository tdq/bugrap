package com.example.bugrap.model;

public class User {
	private int id;
	private String name;
	
	public User(int id, String name) {
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
		return obj instanceof User && ((User)obj).getId() == id;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
