package org.pierre.cditests;

import javax.inject.Inject;

public class CDITester {
	
	@Inject
	Greeting greeting;
	
	



	public void greet() {
		System.out.println(greeting.greet("pierre"));
		
	}

}
