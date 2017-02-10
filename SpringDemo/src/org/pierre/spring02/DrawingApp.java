package org.pierre.spring02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DrawingApp {
	public static void main(String[] args) {
		System.out.println(System.getProperty("java.class.path"));
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring02.xml");
		Triangle tr01 = (Triangle)ac.getBean("triangle");
		tr01.draw();
		
	}

}
