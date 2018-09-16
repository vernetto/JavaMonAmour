package org.pierre.cditests;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class MyMain {
	public static void main(String[] args) {
		SeContainer container = SeContainerInitializer.newInstance().initialize();
		CDITester cdiTester = container.select(CDITester.class).get();
		cdiTester.greet();
	}
}
