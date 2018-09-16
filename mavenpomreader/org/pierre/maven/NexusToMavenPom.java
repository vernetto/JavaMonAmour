package org.pierre.maven;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

public class NexusToMavenPom {
	public static void main(String[] args) throws Throwable {
		objectToXML(); 
		xmlToObject();
	}

	private static void xmlToObject() throws Throwable {
		JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		File pomFile = new File("D:\\pierre\\downloads\\centralpoms\\antlr\\antlr\\2.7.2\\antlr-2.7.2.pom");
		Project project = (Project) jaxbUnmarshaller.unmarshal(pomFile);
		Marshaller jaxbMarshaller = getMarshaller(jaxbContext);
		jaxbMarshaller.marshal(project, System.out);
		
	}

	private static void objectToXML() throws Throwable {
		Project project = new Project();
		project.setArtifactId("antlr");
		project.setGroupId("antlr");
		project.setModelVersion("4.0.0");
		project.setVersion("2.7.2");
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
		Marshaller jaxbMarshaller = getMarshaller(jaxbContext);
		File XMLfile = new File("myproject.xml");
		jaxbMarshaller.marshal(project, XMLfile); 
		jaxbMarshaller.marshal(project, System.out);
	}

	private static Marshaller getMarshaller(JAXBContext jaxbContext) throws JAXBException, PropertyException {
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return jaxbMarshaller;
	}

}



