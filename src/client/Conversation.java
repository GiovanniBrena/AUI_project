package client;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.sun.syndication.io.FeedException;
import com.teamdev.jxbrowser.chromium.JSObject;
import com.ibm.watson.developer_cloud.conversation.v1.model.Entity;
public class Conversation {
	
	private static Map<String, Object> context;
	
	
	public static String sendRequest(String input){
		
		if(input==null) {return initConversation();}
		
		ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2016_07_11);
		service.setUsernameAndPassword(Constants.WATSON_CONVERSATION_USER, Constants.WATSON_CONVERSATION_PSW);
		
		/*HashMap<String,String> headers=new HashMap<String,String>();
		headers.put("User-Agent",
				"watson-apis-java-sdk/3.5.0 (java.vendor=Oracle Corporation; java.version=1.8.0_45; os.arch=amd64; os.name=Windows 8.1; os.version=6.3)"
				);
		service.setDefaultHeaders(headers);
		
		*/
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("version.properties");
		System.out.println(inputStream.toString());
		MessageRequest newMessage;
		if(context!=null) {
			System.out.println("contesto: "+context.toString());
			context.put("customVariable", ContextManager.customVariable);
			
			newMessage = new MessageRequest.Builder().
				inputText(input).
				context(context).
			
				build();
		
		System.out.println("\n\n noi mandiamo:" +newMessage.toString());
		}
		else {
			context=(new HashMap<String, Object>());
			newMessage = new MessageRequest.Builder().
					inputText(input).
					context(context).
					build();
			newMessage.context().put("username", "Sandro");
			System.out.println("\n\n noi mandiamo:" +newMessage.toString());
			}
	
		
	
		
		
		MessageResponse response = service.message(Constants.WATSON_WORKSPACE_ID, newMessage).execute();
		
		context = response.getContext();
		
		
		System.out.println("contesto che vado a reimpostare"+context);
		String result="";
		for (int i = 0; i < response.getText().size(); i++) {
			result+=response.getText().get(i);
		}
		
		return result;
	}
	
	public static String initConversation() {
		
		
		/*
		try {
			FeedRetriever.giveFeed();
		} catch (IllegalArgumentException | FeedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		String response = sendRequest("Ciao ABI");
		
		//System.out.println(response);
		return response;
	}
	
}
