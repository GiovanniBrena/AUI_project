package client;

import java.io.*;
import java.net.*;

import com.darkprograms.speech.synthesiser.Synthesiser;

import database.DBManager;

public class TestClient {
	
	private Thread connectionThread;

	public String serverurl = Constants.SERVER_URL;
	public int serverport = Constants.SERVER_PORT;
	private static PrintWriter printwriter;
	private static TestClient instance; 

	private DBManager db;
	  
	  public static TestClient getInstance(){
		  if(instance==null) { instance = new TestClient();}
		  return instance;
	  }
	  
	  private TestClient(){}
	  
	  
	  @SuppressWarnings("restriction")
	public void startClient(){
		  serverurl = App.ipField.getText();
		  serverport = Integer.parseInt(App.portField.getText());
			connectionThread = new Thread() {
			    public void run() {
			    	init();
			    }
			};
			connectionThread.start();
		}
		
		@SuppressWarnings("deprecation")
		public void stopClient(){
			if(connectionThread!=null) {connectionThread.stop();}
		}
	  
	 
	  /**  Instantiates an instance of the SimpleClient class and initilaizes it.
	  
	  public static void main(String[] args){
	    TestClient client = new TestClient();
	    client.init();
	  }
	 
	  /**  Connects to the SimpleServer on port 8189 and sends a few demo lines
	  *  to the server, and reads, displays the server reply,
	  *  then issues a Bye command signaling the server to quit.
	  */
	  private void init(){
	    Socket socket = null;    
	    try{
	      System.out.println("Connecting to " + serverurl + " on port " + serverport);
	      socket = new Socket(serverurl,serverport);
	      //Set socket timeout for 10000 milliseconds or 10 seconds just 
	      //in case the host can't be reached
	      socket.setSoTimeout(0);
	      System.out.println("Connected.");
	      
	      db = new DBManager(); //db connection
	      
	      InputStreamReader inputstreamreader = new InputStreamReader(socket.getInputStream());
	      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
	      //establish an printwriter using the output streamof the socket object
	      //and set auto flush on    
	      printwriter = new PrintWriter(socket.getOutputStream(),true);
	      printwriter.println("I'm a client!");

	      //printwriter.println("Bye");
	      String lineread = "";
	      while ((lineread = bufferedreader.readLine()) != null){
	        System.out.println("Received from Server: " + lineread);
	        
	        db.postPhrase(lineread, "Server");
	        
	        App.print("ABI: " + lineread);
	        Synthesiser synth = new Synthesiser("it");		                
			InputStream is = synth.getMP3Data(lineread); 
			VoiceListener.inputStreamToFile(is, "res/rec.mp3");
			Mp3Player.getInstance().playMp3File(new File("res/rec.mp3"));
	        
	      }
	      System.out.println("Closing connection.");
	      bufferedreader.close();
	      inputstreamreader.close();
	      printwriter.close();
	      socket.close();
	      System.exit(0);
	 
	    }catch(UnknownHostException unhe){
	      System.out.println("UnknownHostException: " + unhe.getMessage());
	      App.print("ABI Server error.");
	    }catch(InterruptedIOException intioe){
	      System.out.println("Timeout while attempting to establish socket connection.");
	      App.print("Connection timeout.");
	    }catch(IOException ioe){
	      System.out.println("IOException: " + ioe.getMessage());
	      App.print("ABI Server not available.");
	    }finally{
	      try{
	        socket.close();
	      }catch(IOException ioe){
	        System.out.println("IOException: " + ioe.getMessage());
	      }
	    }
	  }
	  
	  
	  public void sendToServer(String text) {
		  if(printwriter==null) {return;}
		  printwriter.println(text);
	  }
	  
	  
	}
