package client;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import uiFace.FaceComponent;
import uiFace.FaceComponent.FaceState;
import javafx.event.*;



@SuppressWarnings("restriction")
public class App extends Application {
	
	public static FaceComponent face;
	
	boolean isActive = false; 
	static TextArea console;
	static TextField ipField;
	static TextField portField;

	public static void main(String[] args) {
	launch(args);
	}

	@Override
	public void init() throws Exception {
	    // On Mac OS X Chromium engine must be initialized in non-UI thread.
	    if (Environment.isMac()) {
	        BrowserCore.initialize();
	    }
	}

	@SuppressWarnings({"unchecked" })
	@Override
	public void start(Stage primaryStage) {

	//The primaryStage is the top-level container
	primaryStage.setTitle("ABI test");

	//The BorderPane has the same areas laid out as the
	//BorderLayout layout manager
	BorderPane componentLayout = new BorderPane();
	componentLayout.setPadding(new Insets(20,0,20,20));
	
	VBox mainLeftVbox = new VBox();
    mainLeftVbox.setPadding(new Insets(10));
    mainLeftVbox.setSpacing(24);

    Text title = new Text("MODE");
    //title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    
    final ToggleGroup group = new ToggleGroup();
    
    RadioButton rb1 = new RadioButton("Repeater");
    rb1.setToggleGroup(group);
    rb1.setSelected(true);
    RadioButton rb2 = new RadioButton("Conversation");
    rb2.setToggleGroup(group);
    RadioButton rb3 = new RadioButton("Manual");
    rb3.setToggleGroup(group);
    
    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
        public void changed(ObservableValue<? extends Toggle> ov,
            Toggle old_toggle, Toggle new_toggle) {
          if (group.getSelectedToggle() != null) {
            if(group.getSelectedToggle()==rb1) { 
            	VoiceListener.getInstance().setMode(VoiceListener.Mode.REPEATER);
            	ipField.setDisable(true);
                portField.setDisable(true);
            }
            else if(group.getSelectedToggle()==rb2) { 
            	VoiceListener.getInstance().setMode(VoiceListener.Mode.CONVERSATION);
            	ipField.setDisable(true);
                portField.setDisable(true);
            }
            else if(group.getSelectedToggle()==rb3) { 
            	VoiceListener.getInstance().setMode(VoiceListener.Mode.MANUAL);
            	ipField.setDisable(false);
                portField.setDisable(false);
            }
          }
        }
      });
    
    VBox optionVBox = new VBox();
    optionVBox.setSpacing(8);
    Text optionTitle = new Text("OPTIONS");
    CheckBox alchemyCheckbox = new CheckBox();
    alchemyCheckbox.setText("Use Sentiment Analysis");
    alchemyCheckbox.setSelected(true);
    alchemyCheckbox.setOnAction(e -> handleAlchemyCheckboxAction(e));
    optionVBox.getChildren().add(optionTitle);
    optionVBox.getChildren().add(alchemyCheckbox);
    
    VBox modeVBox = new VBox();
    modeVBox.setSpacing(8);
    modeVBox.getChildren().add(title);
    modeVBox.getChildren().add(rb1);
    modeVBox.getChildren().add(rb2);
    modeVBox.getChildren().add(rb3);
    
    VBox serverVBox = new VBox();
    serverVBox.setSpacing(8);
    Text serverTitle = new Text("SERVER");
    Text serverLabel = new Text("ip address");
    Text portLabel = new Text("port");
    ipField = new TextField ();
    portField = new TextField ();
    ipField.setText(TestClient.getInstance().serverurl);
    portField.setText(String.valueOf(TestClient.getInstance().serverport));
    
    ipField.setDisable(true);
    portField.setDisable(true);
    
    serverVBox.getChildren().add(serverTitle);
    serverVBox.getChildren().add(serverLabel);
    serverVBox.getChildren().add(ipField);
    serverVBox.getChildren().add(portLabel);
    serverVBox.getChildren().add(portField);
    
    mainLeftVbox.getChildren().add(modeVBox);
    mainLeftVbox.getChildren().add(optionVBox);
    mainLeftVbox.getChildren().add(serverVBox);
    
    
    
    
    BorderPane.setAlignment(mainLeftVbox, Pos.CENTER_LEFT);
    
    Button startListening = new Button("START");
    BorderPane.setAlignment(startListening, Pos.BASELINE_CENTER);
    startListening.setOnAction(new EventHandler() {
        @Override
        public void handle(Event actionEvent) {
        	if(isActive) { 
        		startListening.setText("START"); 
        		rb1.setDisable(false);
        		rb2.setDisable(false);
        		rb3.setDisable(false);
        		alchemyCheckbox.setDisable(false);
        		isActive=false;
        		print("Stop Recognizing.");
        		VoiceListener.getInstance().stopListening();
        		}
        	else {
        		startListening.setText("STOP"); 
        		rb1.setDisable(true);
        		rb2.setDisable(true);
        		rb3.setDisable(true);
        		alchemyCheckbox.setDisable(true);
        		isActive=true;
        		console.setText("");
        		
        		TestClient.getInstance().setServerUrl(ipField.getText());
        		TestClient.getInstance().setServerPort(Integer.valueOf(portField.getText()));
        		
        		VoiceListener.getInstance().startListening();
        		face.setFace(FaceState.mSmile);
        		face.anim();
        		}
        }
    });
    
    VBox consoleVbox = new VBox();
    consoleVbox.setPadding(new Insets(10));
    consoleVbox.setSpacing(8);

    Text title2 = new Text("CONSOLE");
    console = new TextArea();
    console.setEditable(false);
    console.setPrefSize(300, 300);
    console.textProperty().addListener(new ChangeListener<Object>() {
        @Override
        public void changed(ObservableValue<?> observable, Object oldValue,
                Object newValue) {
            console.setScrollTop(Double.MAX_VALUE); 
        }
    });
    Button clearConsole = new Button("clear");
    clearConsole.setOnAction(new EventHandler() {
        @Override
        public void handle(Event actionEvent) {
        	console.setText("");
        }
    });
    
    consoleVbox.getChildren().add(title2);
    consoleVbox.getChildren().add(console);
    consoleVbox.getChildren().add(clearConsole);
    
    face = new FaceComponent();
    face.setMaxSize(500, 500);
    face.setFace(FaceState.mSmile);
    face.animateEyes(true);
    
    componentLayout.setCenter(face);
    componentLayout.setLeft(mainLeftVbox);
    componentLayout.setRight(consoleVbox);
    componentLayout.setBottom(startListening);
    
    
	//Add the BorderPane to the Scene
	Scene appScene = new Scene(componentLayout,1200,650);

	//Add the Scene to the Stage
	primaryStage.setScene(appScene);
	primaryStage.show();
	
	}
	
	
	public static void faceSpeak(boolean value) {
		if(face!=null) { face.speak(value); }
	}
	
	private void handleAlchemyCheckboxAction(ActionEvent e){
		VoiceListener.getInstance().setUseSentiment(!VoiceListener.getInstance().isUsingSentiment());
	}
	
	public static void print(String s){
		if(console==null) {return;}
		String newline = System.getProperty("line.separator");
		console.appendText(newline+s);
	}
	
	

}