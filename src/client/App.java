package client;

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
import javafx.event.*;



@SuppressWarnings("restriction")
public class App extends Application {
	
	boolean isActive = false; 
	static TextArea console;
	static TextField ipField;
	static TextField portField;

	public static void main(String[] args) {
	launch(args);
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
	
	VBox vbox = new VBox();
    vbox.setPadding(new Insets(10));
    vbox.setSpacing(8);

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
    
    CheckBox alchemyCheckbox = new CheckBox();
    alchemyCheckbox.setText("Use Sentiment Analysis");
    alchemyCheckbox.setSelected(true);
    alchemyCheckbox.setOnAction(e -> handleAlchemyCheckboxAction(e));
    
    vbox.getChildren().add(title);
    vbox.getChildren().add(rb1);
    vbox.getChildren().add(rb2);
    vbox.getChildren().add(rb3);
    vbox.getChildren().add(alchemyCheckbox);

    
    
    BorderPane.setAlignment(vbox, Pos.CENTER_LEFT);
    //BorderPane.setMargin(list, new Insets(12,12,12,12));
    //borderPane.setCenter(list);
    
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
        		VoiceListener.getInstance().startListening();
        		}
        }
    });
    
    VBox vbox2 = new VBox();
    vbox2.setPadding(new Insets(10));
    vbox2.setSpacing(8);

    Text title2 = new Text("CONSOLE");
    console = new TextArea();
    console.setEditable(false);
    console.setPrefHeight(300);
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
    
    vbox2.getChildren().add(title2);
    vbox2.getChildren().add(console);
    vbox2.getChildren().add(clearConsole);
    
    VBox topHBox = new VBox();
    topHBox.setPadding(new Insets(10));
    topHBox.setSpacing(8);
    
    Text serverLabel = new Text("Server IP");
    Text portLabel = new Text("port");
    ipField = new TextField ();
    portField = new TextField ();
    ipField.setText(TestClient.getInstance().serverurl);
    portField.setText(String.valueOf(TestClient.getInstance().serverport));
    
    ipField.setDisable(true);
    portField.setDisable(true);
    
    topHBox.getChildren().add(serverLabel);
    topHBox.getChildren().add(ipField);
    topHBox.getChildren().add(portLabel);
    topHBox.getChildren().add(portField);
    
    componentLayout.setRight(topHBox);
    componentLayout.setLeft(vbox);
    componentLayout.setCenter(vbox2);
    componentLayout.setBottom(startListening);
    
    
	//Add the BorderPane to the Scene
	Scene appScene = new Scene(componentLayout,1000,500);

	//Add the Scene to the Stage
	primaryStage.setScene(appScene);
	primaryStage.show();
	
	}
	
	private void handleAlchemyCheckboxAction(ActionEvent e){
		VoiceListener.getInstance().setUseSentiment(!VoiceListener.getInstance().isUsingSentiment());
	}
	
	public static void print(String s){
		String newline = System.getProperty("line.separator");
		console.appendText(newline+s);
	}
	
	

}