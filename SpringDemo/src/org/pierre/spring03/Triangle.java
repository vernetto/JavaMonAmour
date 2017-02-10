package org.pierre.spring03;

public class Triangle {
	public Triangle(String type) {
		super();
		this.type = type;
	}
	String type;
	public void draw() {
		System.out.println(getType() + " Triangle here");
	}
	public String getType() {
		return type;
	}

}
