package TestServer;

import java.io.*;
import java.net.*;

public class Client {

	  String serverurl = "192.168.1.166";
	  int serverport = 8189;
	 
	  /**  Instantiates an instance of the SimpleClient class and initilaizes it.
	  */
	  public static void main(String[] args){
	    Client simpleclient = new Client();
	    simpleclient.init();
	  }
	 
	  /**  Connects to the SimpleServer on port 8189 and sends a few demo lines
	  *  to the server, and reads, displays the server reply,
	  *  then issues a Bye command signaling the server to quit.
	  */
	  public void init(){
	    Socket socket = null;    
	    try{
	      System.out.println("Connecting to " + serverurl + " on port " + serverport);
	      socket = new Socket(serverurl,serverport);
	      //Set socket timeout for 10000 milliseconds or 10 seconds just 
	      //in case the host can't be reached
	      socket.setSoTimeout(0);
	      System.out.println("Connected.");
	      
	      InputStreamReader inputstreamreader = new InputStreamReader(socket.getInputStream());
	      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
	      //establish an printwriter using the output streamof the socket object
	      //and set auto flush on    
	      PrintWriter printwriter = new PrintWriter(socket.getOutputStream(),true);
	      printwriter.println("I'm a client!");

	      //printwriter.println("Bye");
	      String lineread = "";
	      while ((lineread = bufferedreader.readLine()) != null){
	        System.out.println("Received from Server: " + lineread);
	      }
	      System.out.println("Closing connection.");
	      bufferedreader.close();
	      inputstreamreader.close();
	      printwriter.close();
	      socket.close();
	      System.exit(0);
	 
	    }catch(UnknownHostException unhe){
	      System.out.println("UnknownHostException: " + unhe.getMessage());
	    }catch(InterruptedIOException intioe){
	      System.out.println("Timeout while attempting to establish socket connection.");
	    }catch(IOException ioe){
	      System.out.println("IOException: " + ioe.getMessage());
	    }finally{
	      try{
	        socket.close();
	      }catch(IOException ioe){
	        System.out.println("IOException: " + ioe.getMessage());
	      }
	    }
	  }
	}
