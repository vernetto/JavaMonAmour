package ejb3.bean;

import javax.ejb.Remote;

@Remote
public interface HelloEjbRemote {
	String printHello(String name);
}
