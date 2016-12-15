package uiFace;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
 
/**
 * The sample demonstrates how to create Browser instance, embed it, display and load
 * specified URL.
 */
public class FaceUI {
	
	static Browser browser = new Browser();
	
	
	
	//mouth State
	public static final int mNormal=1;
	public static final int mSmile=2;
	public static final int mBigSmile=3;
	public static final int mSad=4;
	public static final int mOoh=5;
	
	
	
	
    public FaceUI() {
        browser = new Browser();
        BrowserView view = new BrowserView(browser);
        JFrame frame = new JFrame("ABI - Hello World");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 800);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
 
        //browser.loadURL("C:/Users/andrea/workspaceTest/provaIBM/src/main/java/provaJfXBrowser/webResource/index.html");
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        //https://abiproject.000webhostapp.com/uiFace/index.html
        browser.loadURL(("file:///"+currentDirectory+"/src/uiFace/webResource/index.html"));
        
      
    }
    
    private String  returnMouthState(int i){
    String mTo="";
    switch (i) {
	case mNormal:return "mNormal";
	case mSmile:return"mSmile";
	case mBigSmile:return"mBigSmile";
	case mSad:return"mSad";
	case mOoh:return"mOoh";
	default:return "mNormal";
	}	
    }

    private void setFace(int i){
    	browser.executeJavaScript("setMouth("+returnMouthState(i)+");");
    }
    private void anim(){
    	browser.executeJavaScript("animTo();");
    }
    public static void main(String[] args) {
		FaceUI face=new FaceUI();
		
		Scanner in=new Scanner(System.in);
		int i=0;
		while(i!=7){
		System.out.println("da 1 a 5 per tipi di faccia,6 per animare_7 per uscire");
		i=in.nextInt();
		if(i==6)face.anim();
		face.setFace(i);
		}
	}
    
    
    
    
}