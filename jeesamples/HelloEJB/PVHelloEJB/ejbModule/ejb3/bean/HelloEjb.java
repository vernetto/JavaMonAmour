package ejb3.bean;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless(mappedName = "HelloEjb")
@LocalBean
public class HelloEjb implements HelloEjbRemote, HelloEjbLocal {

	public HelloEjb() {
	}

	public String printHello(String name) {
		return "Hello " + name;
	}

}
