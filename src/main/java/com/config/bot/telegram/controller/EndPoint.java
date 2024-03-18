package com.config.bot.telegram.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.config.bot.telegram.bot.MyBot;
import com.config.bot.telegram.model.BotTelegram;
import com.config.bot.telegram.model.Response;
import com.config.bot.telegram.service.BotTelegramService;
import com.config.bot.telegram.tool.LoggerCreate;
import com.config.bot.telegram.tool.Tool;

@Controller
public class EndPoint extends LoggerCreate {
	
	@Autowired
	private MyBot myBot;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private BotTelegramService botTelegramService;
	
	////////////index\\\\\\\\\\\\
	@GetMapping( value = {"/", "/index"})
	public String getStartedPage(Model model) {
//		model.addAttribute("botTelegram", new BotTelegram(null, null, null, "", ""));
		reCallList(model);
		return "index";
	}
	
	/////////////start bot\\\\\\\\\\\
	@PostMapping( value = "/startBot")
	public String getStartedPageWithBot(Model model,
			@ModelAttribute BotTelegram bot) {
		
		System.out.println("start bot and save data : " + bot);
		
//		if(null != saveBotData  && saveBotData) {
//			botTelegramService.tryRegisterBotModel(botTelegram);
//		}
		
		bot = tool.macthBotTelegram(bot, reCallList(model));
		
		try {
			if(null != myBot.isStart 
					|| !myBot.isStart) {
		
				myBot.initModel(bot);
				
				myBot.startBot();
			} else {
				System.out.println("hai già startato un bot non puoi startanre un altro in contemporanea");
			}
			
		}catch(Exception e) {
			log.error(e.toString());
		}
		
		model.addAttribute("stringTest", "bot active\nTry me now");
		
		return "index";
	}
	
	/////////////register\\\\\\\\\\\\\\\
	@PostMapping( value = "/registerBot")
	public String resgisterBot(Model model,
			@ModelAttribute BotTelegram botTelegram) {
		
		botTelegramService.tryRegisterBotModel(botTelegram);
		
		reCallList(model);
		
		return "index";
	}
	
	@PostMapping(value = "/toBotModify")
	public String toModify(Model model,
			@ModelAttribute("bot") BotTelegram botToModify) { // TODO vedere sesi riesce ad essere generici nell'invio di user or mail
		System.out.println("call /modify bot : " + botToModify);
		botToModify = tool.macthBotTelegram(botToModify, reCallList(model));
		System.out.println("call /modify bot after serach bot : " + botToModify);
		model.addAttribute("bot", botToModify); // seend bot to html
		return "modifybot";
	}
	
	@PostMapping(value = "/saveBotModify")
	public String saveModify(Model model,
			@ModelAttribute("bot") BotTelegram botToModify) {
		System.out.println("save toString : " + botToModify.toString());
		botTelegramService.modify(botToModify);
		reCallList(model);
		return "index"; // re-load page index
	}
	
//	@PostMapping(value = "/stopBot")
//	public String stopBot(Model model) {
//		System.out.println("stop bot");
//		if(null != myBot) {
//			if(myBot.isStart) {
//				myBot = null;
//				reCallList(model);
//				model.addAttribute("stringTest", "bot stoped");
//			} else {
//				reCallList(model);
//				model.addAttribute("stringTest", "bot not stoped becaused is never strated");
//			}
//		} else {
//			reCallList(model);
//			model.addAttribute("stringTest", "bot not stoped becaused is never strated");
//		}
//		
//		return "index"; // re-load page index
//	}
	
	////////////////////delete\\\\\\\\\\\\\\\\\\\\\\\\
	@PostMapping(value = "/delete")
	public String delete(@RequestParam String id, Model model) {
		System.out.println("call /delete");
		botTelegramService.delete(new BotTelegram(Integer.parseInt(id), null, null, null, null, null, null)); // remove from list obj in json
		// recall index
		reCallList(model);
		return "index";
	}
	
	///////////////////close\\\\\\\\\\\\\\\\\\\\\\\\\\
	@GetMapping(value = "/closeApp")
	public void exitCloseapp() {
		System.out.println("call close app");
		System.exit(0);
	}
	
	///////////////////error\\\\\\\\\\\\\\\\\\\\\\\\\\
	@ExceptionHandler(Exception.class) // questo endpoint è bellissimo cattura tutte le eventuali eccezioni le prende 
	// con obj model le assegnamo ad un attribuito nella pagina html da richiamare per poi stampara errore nella pagina html cosi che 
	// si veda ad html errore anche se non è un errore completo ti da già un idea dell'eventuale errore 
	public String handleNumberFormatException(Exception ex, Model model) {
		System.out.println("error : " + ex.getMessage() + "\n" + ex.toString());
		model.addAttribute("error", ex.getMessage() + "\n" + ex.toString());
		ex.printStackTrace();
		return "error";
	}
	
	// for not have duplicate line on enne end point
	private List<BotTelegram> reCallList(Model model) {
		model.addAttribute("botTelegram", new BotTelegram(null, null, null, "", "", "", new Response("test", "test")));
		List<BotTelegram> repoBotTelegramList = botTelegramService.getListBotTelegram(tool.pathFileJson());
		if(!CollectionUtils.isEmpty(repoBotTelegramList)) {
			model.addAttribute("bots", repoBotTelegramList);
		}
		log.info("list bot : " + repoBotTelegramList);
		return repoBotTelegramList;
	}

}
