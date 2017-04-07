package client;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;



@ClientEndpoint
public class ClientEndPoint {
	public ClientEndPoint(){}
    Session userSession = null;
    public  MessageHandler messageHandler;
 
    public ClientEndPoint(URI endpointURI) {
    	System.out.println("trying to connect ....");
        try {
            WebSocketContainer container = ContainerProvider
                    .getWebSocketContainer();
            container.connectToServer(this, endpointURI);
            container.setDefaultMaxSessionIdleTimeout(100000000);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;

    }
 
    
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }
 
   
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null)
        	//System.out.println("client rec\t" + message);
            this.messageHandler.handleMessage(message);
    }
 
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }
 
    public void sendMessage(String message) {
    
    	synchronized(userSession){
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		this.userSession.getAsyncRemote().sendText(message);
    	}
        
    }
 

    public static interface MessageHandler {
        public void handleMessage(String message);
    }
}
