package org.pierre.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class PersonView implements Serializable {
	private static final long serialVersionUID = -6810648637184992135L;
	private Person person = new Person();
	private static List<Person> persons = new ArrayList<Person>();
	
	public List<Person> getPersons() {
		return persons;
		
	}
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public void add() {
		persons.add(person);
		System.out.println("there are now " + persons.size() + " persons");
	}
}
