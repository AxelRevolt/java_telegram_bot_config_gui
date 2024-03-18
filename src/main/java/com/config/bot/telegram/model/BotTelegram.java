package com.config.bot.telegram.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "botName", "botToken", "botDescription", "chatId", "channelId", "response" })
public class BotTelegram {

	private Integer id;
	private Long chatId, channelId;
	private String botName, botToken, botDescription;
	private Response response;

//	public BotTelegram() {}

	public BotTelegram(Integer id, Long chatId, Long channelId, String botName, String botToken,
			String botDescription, Response response) {
		this.id = id;
		this.chatId = chatId;
		this.channelId = channelId;
		this.botName = botName == null ? "" : botName;
		this.botToken = botToken == null ? "" : botToken;
		this.botDescription = botDescription == null ? "" : botDescription;
		this.response = response;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getBotName() {
		return botName;
	}

	public void setBotName(String botName) {
		this.botName = botName;
	}

	public String getBotToken() {
		return botToken;
	}

	public void setBotToken(String botToken) {
		this.botToken = botToken;
	}

	public String getBotDescription() {
		return botDescription;
	}

	public void setBotDescription(String botDescription) {
		this.botDescription = botDescription;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "BotTelegram [id=" + id + ", chatId=" + chatId + ", channelId=" + channelId + ", botName=" + botName
				+ ", botToken=" + botToken + ", botDescription=" + botDescription + ", response=" + response + "]";
	}

}
