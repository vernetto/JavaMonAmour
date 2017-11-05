package org.pierre.cditests;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class CDITester {
	
	@Inject
	Greeting greeting;
	
	@Inject @Informal
	Greeting informalGreeting;
	
	@Inject
	private Event<String> simpleMessageEvent;
	

	public void greet() {
		System.out.println(greeting.greet("pierre"));
		System.out.println(informalGreeting.greet("pierre"));
		simpleMessageEvent.fire("done");
	}

}
