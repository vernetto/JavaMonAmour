package org.pierre.maven;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "modelVersion", "groupId", "artifactId", "version"})
public class Project {
	public String getModelVersion() {
		return modelVersion;
	}
	@XmlElement
	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}
	public String getGroupId() {
		return groupId;
	}
	@XmlElement
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getArtifactId() {
		return artifactId;
	}
	@XmlElement
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	public String getVersion() {
		return version;
	}
	@XmlElement
	public void setVersion(String version) {
		this.version = version;
	}
	String modelVersion;
	String groupId;
	String artifactId;
	String version;
	
}
