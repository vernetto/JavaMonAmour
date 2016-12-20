package com.pierre.googletranslate;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;


public class GoogleTranslate {
	public static void main(String[] args) {
		MultivaluedHashMap<String, String> values = new MultivaluedHashMap<String, String>();
		values.add("x", "foo");
		values.add("y", "bar");
		Form form = new Form(values);
		

		Client client = ClientBuilder.newClient();

		WebTarget resource = client.target("http://localhost:8080/someresource");

		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    System.out.println(response.getEntity());
		} else {
		    System.out.println("ERROR! " + response.getStatus());    
		    System.out.println(response.getEntity());
		}		
		
	}

}
