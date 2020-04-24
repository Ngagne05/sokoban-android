package com.example.androidapp2;

public class Level {
	int[] array;
	String text;
	boolean completed;
	public Level(int[] array, String text, boolean completed) {
		super();
		this.array = array;
		this.text = text;
		this.completed = completed;
	}
	public int[] getArray() {
		return array;
	}
	public void setArray(int[] array) {
		this.array = array;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	
}
