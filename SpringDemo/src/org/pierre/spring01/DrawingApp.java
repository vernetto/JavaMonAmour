package org.pierre.spring01;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

public class DrawingApp {
	public static void main(String[] args) {
		System.out.println(System.getProperty("java.class.path"));
		ApplicationContext ac = new ClassPathXmlApplicationContext();
		BeanFactory bf = new XmlBeanFactory(new FileSystemResource("spring01.xml"));
		Triangle tr = (Triangle)bf.getBean("triangle");
		tr.draw();
	}

}
