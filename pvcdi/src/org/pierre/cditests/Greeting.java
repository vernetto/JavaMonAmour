package org.pierre.cditests;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Default
public class Greeting {
	
	  @Inject
	  @Property
	  private String rootFolder;
	  
	  public String greet(String name) {
	        return "Hello, " + name + ". rootFolder is " + rootFolder;
	    }

}
