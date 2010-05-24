package com.google.code.microlog4android.repository;


public abstract class AbstractRepositoryNode {
	protected String name;
	
	protected AbstractRepositoryNode() {
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
