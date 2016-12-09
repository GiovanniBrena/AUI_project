package client;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesiser.Synthesiser;
import net.sourceforge.javaflacencoder.FLACFileWriter;

import client.TestClient;
import database.DBManager;


public class VoiceListener {

	private static VoiceListener instance = null;
	
	private final int NOISE_THRESHOLD = 20;
	public enum Mode {
	    REPEATER, CONVERSATION, MANUAL
	}
	private Mode mode = Mode.REPEATER;
	
	private Thread listeningThread;
	
	private static DBManager db;
	
	
	
	public static VoiceListener getInstance(){
		if(instance==null) {instance = new VoiceListener();}
		return instance;
	}
	
	public void setMode(Mode m) {mode = m;}
	
	public void startListening(){
		
		listeningThread = new Thread() {
		    public void run() {
		    	
		    	db = new DBManager();
		    	db.newDbConvers();
		    	
		    	if(mode==Mode.CONVERSATION) {
		    		String helloString = Conversation.initConversation();
		    		App.print("ABI: " + helloString);
		    		db.postPhrase(helloString, "ABIapi");
		    		Synthesiser synth = new Synthesiser("it");		                
            		InputStream is=null;
					try {
						is = synth.getMP3Data(helloString);
					} catch (IOException e) {
						e.printStackTrace();
					} 
            		inputStreamToFile(is, "res/conv.mp3");
            		Mp3Player.getInstance().playMp3File(new File("res/conv.mp3"));
		    	}
		    	
		    	else if(mode==Mode.MANUAL) {
		    		TestClient.getInstance().startClient();
		    	}
		    	
		    	MicrophoneAnalyzer mic = new MicrophoneAnalyzer(FLACFileWriter.FLAC);
			    mic.setAudioFile(new File("AudioTestNow.flac"));
			    System.out.println("ACTIVATING MICROPHONE");
			    while(true){
			        mic.open();

			        int volume = mic.getAudioVolume();
			        boolean isSpeaking = (volume > NOISE_THRESHOLD);
			        if(isSpeaking){
			            try {
			                System.out.println("RECORDING...");
			                mic.captureAudioToFile(mic.getAudioFile());//Saves audio to file.
			                do{
			                    Thread.sleep(1000);//Updates every second
			                }
			                while(mic.getAudioVolume() > NOISE_THRESHOLD);
			                System.out.println("Recording Complete!");
			                System.out.println("Recognizing...");
			                Recognizer rec = new Recognizer(Recognizer.Languages.ITALIAN, Constants.GOOGLE_API_KEY);
			                GoogleResponse response = rec.getRecognizedDataForFlac(mic.getAudioFile(), 3);
			                
			                
			                // got a valid audio recognition
			                if(response.getResponse()!=null) {
			                	db.postPhrase(response.getResponse(), "Elderly");
			                	
			                	displayResponse(response);//Displays output in Console
		                		App.print("User: " + response.getResponse());
		                	
		                		if(mode==Mode.REPEATER) {
		                			Synthesiser synth = new Synthesiser("it");		                
		                			InputStream is = synth.getMP3Data(response.getResponse()); 
		                			inputStreamToFile(is, "res/rec.mp3");
		                			Mp3Player.getInstance().playMp3File(new File("res/rec.mp3"));
		                		}
		                	
		                		else if (mode==Mode.CONVERSATION) {
			                	
			                		String ABIresponse = Conversation.sendRequest(response.getResponse());
			                		
			                		App.print("ABI: " + ABIresponse);
			                		db.postPhrase(ABIresponse, "ABIapi");
		    		    			Synthesiser synth = new Synthesiser("it");		                
		                			InputStream is=null;
		    						try {
		    							is = synth.getMP3Data(ABIresponse);
		    						} catch (IOException e) {
		    							e.printStackTrace();
		    						} 
		                			inputStreamToFile(is, "res/conv.mp3");
		                			Mp3Player.getInstance().playMp3File(new File("res/conv.mp3"));
		                		}
		                		
		                		else if (mode==Mode.MANUAL) {
		                			TestClient.getInstance().sendToServer(response.getResponse());
		                		}
			                }
			                
			                
			                System.out.println("Looping back");//Restarts loops
			            } catch (Exception e) {
			                e.printStackTrace();
			                System.out.println("Error Occured");
			            }
			            finally{
			                mic.close();//Makes sure microphone closes on exit.
			            }
			        }
			    }
			}
			
			
			private void displayResponse(GoogleResponse gr){
			    if(gr.getResponse() == null){
			        System.out.println((String)null);
			        return;
			    }
			    System.out.println("Google Response: " + gr.getResponse());
			    System.out.println("Google is " + Double.parseDouble(gr.getConfidence())*100 + "% confident in"
			            + " the reply");
			    
			    // Print alternative responses
			    /* 
			    System.out.println("Other Possible responses are: ");
			    for(String s: gr.getOtherPossibleResponses()){
			        System.out.println("\t" + s);
			    }
			    */
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
	
	
	
	public static void inputStreamToFile(InputStream is, String path) {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			// read this file into InputStream
			inputStream = is;

			// write the inputStream to a FileOutputStream
			outputStream =
	                    new FileOutputStream(new File(path));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					db.updateOraFine();
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					db.updateOraFine();
					outputStream.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
	
}

