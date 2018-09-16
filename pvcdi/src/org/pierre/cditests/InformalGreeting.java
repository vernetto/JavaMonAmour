package org.pierre.cditests;

@Informal
public class InformalGreeting extends Greeting {
	@Override
	public String greet(String name) {
		return "Informal Hello, " + name ;
	}

}
