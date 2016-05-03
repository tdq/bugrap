package com.example.bugrap.model;

public class Status {
	private int id;
	private String name;
	
	public static final int CLOSED = 2;
	
	public Status(int id, String name) {
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
		return obj instanceof Status && ((Status)obj).getId() == id;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
