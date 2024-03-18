package com.config.bot.telegram.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "key", "responseString"})
public class Response {
	
	private String key;
	private String responseString;

	public Response() {}

	public Response(String key, String responseString) {
		this.key = key == null ? "" : key;
		this.responseString = responseString == null ? "" : responseString;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	@Override
	public String toString() {
		return "Response [key=" + key + ", response=" + responseString + "]";
	}

}
