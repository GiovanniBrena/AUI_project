package ibmAPI;

import java.awt.Label;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import Model.Conversazione;
import Model.Frase;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class guiAlchemy extends Application {
	
	
	private testApi testAPI;
	
	static TextArea console;
	//dove scrivere risultati alchemy
	static TextArea conversationInfoArea;
	static TextArea sentimentArea;
	static TextArea emotionArea;
	static TextArea typerRelArea;
	static TextArea conceptArea;
	static TextArea entitiesArea;
	static TextArea keywordArea;
	
	static TextArea[] alchemyTextAreas={sentimentArea,emotionArea,typerRelArea,conceptArea,entitiesArea,keywordArea};
	
	
	static Canvas stateCanvas;
	static Text stateLabel;
	static Button startListening;
	static TextField inputField;
	 PrintWriter printwriter;

	private void setUpUI(Stage primaryStage){
		//The primaryStage is the top-level container
		primaryStage.setTitle("ALCHEMY_ANALYSIS");

		//The BorderPane has the same areas laid out as the
		//BorderLayout layout manager
		BorderPane componentLayout = new BorderPane();
		componentLayout.setPadding(new Insets(20,0,20,20));
		
		VBox vboxAlchemy = new VBox();
	    vboxAlchemy.setPadding(new Insets(10));
	    vboxAlchemy.setSpacing(8);

	    Text title = new Text("ALCHEMY");
	    TabPane tabPane = new TabPane();
        stateCanvas = new Canvas(500, 25);	
        
        
     
        Tab tabA=new Tab("infoGeneraliConversazione");
        conversationInfoArea=new TextArea();
        tabA.setContent(conversationInfoArea);
        
        tabPane.getTabs().add(tabA);
      
     //
      //Add something in Tab
        ArrayList<Tab> alchemyTabs=new ArrayList<>();
        for (int i = 0; i < alchemyTextAreas.length; i++) {
        	Tab tab=new Tab("alchemy"+(i+0));
        	alchemyTextAreas[i]=new TextArea();
        	tab.setContent(alchemyTextAreas[i]);
        	tabPane.getTabs().add(tab);
			
		}
        
      
       
        
        
     //   
	    vboxAlchemy.getChildren().add(title);
	    vboxAlchemy.getChildren().add(tabPane);
	    vboxAlchemy.getChildren().add(stateCanvas);
	   
	    
	    BorderPane.setAlignment(vboxAlchemy, Pos.CENTER_LEFT);
	    
	   
	    
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
	        		testAPI.sendString(inputField.getText());
	        		
	        		print("SERVER: " + inputField.getText());
	        		inputField.setText("");
	        	
	        }
	    });
	    
	    vbox2.getChildren().add(title2);
	    vbox2.getChildren().add(console);
	    vbox2.getChildren().add(inputField);
	    vbox2.getChildren().add(sendButton);
	    
	    vbox2.setAlignment(Pos.TOP_LEFT);
	  
	    
	    componentLayout.setLeft(vbox2);
	    componentLayout.setCenter(vboxAlchemy);
	    componentLayout.setBottom(sendButton);
	    
	    
		//Add the BorderPane to the Scene
		Scene appScene = new Scene(componentLayout,1200,500);

		//Add the Scene to the Stage
		primaryStage.setScene(appScene);
		primaryStage.show();
  }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		testAPI=new testApi();
		setUpUI(primaryStage);
		
	}
	public static void print(String s){
		String newline = System.getProperty("line.separator");
		console.appendText(newline+s);
	}
	
	public static void printAlchemyData(Frase f){
		print("Frase Originale"+f.getFraseOriginale());
		print("Frase tradotta"+f.getFraseTradotta());
		if(f==null)System.out.println("frase nulla");
		for (int i = 0; i < alchemyTextAreas.length; i++) {
		
			 alchemyTextAreas[i].setText(f.getEmotion().toString());
			
		}
		
		
		
		
	}
	
	public static void printInfoConversation(Conversazione c){
		conversationInfoArea.setText(c.toString());
	}

	
	public static void main(String[] args) {
		
		launch(args);
	
		
		// get VoiceListener instance
		//VoiceListener voiceListener = VoiceListener.getInstance();
		// start listening
		//voiceListener.startListening();
		
		}

}
