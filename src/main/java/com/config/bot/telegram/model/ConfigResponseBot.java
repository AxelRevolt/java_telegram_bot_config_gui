package com.config.bot.telegram.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "typeRespone", "requestPossible", "responsePossible"})
public class ConfigResponseBot {
	
	private String typeRespone;
	
	private HashMap<String, ArrayList<String>> requestResponse;

	public ConfigResponseBot(String typeRespone, HashMap<String, ArrayList<String>> requestResponse) {
		this.typeRespone = typeRespone;
		this.requestResponse = requestResponse;
	}

	public String getTypeRespone() {
		return typeRespone;
	}

	public void setTypeRespone(String typeRespone) {
		this.typeRespone = typeRespone;
	}

	public HashMap<String, ArrayList<String>> getRequestResponse() {
		return requestResponse;
	}

	public void setRequestResponse(HashMap<String, ArrayList<String>> requestResponse) {
		this.requestResponse = requestResponse;
	}

}
