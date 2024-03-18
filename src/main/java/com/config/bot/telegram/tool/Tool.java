package com.config.bot.telegram.tool;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.config.bot.telegram.model.BotTelegram;
import com.config.bot.telegram.model.Response;
import com.config.bot.telegram.repository.RepoJson;

@Service
public class Tool extends LoggerCreate {
	
	@Lazy
	@Autowired
	private RepoJson repoJson;

	public String leggiStringa(String msg) {
		Scanner input = new Scanner(System.in);
		log.info(msg);
		String inputText = input.nextLine();
		while (inputText.isEmpty()) {
			leggiStringa("error try again " + msg);
			return inputText;
		}
		return inputText;
	}

	public int leggiInt(String msg) {
		log.info("leggi int");
		Scanner input = new Scanner(System.in);
		boolean isError = false;
		int i = 0;
		do {
			if (!isError) {
				log.info(msg);

			} else {
				log.info("Error try again :\n" + msg);
			}

			try {
				i = Integer.parseInt(input.nextLine());
			} catch (NumberFormatException e) {
				log.info("error , please insert a number");
				isError = true;
			}
		} while (isError);
		return i;
	}

	public void print(String msg, boolean error) {
		if (!error)
			log.info(msg);
		else
			log.error(msg);
	}

	public boolean answerAffermative(String answerFromConsole) {
		log.info("answerAffermative");
		return answerFromConsole.equalsIgnoreCase("si") || answerFromConsole.equalsIgnoreCase("s")
				|| answerFromConsole.equalsIgnoreCase("yes") || answerFromConsole.equalsIgnoreCase("y");
	}

	public static String randomString(ArrayList<String> listString) {
		Random random = new Random();
		int index = random.nextInt(listString.size());
		return listString.get(index);
	}

	public static int randomInt(int max) {
		Random random = new Random();
		return random.nextInt(max);
	}

	public String getDateString() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}

	public Date getDate() {
		return new Date();
	}

	public Date stringToDate(String dateString) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = formatter.parse(dateString);
			log.info(date.toString());
		} catch (Exception e) {
			log.error("Errore durante la conversione della data");
		}
		return date;
	}
	
	public boolean checkInit(Long aa, Long bb, String ... args) {
		
		for(String a : args) {
			if(StringUtils.isEmpty(a)) {
				return false;
			}
		}
		
		if(null == aa && null == bb ) { // for test
			return true;
		}
		
		return aa != 0 && bb != 0;
	}
	
	public ArrayList<BotTelegram> arrayToList(BotTelegram[] arrToList) {
		ArrayList<BotTelegram> array_list = new ArrayList<BotTelegram>();
		Collections.addAll(array_list, arrToList);
		return array_list;
	}
	
	public BotTelegram macthBotTelegram(BotTelegram botInsert, List<BotTelegram> botRepoList) {
		
		for(BotTelegram b : botRepoList) {
			if(b.getBotName().equalsIgnoreCase(botInsert.getBotName())) {
				return b;
			}
		}
		
		return null;
	}

	public File pathFileJson() {
		
		log.info("start create or recuvera file json storgae");
		
	     // Ottieni la directory di lavoro corrente (dove si trova il JAR)
        String currentWorkingDirectory = System.getProperty("user.dir"); // get current path on run jar on windows or mac

		File pathFileJson = new File(currentWorkingDirectory + File.separator + "testBot.json"); // for craeet seperator for windows or mac 

		try {

			if (!pathFileJson.exists()) {
				pathFileJson.createNewFile();
				try {
					BotTelegram bot = new BotTelegram(0, 00l, 00l, "000", "admin@00", "test", new Response("key0", "test"));
					
					repoJson.writeJsonListRecords(bot, pathFileJson);
				} catch (Exception e) {
					System.err.println("error on craete file and insert accound Admin");
					e.printStackTrace();
				}
			} else {
				log.info("file already exists on : " + currentWorkingDirectory);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		log.info("end create or recuvera file json storage");
		
		return pathFileJson;
	}
	
}
