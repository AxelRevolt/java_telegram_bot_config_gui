package com.config.bot.telegram.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.config.bot.telegram.model.BotTelegram;
import com.config.bot.telegram.model.Response;
import com.config.bot.telegram.tool.LoggerCreate;
import com.config.bot.telegram.tool.Tool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

@Service
public class RepoJson extends LoggerCreate {

	@Autowired
	private Tool tool;

	public BotTelegram searchBotTelegramInDataSave(BotTelegram botTelegramStart, List<BotTelegram> botTelegramRepoList) {

		return tool.macthBotTelegram(botTelegramStart, botTelegramRepoList);
	}

	public ArrayList<BotTelegram> readProductionFromJson(File pathFile) throws FileNotFoundException {

//			System.err.println("hello , readProductionFromJson");

		BotTelegram[] arrBot = null;
		ArrayList<BotTelegram> listBot = new ArrayList<BotTelegram>();
		JsonReader reader = null;

		FileReader fileReader = new FileReader(pathFile);

		try {

			BufferedReader br = new BufferedReader(fileReader);
			if (br.readLine() == null) {
				System.out.println("error file not have data");
			} else {

				reader = new JsonReader(new FileReader(pathFile));
				arrBot = new Gson().fromJson(reader, BotTelegram[].class);

			}

			if (null != arrBot) {
				listBot = tool.arrayToList(arrBot);
			}
			
			for(int i = 0; i < listBot.size(); i++) {
				listBot.get(i).setId(i + 1);
			}

			return listBot;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (NullPointerException | IOException e) {
				e.printStackTrace();
			}
		}
		return listBot;
	}

//		public ArrayList<BotTelegram> readProductionFromJson(File pathFile) throws FileNotFoundException {
//		    try (Reader reader = new FileReader(pathFile)) {
//		        Gson gson = new Gson();
//		        Type BotTelegramListType = new TypeToken<ArrayList<BotTelegram>>() {}.getType();
//		        return gson.fromJson(reader, BotTelegramListType);
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//		    return new ArrayList<>(); // Restituisci una lista vuota in caso di errori
//		}

	public boolean writeJsonListRecords(BotTelegram botTelegram, File file) throws IOException {

		System.out.println("BotTelegram in arrivo : " + botTelegram.toString());

//		JsonArray jsonArray = new JsonArray();
		ArrayList<BotTelegram> oldBotTelegramList = new ArrayList<BotTelegram>();

		int sizeOldList = 0;

		FileReader fileReader = new FileReader(file);
		FileWriter fileWriter = null;

		JsonObject newObject = null;
		JsonObject objPermesso = null; // obj json for new model
		JsonObject objUtente = null; // obj json for new model

		if (file.exists()) {
			try {
				System.out.println("file exist");
				BufferedReader br = new BufferedReader(fileReader);
				if (br.readLine() == null) {
					System.out.println("file is empty");
				} else {
					oldBotTelegramList = readProductionFromJson(file);
					sizeOldList = oldBotTelegramList.size();

				}
			} catch (Exception e) {
				System.err.println("error on writeJsonListRecords");
				e.printStackTrace();
			}
		}

		if (oldBotTelegramList.size() != 0) { //
			System.out.println("Start for old list write");
//			for (int i = 0; i < sizeOldList; i++) {// add old link before write file complete
////				setObjJson(jsonArray, newObject, oldBotTelegramList.get(i));
//			}
			botTelegram.setId(null != oldBotTelegramList.getLast().getId() ? oldBotTelegramList.getLast().getId() + 1 : 1);
			oldBotTelegramList.add(botTelegram);
//			setObjJson(jsonArray, newObject, botTelegram); // add new obj in case of register

		} else { // first write
			botTelegram.setId(1);
			oldBotTelegramList.add(botTelegram);
//			setObjJson(jsonArray, newObject, BotTelegram);
		}
		
//		oldBotTelegramList.add(BotTelegram);

		try {
			System.out.println("tentativo di scrittura del file json");
			fileWriter = new FileWriter(file);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(oldBotTelegramList, fileWriter);
//			Gson gson = new Gson();
//			String listJsonBot = gson.toJson(oldBotTelegramList);
//			fileWriter.write(listJsonBot);
		} catch (Exception e) {
			return false;
		} finally {
			fileWriter.close();
		}

		System.out.println("Fine scrittura file json : " + botTelegram.toString());

		return true;

	}

	public boolean removeObjFromJson(BotTelegram BotTelegram, File file) throws IOException {

		System.out.println("removeObjFromJson BotTelegram in arrivo : " + BotTelegram.toString());

		JsonArray jsonArray = new JsonArray();
		ArrayList<BotTelegram> oldBotTelegramList = new ArrayList<BotTelegram>();

		int sizeOldList = 0;

		FileReader fileReader = new FileReader(file);
		FileWriter fileWriter = null;

		JsonObject newObject = null;

		if (file.exists()) {
			try {
				System.out.println("file exist");
				BufferedReader br = new BufferedReader(fileReader);
				if (br.readLine() == null) {
					System.out.println("file is empty");
				} else {
					oldBotTelegramList = readProductionFromJson(file);
					sizeOldList = oldBotTelegramList.size();

				}
			} catch (Exception e) {
				System.err.println("error on writeJsonListRecords");
				e.printStackTrace();
			}
		}

		for (BotTelegram a : oldBotTelegramList) {
			if (BotTelegram.getId() == a.getId()) {
				oldBotTelegramList.remove(a);// remove obj
				break;
			}
		}
		
//		oldBotTelegramList = readProductionFromJson(file); // re-read file for set id from 1 to infinity

//		for (int i = 0; i < oldBotTelegramList.size(); i++) {
//			oldBotTelegramList.get(i).setId(i + 1);
//			setObjJson(jsonArray, newObject, oldBotTelegramList.get(i));
//		}

		try {
			System.out.println("tentativo di scrittura del file json");
			fileWriter = new FileWriter(file);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(oldBotTelegramList, fileWriter);
		} catch (Exception e) {
			return false;
		} finally {
			fileWriter.close();
		}
		
		System.out.println("Fine scrittura file json : " + BotTelegram.toString());

		return true;

	}

	public boolean modifyObjFromJson(BotTelegram modifiedBotTelegram, File file) throws IOException {

		System.out.println("modifyObjFromJson BotTelegram in arrivo : " + modifiedBotTelegram.toString());

//		JsonArray jsonArray = new JsonArray();
		ArrayList<BotTelegram> oldBotTelegramList = new ArrayList<BotTelegram>();

		int sizeOldList = 0;

		FileReader fileReader = new FileReader(file);
		FileWriter fileWriter = null;

		JsonObject newObject = null;
		JsonObject objPermesso = null;
		JsonObject objUtente = null;

		if (file.exists()) {
			try {
				System.out.println("file exist");
				BufferedReader br = new BufferedReader(fileReader);
				if (br.readLine() == null) {
					System.out.println("file is empty");
				} else {
					oldBotTelegramList = readProductionFromJson(file);
					sizeOldList = oldBotTelegramList.size();
				}
			} catch (Exception e) {
				System.err.println("error on writeJsonListRecords");
				e.printStackTrace();
			}
		}

		for (BotTelegram a : oldBotTelegramList) {
			if (modifiedBotTelegram.getId() == a.getId()) {
				// Modifica l'oggetto
				a.setBotName(modifiedBotTelegram.getBotName());
				a.setBotToken(modifiedBotTelegram.getBotToken());
				a.setChatId(modifiedBotTelegram.getChatId());
				a.setChannelId(modifiedBotTelegram.getChannelId());
				a.setBotDescription(modifiedBotTelegram.getBotDescription());
				a.setResponse(modifiedBotTelegram.getResponse()); // set response map
				break;
			}
		}

//		for (BotTelegram bot : oldBotTelegramList) {
//			setObjJson(bot, newObject, bot);
//		}

		try {
			System.out.println("tentativo di scrittura del file json");
			fileWriter = new FileWriter(file);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(oldBotTelegramList, fileWriter);
//			Gson gson = new Gson();
//			String listJsonBot = gson.toJson(oldBotTelegramList);
//			fileWriter.write(listJsonBot);
		} catch (Exception e) {
			return false;
		} finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
		}

		System.out.println("Fine scrittura file json : " + modifiedBotTelegram.toString());

		return true;
	}
	
}

//	private void setObjJson(JsonArray jsonArray, JsonObject newObject,
//			BotTelegram botTelegram) {
//
//		JsonObject responseObj = new JsonObject();
//		JsonObject responseObjEnne = new JsonObject();
//				
//		newObject = new JsonObject();
//
//		newObject.addProperty("id", botTelegram.getId());
//		newObject.addProperty("botName", botTelegram.getBotName());
//		newObject.addProperty("botToken", botTelegram.getBotToken());
//		newObject.addProperty("chatId", botTelegram.getChatId());
//		newObject.addProperty("channelId", botTelegram.getChannelId());
//		newObject.addProperty("description", botTelegram.getBotDescription());
//		
//		for(int i = 0; i < sizeList; i++) {
//			responseObjEnne = new JsonObject();
//			for(String response : botTelegram.getResponse().get(i).getResponse()) {
//					responseObj.addProperty(botTelegram.getResponse().get(i).getKey(), botTelegram.getResponse().get(i).getResponse());
//			}
//			responseObjEnne.add(String.valueOf(i), responseObjEnne);
//		}
//		
//		newObject.add("response", responseObj);
//
//		jsonArray.add(newObject);// add obj main
//	}
	