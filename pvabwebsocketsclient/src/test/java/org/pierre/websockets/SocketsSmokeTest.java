package org.pierre.websockets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.junit.Before;
import org.junit.Test;

public class SocketsSmokeTest {
	
	private WebSocketContainer container;
	private HelloEndpoint endpoint;
	
	public SocketsSmokeTest() {

	}
	
	@Before
	public void onInit() {
		this.container = ContainerProvider.getWebSocketContainer();
		this.endpoint = new HelloEndpoint();
	}
	
	//ws://localhost:41725/websockets-server/hello
	@Test
	public void pingServer() throws DeploymentException, IOException, URISyntaxException {
		Session connectToServer = this.container.connectToServer(this.endpoint, new URI("ws://localhost:8080/websockets-server-1.0-SNAPSHOT/hello"));
		this.endpoint.sendMessage("hello from client");
	}
}
