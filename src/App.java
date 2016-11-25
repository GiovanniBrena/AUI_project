
public class App{

	public static void main(String[] args) {
		
		// get VoiceListener instance
		VoiceListener voiceListener = VoiceListener.getInstance();
		// start listening
		voiceListener.startListening();
		
	}
	

}