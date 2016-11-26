import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesiser.Synthesiser;
import net.sourceforge.javaflacencoder.FLACFileWriter;




@SuppressWarnings("restriction")
public class VoiceListener {

	private static VoiceListener instance = null;
	
	private final int NOISE_THRESHOLD = 8; 
	
	
	
	public static VoiceListener getInstance(){
		if(instance==null) {instance = new VoiceListener();}
		return instance;
	}
	
	
	
	public void startListening(){
		
		Thread listeningThread = new Thread() {
		    public void run() {
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
			                
			                if(response.getResponse()!=null) {
			                	displayResponse(response);//Displays output in Console  
			                	Synthesiser synth = new Synthesiser("it");		                
			                	InputStream is = synth.getMP3Data(response.getResponse()); 
			                	inputStreamToFile(is);
			                	
			                	Mp3Player.getInstance().playMp3File(new File("res/rec.mp3"));
			                	
			                }
			                
			                
			                System.out.println("Looping back");//Restarts loops
			            } catch (Exception e) {
			                // TODO Auto-generated catch block
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
	
	
	private void inputStreamToFile(InputStream is) {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			// read this file into InputStream
			inputStream = is;

			// write the inputStream to a FileOutputStream
			outputStream =
	                    new FileOutputStream(new File("res/rec.mp3"));

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
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
	
}

