package uiFace;

import client.TestClient;
import client.VoiceListener;
import client.WebApp;
import uiFace.FaceComponent.FaceState;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

 
/**
 * The sample demonstrates how to create Browser instance, embed it, display and load
 * specified URL.
 */

public class UIBrowserComponent extends BrowserView {
	
	private static Browser browser = new Browser();
	private DOMDocument document;
	
	private DOMElement startBtn;
	
	private DOMElement modeManualBtn;
	private DOMElement modeConversationBtn;
	private DOMElement modeRepeaterBtn;
	private DOMElement sentimentCheckbox;
	private DOMElement serverUrl;
	private DOMElement serverPort;

	private FaceState faceState; 	
	
	public UIBrowserComponent() {
		super(browser);
        String currentDirectory = System.getProperty("user.dir");
        browser.loadURL(("file:///"+currentDirectory+"/res/UI/abi_UI.html"));    
        document = browser.getDocument();
        
        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                document = event.getBrowser().getDocument();
                initBrowser();
                setFace(FaceState.mSmile);
                anim();
            }
        });
	}

    public void setFace(FaceState state){
    	faceState = state;
    	browser.executeJavaScript("setMouth("+faceState+");");
    }
    public void anim(){
    	browser.executeJavaScript("animTo();");
    }
    
    public void speak(boolean value){
    	browser.executeJavaScript("speak("+value+");");
    }
    
    public void animateEyes(boolean value){
    	browser.executeJavaScript("animateEyes("+value+");");
    }
    
    public void think(boolean value){
    	browser.executeJavaScript("think("+value+");");
    }
    
    private void initBrowser(){
    	startBtn = (DOMElement) document.findElement(By.id("startButton"));
    	
    	modeManualBtn = (DOMElement) document.findElement(By.id("modeManual"));
    	modeConversationBtn = (DOMElement) document.findElement(By.id("modeConversation"));
    	modeRepeaterBtn = (DOMElement) document.findElement(By.id("modeRepeater"));
    	sentimentCheckbox = (DOMElement) document.findElement(By.id("sentimentCheck"));
    	
    	startBtn.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
    	    public void handleEvent(DOMEvent event) {
    	    	
    	    	if(!WebApp.isActive) {
    	    	
    	    	serverUrl = (DOMElement) document.findElement(By.id("serverUrl"));
    	    	serverPort = (DOMElement) document.findElement(By.id("serverPort"));
    	    	
    	    	TestClient.getInstance().setServerUrl(serverUrl.getAttribute("value"));
    	    	TestClient.getInstance().setServerPort(Integer.valueOf(serverPort.getAttribute("value")));
    	    	
    	    	browser.executeJavaScript("toggleStartButton(false);");
    	    	
    	    	// start
    	    	}
    	    	else {
    	    		browser.executeJavaScript("toggleStartButton(true);");
    	    	}
    	    	
    	    	WebApp.startButtonClick();
    	    	
    	    }
    	}, false);
    	
    	modeManualBtn.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
    	    public void handleEvent(DOMEvent event) {
    	    	WebApp.setMode(VoiceListener.Mode.MANUAL);
    	    }
    	}, false);
    	
    	modeConversationBtn.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
    	    public void handleEvent(DOMEvent event) {
    	    	WebApp.setMode(VoiceListener.Mode.CONVERSATION);
    	    }
    	}, false);
    	
    	modeRepeaterBtn.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
    	    public void handleEvent(DOMEvent event) {
    	    	WebApp.setMode(VoiceListener.Mode.REPEATER);
    	    }
    	}, false);
    	
    	sentimentCheckbox.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
    	    public void handleEvent(DOMEvent event) {
    	    	WebApp.setUseSentiment(VoiceListener.getInstance().isUsingSentiment());
    	    }
    	}, false);
    	
    }
    
    
    
    
    
    
    
    
}