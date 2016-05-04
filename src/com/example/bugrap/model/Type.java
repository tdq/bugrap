package com.example.bugrap.model;

import java.io.Serializable;

public class Type implements Serializable {
	private int id;
	private String name;
	
	public Type() {
		
	}
	
	public Type(int id, String name) {
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
		return obj instanceof Type && ((Type)obj).getId() == id;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
