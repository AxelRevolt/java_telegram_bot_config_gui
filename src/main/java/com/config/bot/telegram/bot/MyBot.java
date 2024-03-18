package com.config.bot.telegram.bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.thymeleaf.util.StringUtils;

import com.config.bot.telegram.model.BotTelegram;
import com.config.bot.telegram.tool.Tool;
import com.config.bot.telegram.tool.string.Frasi;

@Service
@SuppressWarnings("deprecation")
public class MyBot extends TelegramLongPollingBot {
	
	@Autowired
	private Tool tool;

	private Long myChatId;
	private Long myChannelId;
	private String botName;
	private String tokenBot;
	private BotTelegram botTelegram;
	
	public Boolean isStart = false;

	public void init(Long myChatId, Long myChannelId, String botName, String tokenBot,
			BotTelegram botTelegram) {
		this.myChatId = myChatId;
		this.myChannelId = myChannelId;
		this.botName = botName;
		this.tokenBot = tokenBot;
		this.botTelegram = botTelegram;
	}
	
	public void initModel(BotTelegram botTelegram) {
		this.myChatId = botTelegram.getChatId();
		this.myChannelId = botTelegram.getChannelId();
		this.botName = botTelegram.getBotName();
		this.tokenBot = botTelegram.getBotToken();
		this.botTelegram = botTelegram;
	}

	@Override
	public String getBotUsername() {
		return botName;
	}

	@Override
	public String getBotToken() {
		return tokenBot;
	}

//	public static void main(String[] args) { // test with main
//		try {
//			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//			botsApi.registerBot(new MyBot(0, 0, null, null));
//			System.out.println("sono in ascolto . . . ");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public void startBot() {
		
		if (tool.checkInit(myChatId, myChannelId, botName, tokenBot)) {
			tool.print("init valid in myBot", false);
			try {
				TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
				botsApi.registerBot(this); // new MyBot() funzionava prima senza che avevsse bisogno di parametri generici per startare il bot
				System.out.println("sono in ascolto . . . ");
				isStart = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else  {
			tool.print("error on init in myBot", true);
		}
	}
	
	@Override
	public void onUpdateReceived(Update update) {

		String messageFromUser = update.getMessage().getText();
		
		if (update.hasMessage() && update.getMessage().hasText()) {
			System.out.println("text from user : " + messageFromUser);
			long chatId = update.getMessage().getChatId();
			LocalDateTime date = LocalDateTime.ofEpochSecond(update.getMessage().getDate(), 0, ZoneOffset.of("+02:00"));
			System.out.println("chatId : " + chatId + "\nHours : " + date);

			if(!StringUtils.isEmpty(botTelegram.getResponse().getKey()) &&
					!StringUtils.isEmpty(botTelegram.getResponse().getResponseString())) {
				sendResponse(chatId, response(botTelegram, messageFromUser)); // resonse custom from inbsert user
			} else {
				sendResponse(chatId, response(messageFromUser)); // response default if response obj is empty
			}
		}

	}

	public void sendResponse(Long chatId, String response) {
		try {
			execute(new SendMessage(String.valueOf(chatId), response));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String response(String messageFromUser) {

		System.out.println("Build response : " + messageFromUser);

		try {

			if (messageFromUser.toLowerCase().trim().contains("/start")) {
				return Tool.randomString(Frasi.responseBot("ciao"));
			}
			if (messageFromUser.toLowerCase().trim().contains("/aiuto")) {
				return Frasi.aiutoBot;
			}
			if (messageFromUser.toLowerCase().trim().contains("/test")) {
				return Frasi.test;
			}
			if (messageFromUser.toLowerCase().trim().contains("ciao")) {
				return Tool.randomString(Frasi.responseBot("ciao"));
			}
			if (messageFromUser.toLowerCase().trim().contains("come stai")) {
				return Tool.randomString(Frasi.responseBot("come stai"));
			}
			if (messageFromUser.toLowerCase().trim().contains("chi sei")) {
				return Tool.randomString(Frasi.responseBot("chi sei"));
			}

			return Tool.randomString(Frasi.responseBot(""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
	
	public String response(BotTelegram botTelegram, String messageFromUser) {

		System.out.println("Build response : " + messageFromUser);

		if(messageFromUser.trim().equalsIgnoreCase(botTelegram.getResponse().getKey())) {
			return botTelegram.getResponse().getResponseString();
		} else {
			return "sono giovane non capisco, scusa";
		}

	}

}
