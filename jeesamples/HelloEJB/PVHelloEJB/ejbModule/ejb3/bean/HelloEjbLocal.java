package ejb3.bean;

import javax.ejb.Local;

@Local
public interface HelloEjbLocal {
	String printHello(String name);
}
