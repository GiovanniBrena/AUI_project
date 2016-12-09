/*
 * Christopher Deckers (chrriis@nextencia.net)
 * http://www.nextencia.net
 *
 * See the file "readme.txt" for information on usage and redistribution of
 * this file, and for a DISCLAIMER OF ALL WARRANTIES.
 */
package ui_V2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

//import com.sun.jna.win32.StdCall;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;


public class  JavascriptExecution  {
 
  public JavascriptExecution getIstance(){return this;}
  
 
  
  
  public static void main(String[] args) {
    
	
	  //questa è la classe che fa partire la UI e da cui possiamo eseguire javascript
	  EmotionFace ef=new EmotionFace();
	  new Thread(ef).start();
	 
	 
	  int i=0;
	  while(i!=5){
	  System.out.println("Premi 0,1,2 per cambiare espressione");
	  Scanner sc = new Scanner(System.in);
	  i = sc.nextInt();
	  ef.changeFace(i);
	  }
    
 
  }

  
  
 
  
}
