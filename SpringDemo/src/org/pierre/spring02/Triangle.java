package org.pierre.spring02;

public class Triangle {
	String type;
	public void draw() {
		System.out.println(getType() + " Triangle here");
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
