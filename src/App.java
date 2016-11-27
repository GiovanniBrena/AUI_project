
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


@SuppressWarnings("restriction")
public class App extends Application {
	
	boolean isActive = false; 
	static TextArea console;

	public static void main(String[] args) {
	launch(args);
	
	// get VoiceListener instance
	//VoiceListener voiceListener = VoiceListener.getInstance();
	// start listening
	//voiceListener.startListening();
	
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
    
    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
        public void changed(ObservableValue<? extends Toggle> ov,
            Toggle old_toggle, Toggle new_toggle) {
          if (group.getSelectedToggle() != null) {
            if(group.getSelectedToggle()==rb1) { VoiceListener.getInstance().setMode(VoiceListener.Mode.REPEATER);}
            else if(group.getSelectedToggle()==rb2) { VoiceListener.getInstance().setMode(VoiceListener.Mode.CONVERSATION);}
          }
        }
      });

    
    vbox.getChildren().add(title);
    vbox.getChildren().add(rb1);
    vbox.getChildren().add(rb2);
    
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
        		isActive=false;
        		print("Stop Recognizing.");
        		VoiceListener.getInstance().stopListening();
        		}
        	else {
        		startListening.setText("STOP"); 
        		rb1.setDisable(true);
        		rb2.setDisable(true);
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
    
    componentLayout.setLeft(vbox);
    componentLayout.setCenter(vbox2);
    componentLayout.setBottom(startListening);
    
    
	//Add the BorderPane to the Scene
	Scene appScene = new Scene(componentLayout,800,500);

	//Add the Scene to the Stage
	primaryStage.setScene(appScene);
	primaryStage.show();
	
	}
	
	
	public static void print(String s){
		String newline = System.getProperty("line.separator");
		console.appendText(newline+s);
	}

}