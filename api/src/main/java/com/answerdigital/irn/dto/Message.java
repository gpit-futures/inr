package com.answerdigital.irn.dto;

public class Message {

	private String source;

	private String destination;
	
	private ResponseDTO body;
	
	public Message(String source, String destination, ResponseDTO body) {
		this.source = source;
		this.destination = destination;
		this.body = body;
	}
	
	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public ResponseDTO getBody() {
		return body;
	}
}
