package com.config.bot.telegram.factory;

import org.springframework.stereotype.Component;

import com.config.bot.telegram.model.BotTelegram;
import com.config.bot.telegram.model.Response;

@Component
public class BotTelegramFactory {
	
	public BotTelegram convert(Integer id, Long chatId, Long channelId,
			String botName, String botToken, String description, Response response) {
		return new BotTelegram(id, chatId, channelId, botName, botToken, description, response);
	}

}