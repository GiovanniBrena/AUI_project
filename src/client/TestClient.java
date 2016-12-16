package client;

import java.io.*;
import java.net.*;

import database.DBManager;

public class TestClient {
	
	private Thread connectionThread;

	public String serverurl = Constants.SERVER_URL;
	public int serverport = Constants.SERVER_PORT;
	private static PrintWriter printwriter;
	private static TestClient instance; 

	private DBManager db;

	private int idTupla;
	  
	  public static TestClient getInstance(){
		  if(instance==null) { instance = new TestClient();}
		  return instance;
	  }
	 
	  private TestClient(){}
	  
	  public void setServerUrl(String ip){
		  serverurl = ip;
	  }
	  public void setServerPort(int port){
		  serverport = port;
	  }
	  
	  @SuppressWarnings("restriction")
	public void startClient(){
		  //serverurl = App.ipField.getText();
		  //serverport = Integer.parseInt(App.portField.getText());
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
	        
	        idTupla = db.postPhrase(lineread, "Server");
	        db.updateAudioPath(idTupla);
	        
	        App.print("ABI: " + lineread);
	        File audio = AudioFileManager.synthesiseAudioToFile(lineread, createAudioUrl(idTupla));
	        Mp3Player.getInstance().playMp3File(audio);
	        
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
	  
	  private String createAudioUrl(int idTupla) {
			String myUrl = "res/conv"+idTupla+".mp3";
			return myUrl;
		}
	  
	  
	}
