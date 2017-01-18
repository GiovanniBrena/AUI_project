package client;


import java.util.List;

import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentEmotion;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Entity;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Sentiment.SentimentType;

import Model.Frase;
import ibmAPI.Alchemy;
import ibmAPI.Translator;
import database.DBManager;

public class SentimentAnalyzer {
	
	public static void analyzeString(String input, int id){
		
		System.out.println(input);
		
				
				String inputENG=Translator.translateString(input);
				Frase temp=Alchemy.sendRequestReturnFrase(inputENG);
				temp.setFraseOriginale(input);
				
				System.out.println(inputENG);
				
				DocumentSentiment sentiment =  temp.getSentiment();
				DocumentEmotion emotions = temp.getEmotion();
				
				List <Entity> entities=temp.getEntitiesAlchemy();
				System.out.println("Entities trovate"+entities.toString());
				ContextManager.update(temp);
				
				
				SentimentType sentimentType =  sentiment.getSentiment().getType();
				System.out.println("prov: "+sentimentType.toString());
				Double sentimentScore;
				if(sentiment.getSentiment().getScore()==null)sentimentScore=(double) 0;
				else sentimentScore = sentiment.getSentiment().getScore().doubleValue();
				System.out.println("Sentiment Score:-"+sentimentScore+"--");
				Double angerValue = emotions.getEmotion().getAnger().doubleValue();
				Double disgustValue = emotions.getEmotion().getDisgust().doubleValue();
				Double fearValue = emotions.getEmotion().getFear().doubleValue();
				Double joyValue = emotions.getEmotion().getJoy().doubleValue();
				Double sadnessValue = emotions.getEmotion().getSadness().doubleValue();
				if(id!=0){
				DBManager db = new DBManager();
				
				
				db.updateSentiment(sentimentScore, id);
				db.updateEmotion( angerValue, disgustValue, fearValue, joyValue, sadnessValue, id);
				}
				System.out.println("----------------------");
				System.out.println("- SENTIMENT ANALYSIS -");
				System.out.println("Type: " + sentimentType);
				System.out.println("Score: " + sentimentScore);
				System.out.println("Anger: " + angerValue);
				System.out.println("Disgust: " + disgustValue);
				System.out.println("Fear: " + fearValue);
				System.out.println("Joy: " + joyValue);
				System.out.println("Sadness: " + sadnessValue);
				System.out.println("----------------------");
				
				
			}
		
		
		
	
}
