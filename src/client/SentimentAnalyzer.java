package client;

import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentEmotion;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Sentiment.SentimentType;

import Model.Frase;
import ibmAPI.Alchemy;
import ibmAPI.Translator;

public class SentimentAnalyzer {
	
	public static void analyzeString(String input){
		
		Thread analyzerThread = new Thread() {
			
			public void run(){
				String inputENG=Translator.translateString(input);
				Frase temp=Alchemy.sendRequestReturnFrase(inputENG);
				temp.setFraseOriginale(input);
				
				DocumentSentiment sentiment =  temp.getSentiment();
				DocumentEmotion emotions = temp.getEmotion();
				
				SentimentType sentimentType =  sentiment.getSentiment().getType();
				Double sentimentScore = sentiment.getSentiment().getScore().doubleValue();
				
				Double angerValue = emotions.getEmotion().getAnger().doubleValue();
				Double disgustValue = emotions.getEmotion().getDisgust().doubleValue();
				Double fearValue = emotions.getEmotion().getFear().doubleValue();
				Double joyValue = emotions.getEmotion().getJoy().doubleValue();
				Double sadnessValue = emotions.getEmotion().getSadness().doubleValue();
				
				
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
		};
		
		analyzerThread.start();
	}
}