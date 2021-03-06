package testServer;

import java.io.*;
import java.net.*;
import java.util.Calendar;

import javax.swing.AbstractButton;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



@SuppressWarnings("restriction")
public class Server extends Application {
	
	boolean isRunning = false; 
	boolean hasClient = false;
	static TextArea console;
	static Canvas stateCanvas;
	static Text stateLabel;
	static Button startListening;
	static TextField inputField;
	
	private Thread serverThread;
	
	PrintWriter printwriter;

	  public static void main(String[] args){
		  launch(args);

	  }
	  
		@Override
		public void start(Stage primaryStage) throws Exception {
			
			setUpUI(primaryStage);
			//Server simpleserver = new Server();
		    //simpleserver.init();
		}
	 
		public void startServer(){
			serverThread = new Thread() {
			    public void run() {
			    	
			    	try {
						initServer();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			};
			serverThread.start();
			isRunning=true;
			drawStateIcon();
		}
		
		public void stopServer(){
			if(serverThread!=null) {serverThread.stop();}
			isRunning=false;
			drawStateIcon();
		}
		
		
	  /**  Sets up a ServerSocket and listens on port 8189.
	 * @throws Exception 
	  */
	  private void initServer() throws Exception{
	    ServerSocket serversocket = null;
	    Socket socket = null;
	    try{
	      //establish a server socket monitoring port 8189 
	      //port 8189 is not used by any services
	      serversocket = new ServerSocket(8189);
	     
	      System.out.println("Listening on port 8189");
	      
	      print("Server Active");
	     
	      print("IP of the server if on same network:"+Inet4Address.getLocalHost().getHostAddress());
	      print("IP of the server from external network:"+IpChecker.getIp());
	      print("Waiting connections on port 8189");
	      
	      isRunning = true;
	      drawStateIcon();
	      
	      //wait indefinitely until a client connects to the socket
	      socket = serversocket.accept();
	 
	      //set up communications for sending and receiving lines of text data
	      //establish a bufferedreaderr from the input stream provided by the socket object
	      InputStreamReader inputstreamreader = new InputStreamReader(socket.getInputStream());
	      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
	 
	      //establish an printwriter using the output streamof the socket object
	      //and set auto flush on    
	      printwriter = new PrintWriter(socket.getOutputStream(),true);
	 
	      String datetimestring = (Calendar.getInstance()).getTime().toString();
	      printwriter.println("Buongiorno Antonio, come stai?");
	      
	      hasClient=true;
	      drawStateIcon();
	      print("Client connected");
	 
	      String lineread = "";
	      boolean done = false;
	   
	      
	      // connection loop
	      while (((lineread = bufferedreader.readLine()) != null) && (!done)){
	        System.out.println("Received from Client: " + lineread);
	        
	       // printwriter.println("You sent: " + lineread);
	        
	        print(lineread);
	        
	        if (lineread.compareToIgnoreCase("Bye") == 0) done = true;
	      }
	      
	      // end connection
	      System.out.println("Closing connection.");
	      bufferedreader.close();
	      inputstreamreader.close();
	      printwriter.close();
	      socket.close();
	      hasClient=false;
	      isRunning = false;
		  drawStateIcon();
	    }catch(UnknownHostException unhe){
	      System.out.println("UnknownHostException: " + unhe.getMessage());
	    }catch(InterruptedIOException intioe){
	      System.out.println("Timeout while attempting to establish socket connection.");
	    }catch(IOException ioe){
	      System.out.println("IOException: " + ioe.getMessage());
	    }finally{
	    	hasClient=false;
	    	isRunning = false;
		    drawStateIcon();
	      try{
	        socket.close();
	        serversocket.close();
	      }catch(IOException ioe){
	        System.out.println("IOException: " + ioe.getMessage());
	      }
	    }
	  }


	  @SuppressWarnings({ "unchecked", "rawtypes" })
	private void setUpUI(Stage primaryStage){
			//The primaryStage is the top-level container
			primaryStage.setTitle("ABI SERVER");

			//The BorderPane has the same areas laid out as the
			//BorderLayout layout manager
			BorderPane componentLayout = new BorderPane();
			componentLayout.setPadding(new Insets(20,0,20,20));
			
			VBox vbox = new VBox();
		    vbox.setPadding(new Insets(10));
		    vbox.setSpacing(8);

		    Text title = new Text("STATE");
		    stateLabel = new Text("active");
		    stateLabel.setFont(Font.font("Arial", FontWeight.LIGHT, 11));
		    stateLabel.setTextAlignment(TextAlignment.CENTER);
	        stateCanvas = new Canvas(50, 25);	      
	        drawStateIcon();

		    vbox.getChildren().add(title);
		    vbox.getChildren().add(stateCanvas);
		    vbox.getChildren().add(stateLabel);
		    
		    BorderPane.setAlignment(vbox, Pos.CENTER_LEFT);
		    
		    startListening = new Button("START SERVER");
		    BorderPane.setAlignment(startListening, Pos.BASELINE_CENTER);
		    startListening.setOnAction(new EventHandler() {
		        @Override
		        public void handle(Event actionEvent) {
		        	if(isRunning) {
		        		stopServer();
		        		startListening.setText("START SERVER");
		        		inputField.setText("");
		        	}
		        	else {
		        		startServer();
		        		startListening.setText("STOP SERVER");
		        		}
		        }
		    });
		    
		    
		    
		    
		    
		    VBox vbox2 = new VBox();
		    vbox2.setPadding(new Insets(10));
		    vbox2.setSpacing(8);

		    Text title2 = new Text("CONSOLE");
		    console = new TextArea();
		    console.setEditable(false);
		    console.textProperty().addListener(new ChangeListener<Object>() {
		        @Override
		        public void changed(ObservableValue<?> observable, Object oldValue,
		                Object newValue) {
		            console.setScrollTop(Double.MAX_VALUE); 
		        }
		    });
		    
		    inputField = new TextField ();
		    Button sendButton = new Button("Send");
		    sendButton.setOnAction(new EventHandler() {
		        @Override
		        public void handle(Event actionEvent) {
		        	if(isRunning&&hasClient) {
		        		System.out.println("sto scrivendo del text");
		        		printwriter.println(inputField.getText());
		        		print("SERVER: " + inputField.getText());
		        		inputField.setText("");
		        	}
		        }
		    });
		    
		    vbox2.getChildren().add(title2);
		    vbox2.getChildren().add(console);
		    vbox2.getChildren().add(inputField);
		    vbox2.getChildren().add(sendButton);
		    
		    vbox2.setAlignment(Pos.TOP_RIGHT);
		  
		    
		 
		    
		    
		    
		   
		    
		    
		    
		    
		    /*RADIO BUTTON EMOTION*/  
		    VBox vbox3 = new VBox();
		    vbox3.setPadding(new Insets(10));
		    vbox3.setSpacing(10);
		    Text title3 = new Text("FACE EMOTION");
		    final ToggleGroup group = new ToggleGroup();
		    
		    
		    RadioButton rb1 = new RadioButton("Normal");
		    rb1.setToggleGroup(group);
		    rb1.setSelected(true);

		    RadioButton rb2 = new RadioButton("Happy");
		    rb2.setToggleGroup(group);
		     
		    RadioButton rb3 = new RadioButton("SuperHappy");
		    rb3.setToggleGroup(group);
		    
		    RadioButton rb4 = new RadioButton("Sad");
		    rb4.setToggleGroup(group);
		    
		    RadioButton rb5 = new RadioButton("Ooh");
		    rb5.setToggleGroup(group);
		    

		    Button sendButtonEmotion = new Button("Send_Face");
		    sendButtonEmotion.setOnAction(new EventHandler() {
		        @Override
		        public void handle(Event actionEvent) {
		        	
		        	if(isRunning&&hasClient) {
		        		RadioButton button=(RadioButton) group.getSelectedToggle();
		        		
		        		printwriter.println("emotion:"+button.getText());
		        		print("SERVER: manda face"+button.getText());
		        		inputField.setText("");
		        	}
		        }
		    });
		    vbox3.getChildren().add(title3);
		    vbox3.getChildren().add(rb1);
		    vbox3.getChildren().add(rb2);
		    vbox3.getChildren().add(rb3);
		    vbox3.getChildren().add(rb4);
		    vbox3.getChildren().add(rb5);
		    vbox3.getChildren().add(sendButtonEmotion);
		  
		    
		    vbox3.setAlignment(Pos.TOP_LEFT);
		    
		    
		    componentLayout.setLeft(vbox);
		    componentLayout.setCenter(vbox2);
		    componentLayout.setRight(vbox3);
		    componentLayout.setBottom(startListening);
		    
			//Add the BorderPane to the Scene
			Scene appScene = new Scene(componentLayout,800,500);

			//Add the Scene to the Stage
			primaryStage.setScene(appScene);
			primaryStage.show();
	  }
	  
	    private void drawStateIcon() {
	    	GraphicsContext gc = stateCanvas.getGraphicsContext2D();
	    	String text;
	    	if(isRunning&&hasClient) {
	    		gc.setFill(Color.GREEN);
	    		text="Connected";
	    	}
	    	else if(isRunning){
	    		gc.setFill(Color.YELLOW);
	    		text="Active";
	    	}
	    	else {
	    		gc.setFill(Color.RED);
	    		text="Not Active";
	    	}
	        
	        gc.setStroke(Color.DARKGRAY);
	        gc.setLineWidth(2);
	        gc.fillOval(2, 2, 20, 20);
	        gc.strokeOval(2, 2, 20, 20);
	        stateLabel.setText(text);
	    }

		public static void print(String s){
			String newline = System.getProperty("line.separator");
			console.appendText(newline+s);
		}
} 