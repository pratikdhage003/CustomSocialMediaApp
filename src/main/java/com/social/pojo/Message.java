package com.social.pojo;

public class Message {

	private int message_id;
	private String message_content;
	private String posted_at;
	private int user_id;

	public Message() {

	}

	public Message(int user_id, String message_content) {
		this.user_id = user_id;
		this.message_content = message_content;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public String getMessage_content() {
		return message_content;
	}

	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}

	public String getPosted_at() {
		return posted_at;
	}

	public void setPosted_at(String posted_at) {
		this.posted_at = posted_at;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Message [message_id=" + message_id + ", message_content=" + message_content + ", posted_at=" + posted_at
				+ ", user_id=" + user_id + "]";
	}

}
