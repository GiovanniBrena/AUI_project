package ibmAPI;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

import com.ibm.watson.developer_cloud.language_translator.v2.*;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;

import Model.Conversazione;
import Model.Frase;
import javafx.application.Application;
import javafx.stage.Stage;


@SuppressWarnings("restriction")
public  class testApi  {

	
	private Conversazione conversation;
	
	public testApi(){
		Instant instant=Instant.now();
		conversation=new Conversazione(new ArrayList<Frase>(), java.util.Date.from(instant));
		
		
	}
	
	
	public static void print(String s){
	
			
		}
		
		/*public static void main(String[] args) {
			
			
			Scanner in=new Scanner(System.in);
			
			
			String input="";
			while (input!="ESCI") {
				System.out.println("Traduttore,introduci la frase o scrivi ESCI");
				input=in.nextLine();
				String tradotta=Translator.translateString(input);
				System.out.println("Frase tradotta:"+tradotta);
				String analisiTesto=Alchemy.sendRequest(tradotta);
				System.out.println("ANALISI:\n"+analisiTesto);
			}
		
		}
*/
	public void sendString(String input){
		if(conversation==null){
			Instant instant=Instant.now();
			conversation=new Conversazione(new ArrayList<Frase>(), java.util.Date.from(instant));
								}
		String inputENG=Translator.translateString(input);
		Frase temp=Alchemy.sendRequestReturnFrase(inputENG);
		System.out.println("frase ritornata"+temp);
		temp.setFraseOriginale(input);
		
		
		
		guiAlchemy.printInfoConversation(conversation);
		guiAlchemy.printAlchemyData(temp);
		
		
	}
	
	
	

		
		
}
