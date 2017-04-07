package model;


public class MessageModel {
	
	int messageId;
	String flage;
	String source;
	String destination;
	String message;
	
	public MessageModel(){}
	public MessageModel(String flage, String source, String destination, String message) {
		super();
		this.flage = flage;
		this.source = source;
		this.destination = destination;
		this.message = message;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getFlage() {
		return flage;
	}
	public void setFlage(String flage) {
		this.flage = flage;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
