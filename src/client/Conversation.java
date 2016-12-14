package client;
import java.util.Map;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

public class Conversation {
	
	private static Map<String, Object> context;
	
	public static String sendRequest(String input){
		
		if(input==null) {return initConversation();}
		
		ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2016_07_11);
		service.setUsernameAndPassword(Constants.WATSON_CONVERSATION_USER, Constants.WATSON_CONVERSATION_PSW);

		MessageRequest newMessage;
		if(context!=null) {
		newMessage = new MessageRequest.Builder().
				inputText(input).
				context(context).
				build();
		}
		else {
			newMessage = new MessageRequest.Builder().
					inputText(input).
					build();
			}
	
		MessageResponse response = service.message(Constants.WATSON_WORKSPACE_ID, newMessage).execute();
		context = response.getContext();
		
		//System.out.println(response);
		
		return response.getText().get(0);
	}
	
	public static String initConversation() {
		context = null;
		String response = sendRequest("");
		//System.out.println(response);
		return response;
	}
	
}
