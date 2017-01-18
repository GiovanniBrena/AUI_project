package client;

import java.util.Scanner;

public class consoleConversation {

	
	
	
	
	
	public static void main(String[] args) {
		
		
		System.out.println(Conversation.sendRequest(null));
		Scanner scanner = new Scanner(System.in);
		String input=scanner.nextLine();
		while(input!="esci da qui"){
				SentimentAnalyzer.analyzeString(input, 0);
				System.out.println(Conversation.sendRequest(input));
				System.out.println("cosa rispondi?");
				input=scanner.nextLine();
			
			
			
			
		}
		
		
		
		
	}
}
