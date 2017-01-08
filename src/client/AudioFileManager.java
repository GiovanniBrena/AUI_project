package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.synthesiser.Synthesiser;

public class AudioFileManager {
	
	public static File synthesiseAudioToFile(String text, String fileUrl){
		// get the default initial phrase to speech
		Synthesiser synth = new Synthesiser("it");		                
		InputStream is=null;
		try {
			is = synth.getMP3Data(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// create vocal file
		inputStreamToFile(is, fileUrl);
		return new File(fileUrl);
	}
	
	private static void inputStreamToFile(InputStream is, String path) {
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
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
	
	public static void saveUserRec(int convId, Microphone mic) throws FileNotFoundException{
		
		System.out.println("Saving user rec.");
		File f = mic.getAudioFile();
		try {
			InputStream is = new FileInputStream(f);
			OutputStream outstream = new FileOutputStream(new File("res/convUser"+convId+".flac")); //funziona anche mettendo .mp3 ma non con il mac 
			
			byte[] buffer = new byte[4096];
			int len;
			while ((len = is.read(buffer)) > 0) {
			    outstream.write(buffer, 0, len);
			}
			outstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		System.out.println("Saving user rec.");
//		File rec = new File("res/convUser"+convId+".mp3");
//		rec = mic.getAudioFile();
		
		
		System.out.println("User rec saved.");
	}

}
