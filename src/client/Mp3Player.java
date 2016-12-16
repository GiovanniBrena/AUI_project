package client;
import java.io.*;
import javax.sound.sampled.*;

import uiFace.FaceComponent.FaceState;

public class Mp3Player {

	private static Mp3Player instance = null;
	
	
	public static Mp3Player getInstance(){
		if(instance==null) {instance = new Mp3Player();}
		return instance;
	}
	
	
	public void playMp3File(File file) {
		AudioInputStream din = null;
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(file);
			AudioFormat baseFormat = in.getFormat();
			AudioFormat decodedFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			din = AudioSystem.getAudioInputStream(decodedFormat, in);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			if(line != null) {
				line.open(decodedFormat);
				byte[] data = new byte[4096];
				// Start
				App.faceSpeak(true);
				WebApp.faceSpeak(true);
				
				line.start();
				
				int nBytesRead;
				while ((nBytesRead = din.read(data, 0, data.length)) != -1) {	
					line.write(data, 0, nBytesRead);
				}
				// Stop
				App.faceSpeak(false);
				WebApp.faceSpeak(false);
				line.drain();
				line.stop();
				line.close();
				din.close();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(din != null) {
				try { din.close(); } catch(IOException e) { }
			}
		}
		
	}
	
	
}


