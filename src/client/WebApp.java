package client;


import java.io.InputStream;

import com.ibm.watson.developer_cloud.util.RequestUtils;
import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import uiFace.FaceComponent.FaceState;
import uiFace.UIBrowserComponent;



@SuppressWarnings("restriction")
public class WebApp extends Application {
	
	public static UIBrowserComponent uiBrowser;
	
	public static InputStream version;
	
	public static boolean isActive = false;
	
	public static boolean isUIActive=false;

	public static void main(String[] args) {
	launch(args);
	}

	@Override
	public void init() throws Exception {
		
	    // On Mac OS X Chromium engine must be initialized in non-UI thread.
	    if (Environment.isMac()) {
	        BrowserCore.initialize();
	    }
	   version= Thread.currentThread().getContextClassLoader().getResourceAsStream("version.properties");
	   System.out.println(RequestUtils.getUserAgent());
	    System.out.println("classLoader da dentro webApp"+version);
	}

	@SuppressWarnings({"unchecked" })
	@Override
	public void start(Stage primaryStage) {
	isUIActive=true;
	primaryStage.setTitle("ABI test");
	BorderPane componentLayout = new BorderPane();
    
	uiBrowser = new UIBrowserComponent( );
    uiBrowser.setFace(FaceState.mSmile);
    uiBrowser.animateEyes(true);
    
    componentLayout.setCenter(uiBrowser);

     
	//Add the BorderPane to the Scene
	Scene appScene = new Scene(componentLayout,1000,700);

	//Add the Scene to the Stage
	primaryStage.setScene(appScene);
	primaryStage.show();
	
	}
	
	
	public static void setMode(VoiceListener.Mode mode) {
		VoiceListener.getInstance().setMode(mode);
	}
	
	public static void setUseSentiment(boolean value) {
		VoiceListener.getInstance().setUseSentiment(value);
	}
	
	public static void setServerUrl(String ip) { TestClient.getInstance().setServerUrl(ip);}
	public static void setServerUrl(int port) { TestClient.getInstance().setServerPort(port);}
	
	public static  void startButtonClick() {
		if(isActive) { 
    		isActive=false;
    		VoiceListener.getInstance().stopListening();
    		}
    	else {
    		isActive=true;
    		VoiceListener.getInstance().startListening();
    		uiBrowser.setFace(FaceState.mSmile);
    		//uiBrowser.anim();
    		}
	}
	
	public static void faceSpeak(boolean value) {
		if(uiBrowser!=null) { uiBrowser.speak(value); }
	}
	public static void setFace(FaceState state) {
		if(uiBrowser!=null) { uiBrowser.setFace(state); }
	}

}