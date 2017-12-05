package org.pierre.websockets;

import java.io.IOException;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class HelloEndpoint extends Endpoint {

	private Session session;

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		System.out.println("onOpen");
		this.session = session;
		this.session.addMessageHandler(new MessageHandler.Whole<String>() {

			public void onMessage(String message) {
				System.out.println("!!!!! retrieved: " + message);
				
			}
		});
		
	}
	
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

}
