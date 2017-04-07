package server;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.Extension;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.MessageHandler.Partial;
import javax.websocket.MessageHandler.Whole;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.TypeKey;

import model.MessageModel;

@ServerEndpoint("/chat/{username}")
public class ServerEndPoint {
	static Map connectedUsers = Collections.synchronizedMap((new HashMap<String, Session>()));

	@OnOpen
	public void startSession(Session session, @PathParam("username") String user) {
		System.out.println("before open ");
		printInfo();

		System.out.println("new User:" + session.getId() + "\t" + user);

		connectedUsers.put(user, session);

		System.out.println("after open ");
		printInfo();
		
		broadcast_counter();
	}

	@OnClose
	public void endSession(Session session) {
		System.out.println("before close ");
		printInfo();
		System.out.println("close:" + session.getId() + "\t" + connectedUsers);
		connectedUsers.values().remove(session);
		System.out.println("after close ");
		printInfo();
		
		broadcast_counter();
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		MessageModel messageModel = convertJsonToModel(message);
		System.out.println("new Message Source " + messageModel.getSource() + " to " + messageModel.getDestination());
		Session session2 = (Session) connectedUsers.get(messageModel.getDestination());
		if (session2 != null)
			session2.getAsyncRemote().sendText(message);
		else
			System.out.println("error in sending message !! session is closed ");
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		System.out.println(throwable.toString());
	}

	public void printInfo() {
		System.out.println("num of Users now: " + connectedUsers.size());
	}

	public static void broadcast_counter() {
		
		Iterator it = connectedUsers.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<String, Session> entry = (Entry<String, Session>) it.next();
			
			MessageModel messageModel = new MessageModel();
			messageModel.setFlage("chat_counter");
			messageModel.setMessage(String.valueOf(connectedUsers.size()));
			String json  = ServerEndPoint.convertModelToJson(messageModel);
			entry.getValue().getAsyncRemote().sendText(json);
		}
	}

	public static String convertModelToJson(MessageModel messageModel) {
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(messageModel);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return json;
	}

	public static MessageModel convertJsonToModel(String json) {
		MessageModel messageModel = new MessageModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			messageModel = mapper.readValue(json, MessageModel.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return messageModel;
	}

}
