package client;
import java.io.File;

import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import net.sourceforge.javaflacencoder.FLACFileWriter;
import uiFace.FaceComponent;
import client.TestClient;
import database.DBManager;


public class VoiceListener {

	private static VoiceListener instance = null;

	private MicrophoneAnalyzer mic;
	private final int NOISE_THRESHOLD = 20;
	public enum Mode {
		REPEATER, CONVERSATION, MANUAL
	}
	private Mode mode = Mode.REPEATER;
	private boolean useSentiments = true;

	
	
	
	private Thread listeningThread;
	private DBManager db;

	protected int idTupla;
	protected int idResp;

	public static VoiceListener getInstance(){
		if(instance==null) {instance = new VoiceListener();}
		return instance;
	}


	public void setMode(Mode m) {mode = m;}
	public void setUseSentiment(boolean value) {useSentiments = value;}
	public boolean isUsingSentiment() {return useSentiments;}
	public int getMicrofoneVolume() {
		if(mic==null) { return 0;}
		else {return mic.getAudioVolume();}
	}

	public void startListening(){

		listeningThread = new Thread() {


			public void run() {

				/* ---- INITIALIZATION ---- */

				System.out.println("INITIALIZING...");
				//instantiate DB, create and store new Conversation on DB
				db = new DBManager();
				db.newDbConvers();

				if(mode==Mode.CONVERSATION) {
					// initialize conversation 
					String helloString = Conversation.initConversation();
					App.print("ABI: " + helloString);
					// store into DB
					db.postPhrase(helloString, "ABIapi");

					// get the default initial phrase to speech & play file
					File audioResponse = AudioFileManager.synthesiseAudioToFile(helloString, "res/conv.mp3");
					Mp3Player.getInstance().playMp3File(audioResponse);
				}

				// initialize client
				else if(mode==Mode.MANUAL) {
					TestClient.getInstance().startClient();
				}

				// open microphone listening
				mic = new MicrophoneAnalyzer(FLACFileWriter.FLAC);
				
				System.out.println("ACTIVATING MICROPHONE");
				int countMic = 0;

				/* ---- END INITIALIZATION ---- */



				/* ---- LISTENING LOOP ---- */

				while(true){
					countMic++;
					mic.setAudioFile(new File("UserRec"+countMic+".flac"));
					mic.open();
					// get current volume
					int volume = mic.getAudioVolume();
					boolean isSpeaking = (volume > NOISE_THRESHOLD);

					// start recording if volume > NOISE_THRESHOLD
					if(isSpeaking){
						try {
							System.out.println("RECORDING...");
							//Saves audio to file.
							mic.captureAudioToFile(mic.getAudioFile());
							//Updates every second
							do{
								Thread.sleep(1000);
							}
							while(mic.getAudioVolume() > NOISE_THRESHOLD);
							mic.close(); //chiudo il microfono appena finito di registrare
							// finish recording
							//App.face.think(true);

							System.out.println("Recording Complete! Recognizing...");

							
						
							// instantiate Google Recognizer and get response
							Recognizer rec = new Recognizer(Recognizer.Languages.ITALIAN, Constants.GOOGLE_API_KEY);
							GoogleResponse response = rec.getRecognizedDataForFlac(mic.getAudioFile(), 3);

							
							// invalid recognition
							if(response.getResponse()==null) { 
								System.out.println("Riconoscimento non valido");
								//App.face.think(false);
								continue; 
							}

							
							
							//store response into DB
							idTupla = db.postPhrase(response.getResponse(), "Elderly");
							
							//save audio 
							AudioFileManager.saveUserRec(idTupla, mic);
							
							//Displays output in Console
							displayResponse(response);
							App.print("User: " + response.getResponse());

							if(useSentiments) {
								// start Sentiment Analysis
								//SentimentAnalyzer.analyzeString(response.getResponse(), idTupla);
							}
							
							// switch by mode
							switch(mode) {	
							case REPEATER: {
								String audioUrl = createAudioUrl(idTupla);
								db.updateAudioPath(idTupla);
								// just repeat
								File audioResponse = 
										AudioFileManager.synthesiseAudioToFile(response.getResponse(), audioUrl);
								Mp3Player.getInstance().playMp3File(audioResponse);
								break;
							}

							case CONVERSATION: {
								// get conversation response
								String ABIresponse = Conversation.sendRequest(response.getResponse());
								App.print("ABI: " + ABIresponse);

								// store ABI response into DB
								idResp = db.postPhrase(ABIresponse, "ABIapi");
								db.updateAudioPath(idResp);
								// read response
								File audioResponse = 
										AudioFileManager.synthesiseAudioToFile(ABIresponse, createAudioUrl(idResp));
								Mp3Player.getInstance().playMp3File(audioResponse);
								break;
							}

							case MANUAL: {
								
								db.updateAudioPath(idTupla);
								
								// just send text to server
								TestClient.getInstance().sendToServer(response.getResponse());
								break;
							}
							}                
							//Restarts listening loops
							//App.face.think(false);
							System.out.println("Looping back");

						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Error Occured");
						}
						finally{
							db.updateOraFine();
							//mic.close();//Makes sure microphone closes on exit.
						}
					}
				}
				/* ---- END LISTENING LOOP ---- */
			}

			private String createAudioUrl(int idTupla) {
				String myUrl = "res/conv"+idTupla+".mp3";
				return myUrl;
			}
			
			private String createElderlyAudioUrl(int idTupla) {
				String myUrl = "res/convUser"+idTupla+".mp3";
				return myUrl;
			}


			private void displayResponse(GoogleResponse gr){
				if(gr.getResponse() == null){
					System.out.println((String)null);
					return;
				}
				System.out.println("Google Response: " + gr.getResponse());
				System.out.println("Google is " + Double.parseDouble(gr.getConfidence())*100 + "% confident in"
						+ " the reply");

			}  
		};

		listeningThread.start();
	}   


	@SuppressWarnings("deprecation")
	public void stopListening(){
		if(listeningThread!=null) {listeningThread.stop();
		db.updateOraFine();
		}
	}

}

