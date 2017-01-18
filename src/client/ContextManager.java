package client;

import java.io.IOException;

import javax.print.attribute.standard.RequestingUserName;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.syndication.io.FeedException;

import Model.Frase;

public class ContextManager {

	/*JSON object inserted in context variable of Conversation
	 * where we insert variable:
	 * 
	 * news:if requested we store here the title of a news taken from RSS
	 * City:if a City was mentioned in last phrase we store it
	 * Person:if a Person was mentioned in last phrase we store it
	 * 
	 * 
	 * 
	 * */
	public static JsonObject customVariable;
	
	
	public static void update(Frase fraseAnalizzata){
		
		
		
		customVariable=new JsonObject();
		if(FeedRetriever.getFeed(0)!=null)customVariable.addProperty( "news",FeedRetriever.getFeed(0));
		
		customVariable.addProperty("sentiment",fraseAnalizzata.getSentiment().getSentiment().getType().toString());
		for (int i = 0; i < fraseAnalizzata.getEntitiesAlchemy().size(); i++) {
	
		customVariable.addProperty( fraseAnalizzata.getEntitiesAlchemy().get(i).getType(),fraseAnalizzata.getEntitiesAlchemy().get(i).getText());
		
			
			
		}
		
		/*
		try {
			customVariable.addProperty("news", FeedRetriever.giveFeed());
		} catch (IllegalArgumentException | FeedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		System.out.println("la mia customVariable:"+customVariable.toString());
	}
	
	

	
	
	

	





















}
