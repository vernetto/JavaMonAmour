package org.pierre.cditests;
import javax.enterprise.inject.Default;

@Default
public class Greeting {
	  public String greet(String name) {
	        return "Hello, " + name + ".";
	    }

}
