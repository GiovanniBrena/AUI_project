package uiFace;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

 
/**
 * The sample demonstrates how to create Browser instance, embed it, display and load
 * specified URL.
 */

public class FaceComponent extends BrowserView {
	
	private static Browser browser = new Browser();
	
	//mouth State
	public enum FaceState {
		mNormal, mSmile, mBigSmile, mSad, mOoh
	}
	private FaceState faceState; 	
	
	public FaceComponent() {
		super(browser);
        String currentDirectory = System.getProperty("user.dir");
        browser.loadURL(("file:///"+currentDirectory+"/src/uiFace/webResource/index.html"));    
        setFace(FaceState.mOoh);
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
    
    
    
}