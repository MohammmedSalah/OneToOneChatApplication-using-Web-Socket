package client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.plaf.SliderUI;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.server.ServerEndpoint;

import model.MessageModel;
import server.ServerEndPoint;

public class Main implements ClientEndPoint.MessageHandler {
	ClientEndPoint chatClientEndpoint = null;
	public ClientEndPoint rec(){
		chatClientEndpoint = null;
		try {
			
			chatClientEndpoint = new ClientEndPoint(new URI(
					"ws://localhost:8080/ChatApp_OneToOne/chat/user1"));
			chatClientEndpoint.addMessageHandler(this);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chatClientEndpoint;
	}

	public static void main(String[] args) {
		System.out.println("starting ....");
		ClientEndPoint chatClientEndpoint = null;
		Main main = new Main();

		chatClientEndpoint = main.rec();

		while (true) {
			try {
				Thread.sleep(1000);
				main.sendMessage();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	

	}
	
	//{"messageId":0,"flage":"chat","source":"one","destination":"one","message":"Hello :)"}
	@Override
	public void handleMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println("from server \t"+message);
	}
	public void sendMessage(){
		MessageModel messageModel = new MessageModel();
		messageModel.setFlage("chat");
		messageModel.setSource("user1");
		messageModel.setDestination("user1");
		messageModel.setMessage("Hello :)");
		String json  = ServerEndPoint.convertModelToJson(messageModel);
		System.out.println(json);
		chatClientEndpoint.sendMessage(json);
	}

}
