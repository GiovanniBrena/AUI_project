//package ui_V2;
//
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//import java.awt.event.MouseListener;
//import java.awt.print.Pageable;
//
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.SwingUtilities;
//
//import chrriis.common.UIUtils;
//import chrriis.common.WebServer;
//import chrriis.dj.nativeswing.swtimpl.NativeComponent;
//import chrriis.dj.nativeswing.swtimpl.NativeInterface;
//import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
//import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
//import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
//
//public class EmotionFace  implements Runnable{
//
//	final JWebBrowser webBrowser;
//	
//	public static final int NORMAL = 0;
//	public static final int SAD = 1;
//	public static final int HAPPY = 2;
//
//	private String smileyFaceExecute = "$('.eye').attr('class', ' eye eyeHappy');$('.iris').css({'visibility':'hidden'});";
//	private String normalFaceExecute = "$('.eye').attr('class', ' eye eyeNormal');$('.iris').css({'visibility':'visible'});";
//	private String typeOfFace[] = { normalFaceExecute, normalFaceExecute, smileyFaceExecute };
//
//
//	
//	public EmotionFace(){
//		webBrowser=new JWebBrowser();
//		
//		
//		
//		
//	}
//	
//	
//	public JPanel initFace() {
//		JPanel web = new JPanel(new BorderLayout());
//		JPanel webBrowserPanel = new JPanel(new BorderLayout());
//		webBrowserPanel.setBorder(BorderFactory.createTitledBorder("Native Web Browser component"));
//		
//		webBrowser.setBarsVisible(false);
//		webBrowser.setStatusBarVisible(true);
//		// open the index.html of the face
//		//webBrowser.navigate(faceHostAddress);
//		//webBrowser.navigate("index.html");
//		webBrowser.navigate(WebServer.getDefaultWebServer().getClassPathResourceURL(getClass().getName(), "index.html"));
//
//
//		webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
//		web.add(webBrowserPanel, BorderLayout.CENTER);
//		
//		return web;
//
//	}
//
//	public  void init() {
//		NativeInterface.open();
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				UIUtils.setPreferredLookAndFeel();
//				
//				EmotionFace ef=new EmotionFace();
//				JFrame frame = new JFrame("ABI FACE");
//				frame.setResizable(false);
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				frame.getContentPane().add(initFace(), BorderLayout.CENTER);
//				frame.setSize(1000, 800);
//				frame.setLocationByPlatform(true);
//				frame.setVisible(true);
//			}
//		});
//		
//	NativeInterface.runEventPump();
//
//	}
//
//	public void changeFace(int i) {
//	
//		
//		
//		SwingUtilities.invokeLater(new Runnable() {
//			  
//			public void run() {
//				  System.out.println("Sto eseguendo javascript : "+typeOfFace[2].toString());
//				  
//				  webBrowser.executeJavascript(typeOfFace[i]);
//			  }
//			});
//
//	}
//
//	public void moveIris(int x, int y) {
//		String toExecute = "$('.iris').css({top: '" + y + "%',left:'" + x + "%'})";
//		webBrowser.executeJavascriptWithResult(toExecute);
//
//	}
//
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		init();
//		
//	}
//
//
//	
//
//}
