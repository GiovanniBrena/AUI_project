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
	
	public static final int mNormal=1;
	public static final int mSmile=2;
	public static final int mBigSmile=3;
	public static final int mSad=4;
	public static final int mOoh=5;
	
	
	public FaceComponent() {
		super(browser);
        String currentDirectory = System.getProperty("user.dir");
        browser.loadURL(("file:///"+currentDirectory+"/src/uiFace/webResource/index.html"));        
	}

    public void setFace(FaceState state){
    	faceState = state;
    	browser.executeJavaScript("setMouth("+"mSad"+");");
    }
    public void anim(){
    	browser.executeJavaScript("animTo();");
    }
        
    
}