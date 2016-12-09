package ibmAPI;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.alchemy.v1.*;
import com.ibm.watson.developer_cloud.alchemy.v1.model.*;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentEmotion.Emotion;

import Model.Frase;

public class Alchemy {

	private static Map<String, Object> context;

	public static String sendRequest(String input) {

		if (input == null) {
			System.out.println("non hai immesso nulla");
			return null;
		}

		AlchemyLanguage service = new AlchemyLanguage();
		service.setApiKey("151b8c898e6521b2397d1de19f9473138cb2c072");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(AlchemyLanguage.TEXT, input);
		params.put("language", "english");
		params.put("outputMode", "json");

		DocumentSentiment sentiment = service.getSentiment(params).execute();
		SAORelations relations = service.getRelations(params).execute();
		Dates data=service.getDates(params).execute();
		TypedRelations typedRelation = service.getTypedRelations(params).execute();
		Concepts conceptAlchemy = service.getConcepts(params).execute();
		Entities entitiesAlchemy = service.getEntities(params).execute();
		Keywords keyWordAlchemy = service.getKeywords(params).execute();
		CombinedResults combined=service.getCombinedResults(params).execute();

		DocumentEmotion emotion = service.getEmotion(params).execute();

		System.out.println("Sentiment \n:" + sentiment + "\n*************************");
		System.out.println("Data \n:" + data + "\n*************************");
		System.out.println("Combined \n:" + combined + "\n*************************");
		
		System.out.println("Emotion \n:" + emotion.getEmotion() + "\n*************************");
		
		System.out.println("Relation \n:" + relations + "\n*************************");
		System.out.println("TypedRelation \n:" + typedRelation + "\n*************************");
		System.out.println("Concept \n:" + conceptAlchemy + "\n*************************");

		System.out.println("Entities \n:" + entitiesAlchemy + "\n*************************");
		System.out.println("Keywords \n:" + keyWordAlchemy + "\n*************************");

		JSONObject sen = new JSONObject(sentiment);
		// System.out.println("*JSON:"+sen+"**********");
		JSONObject docSent = sen.getJSONObject("sentiment");

		// System.out.println("Type:"+docSent.get("type")+" valore:
		// "+docSent.get("score"));
		// System.out.println("Fine funzione");
		return docSent.toString();
	}

	
	public static Frase sendRequestReturnFrase(String input) {
		

		if (input == null) {
			System.out.println("non hai immesso nulla");
			return null;
		}

		AlchemyLanguage service = new AlchemyLanguage();
		service.setApiKey("151b8c898e6521b2397d1de19f9473138cb2c072");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(AlchemyLanguage.TEXT, input);
		params.put("language", "english");
		params.put("outputMode", "json");

		DocumentSentiment sentiment = service.getSentiment(params).execute();
		SAORelations relations = service.getRelations(params).execute();
		Dates data=service.getDates(params).execute();
		List<TypedRelation> typedRelation = service.getTypedRelations(params).execute().getTypedRelations();
		List<Concept> conceptAlchemy = service.getConcepts(params).execute().getConcepts();
		List<Entity> entitiesAlchemy = service.getEntities(params).execute().getEntities();
		List<Keyword> keyWordAlchemy = service.getKeywords(params).execute().getKeywords();

		
		DocumentEmotion emotion = service.getEmotion(params).execute();
		
		Instant instant=Instant.now();
		Frase frase=new Frase(input,input,java.util.Date.from(instant),sentiment,emotion,typedRelation,conceptAlchemy,entitiesAlchemy,keyWordAlchemy);
		
		return frase;
	}
	
	
}
