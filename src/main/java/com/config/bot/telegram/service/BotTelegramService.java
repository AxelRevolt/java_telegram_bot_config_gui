package com.config.bot.telegram.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.config.bot.telegram.factory.BotTelegramFactory;
import com.config.bot.telegram.model.BotTelegram;
import com.config.bot.telegram.model.Response;
import com.config.bot.telegram.repository.RepoJson;
import com.config.bot.telegram.tool.LoggerCreate;
import com.config.bot.telegram.tool.Tool;

@Service
public class BotTelegramService extends LoggerCreate{
	
	@Autowired
	private Tool tool;

	@Autowired
	private RepoJson repoJson;
	
	@Autowired
	private BotTelegramFactory botTelegramFactory;

	public BotTelegram tryLogin(BotTelegram BotTelegram, List<BotTelegram> BotTelegramRepoList) {
		return repoJson.searchBotTelegramInDataSave(BotTelegram, BotTelegramRepoList);
	}

	public void register(BotTelegram BotTelegram) {
		try {
			repoJson.writeJsonListRecords(BotTelegram, tool.pathFileJson());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(BotTelegram BotTelegram) {
		try {
			repoJson.removeObjFromJson(BotTelegram, tool.pathFileJson());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BotTelegram convert(String id, String chatId, String channelId,
			String botName, String botToken, String description, Response response) {
		return botTelegramFactory.convert(StringUtils.isNotEmpty(id) ? Integer.parseInt(id) : null, 
					StringUtils.isNotEmpty(chatId) ? Long.parseLong(chatId) : null,
					StringUtils.isNotEmpty(channelId) ? Long.parseLong(channelId) : null,
					botName, botToken, description, response);
	}
	
	public void modify(BotTelegram botTelegram) {
		try {
			repoJson.modifyObjFromJson(botTelegram, tool.pathFileJson());
		}catch(IOException e) {
			log.error("error on try to modify botConfig on bot : {}", botTelegram);
			e.printStackTrace();
		}
	}

	public List<BotTelegram> getListBotTelegram(File file) {
		try {
			return repoJson.readProductionFromJson(file);
		} catch (FileNotFoundException e) {
			log.error("file not found");
			e.printStackTrace();
		}
		return null;
	}
	
	public void tryRegisterBot(String chatId, String channelId, String botName, String botToken,
			 String description, Response response) {
		
		log.info("start tryRegisterBot");
		
		List<BotTelegram> repoBotTelegramList = getListBotTelegram(tool.pathFileJson());
		BotTelegram botInsert = convert(null, chatId, channelId, botName, botToken, description, response);
		
		if(null == tool.macthBotTelegram(botInsert, repoBotTelegramList)) { // not exist bot in repo file then start register it
			log.info("start register bot in file");
			register(botInsert);
			log.info("end register bot in file");
		}
		log.info("end tryRegisterBot");
	}
	
	public void tryRegisterBotModel(BotTelegram botTelegramInsert) {
		
		log.info("start tryRegisterBot");
		
		List<BotTelegram> repoBotTelegramList = getListBotTelegram(tool.pathFileJson());
		
		if(null == tool.macthBotTelegram(botTelegramInsert, repoBotTelegramList)) { // not exist bot in repo file then start register it
			log.info("start register bot in file");
			register(botTelegramInsert);
			log.info("end register bot in file");
		}
		log.info("end tryRegisterBot");
	}

}
