package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;



public class FeedRetriever {

	
	private static   List<String> feedsRSS=null;
	
	
	public static void  giveFeed() throws IllegalArgumentException, FeedException, IOException {
		   String url = "http://www.ansa.it/sito/notizie/topnews/topnews_rss.xml";
	        URL feedUrl = new URL(url);
	        
	        SyndFeedInput input = new SyndFeedInput();
	        SyndFeed feed = input.build(new XmlReader(feedUrl));
	        feedsRSS=new ArrayList<String>();
	        for (SyndEntry entry : (List<SyndEntry>)feed.getEntries()) {
	        	
	        	feedsRSS.add(entry.getTitle());
	        }
	       
	}
	
	
	public static String getFeed(int index){
		return feedsRSS.get(index);
		
	}
	

	
	
	
}