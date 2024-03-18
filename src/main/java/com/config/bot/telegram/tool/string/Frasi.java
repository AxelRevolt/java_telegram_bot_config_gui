package com.config.bot.telegram.tool.string;

import java.util.ArrayList;

public class Frasi {

	public static String aiutoBot = "Ciao per ora sono in grado di risponde a queste tre domande :\n" 
			+ "1️⃣ Ciao\n"
			+ "2️⃣ Come stai\n" 
			+ "3️⃣ Chi sei\n" 
			+ "4️⃣ in lavorazione ma puoi chiedermi img di qualcosa e ti inviaero la prima immagine trovata online(Esempio img di pappagalli)\n\n"
			+ "Ciaooo.";

	public static String test = "Working in progress . . . ";

	public static ArrayList<String> responseBot(String typeResponse) {

		ArrayList<String> responseCiao = null;

		System.out.println("typeResponse : " + typeResponse);

		if (typeResponse.equalsIgnoreCase("ciao")) {
			System.out.println("We are in ciao");
			responseCiao = new ArrayList<String>();
			responseCiao.add("Ciao a te 😊");
			responseCiao.add("Ciao 😒");
			responseCiao.add("Scusi , lei non ha altro da fare che importunare me ? 😒");
			responseCiao.add("Ma Buongiorno/Buona sera , un caffè ? ☕");
			return responseCiao;
		}
//		else 
		if (typeResponse.equalsIgnoreCase("come stai")) {
			System.out.println("We are in come stai");
			responseCiao = new ArrayList<String>();
			responseCiao.add("Bene , grazie 😊");
			responseCiao.add("Guarda potrei star meglio 😒");
			responseCiao.add("Non troppo bene 😒");
			responseCiao.add("Bene , vuoi un caffe ? ☕");
			return responseCiao;
		}
//			else 
		if (typeResponse.equalsIgnoreCase("chi sei")) {
			System.out.println("We are in chi sei");
			responseCiao = new ArrayList<String>();
			responseCiao.add("Qualcuno o qualcosa che sta imparando 😊(no non è vero sono tutte frasi perimpostate)");
			responseCiao.add("Qualcuno o qualcosa , ma ancora non lo so 😒");
//			responseCiao.add("Non troppo bene 😒");
//			responseCiao.add("Bene , vuoi un caffe ? ☕");
			return responseCiao;
		}
		// else
		if (typeResponse.equalsIgnoreCase("")) {
			System.out.println("We are in esle");
			responseCiao = new ArrayList<String>();
			responseCiao
					.add("Mi spiace sono limitato , posso rispondere a :\nCiao\ncome stai\nchi sei\nper ora . . .😊");
			return responseCiao;
		}

		return responseCiao;
	}
	
}
