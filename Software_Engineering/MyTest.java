import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.TextParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;


public class MyTest {
	
	//Default timeout
	private static final int DEFAULT_TIMEOUT = 2000;

	//Crawler variables
	static CrawlConfig config;
	static PageFetcher pageFetcher;
	static RobotstxtConfig robotstxtConfig;
	static RobotstxtServer robotstxtServer;
	static CrawlController controller;
	static List<Object> datas;

	/**
	 * Initial setup of crawler
	 * @throws Exception
	 */
	@BeforeClass 
	public static void setup() throws Exception {

		String crawlStorageFolder = "data/";
		int numberOfCrawlers = 1; 

		config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setIncludeBinaryContentInCrawling(false);
		config.setMaxDepthOfCrawling(-1); 
		config.setMaxOutgoingLinksToFollow(5000);

		pageFetcher = new PageFetcher(config);                            
		robotstxtConfig = new RobotstxtConfig();
		robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

		controller = new CrawlController(config, pageFetcher, robotstxtServer);
		controller.addSeed("http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2public/Machine_learning.html");            
		controller.start(MyCrawler.class, numberOfCrawlers); 

		datas = controller.getCrawlersLocalData();
	}

	/**
	 * Error 1 - Crawler is visiting URL's that are not allowed
	 * The following test addresses the error of the crawler visiting Url's that have been disallowed in the shouldVisit method of the MyCrawler class
	 * The test loops through all the pages that have been retrieved by the crawler and gets the Url using the page.getWebURL().getURL().startsWith() method
	 * Using an if statement it tests all the url's to check that they are valid
	 * In the MyCrawler class it is specified that only Url's that start with http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2public/ are allowed
	 * Therefore any Url that starts differently is deemed to be invalid 
	 * If the Url is invalid the legalUrl boolean is set to false
	 * Finally an assertTrue is used on this boolean to ensure that all Url's are valid
	 * This error was noted because the crawler visited the webpage http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2private/IDA.html
	 * This Url has a start which is not allowed according to the MyCrawler class
	 */
	@Test (timeout = DEFAULT_TIMEOUT)
	public void illegalUrlTest() {

		//Is the url legal?
		boolean legalUrl = true;

		for(Object data : datas) 
		{
			//Loop through all the webpages that have been crawled
			@SuppressWarnings("unchecked")
			ArrayList<Page> pages = (ArrayList<Page>) data;
			for(Page page : pages) 
			{
				//If the webpage url does not have a valid start
				if(!page.getWebURL().getURL().startsWith("http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2public/")) 
				{
					//Set to false and break loop
					legalUrl = false;
					break;
				}
			}
		}
		//Test has visited any illegal url's
		assertTrue("Web crawler is visiting url's that it should not", legalUrl);
	}

	/**
	 * Error 2 - No capital letters in the output
	 * The following method tests the HTMLParseData class to ensure that it returns the text from the webpage in the correct format
	 * The test sets the html content of the class to a test string using the setHtml method
	 * The test data that is used is a String with a mixture of both capital and lower case letters - "<p>Test For Capital Letters</p>"
	 * The data is then immediately retrieved from the parsing class using the getHtml method
	 * An assertEquals is used to ensure that the retrieved data is the same as the data that was set
	 * This addresses the error in the programme of capital letters not parsing
	 * This is a perceived error because none of the data that the crawler returns has capital letters, despite them being present in the webpage
	 * The error is evident throughout all the webpages that are crawled
	 */
	@Test (timeout = DEFAULT_TIMEOUT)
	public void capitalLetterTestHtml() {

		//Test string
		String originalText = "<p>Test For Capital Letters</p>"; 
		
		//Set the string to htmlParseData
		HtmlParseData htmlParseData = new HtmlParseData();
		htmlParseData.setHtml(originalText);
		
		//Retrieve the string from htmlParseData
		String retrievedText = htmlParseData.getHtml();
		
		//Test if the original string is the same as the retrieved string
		assertEquals("Test to check if capital letters are properly parsed by HtmlParseData", originalText, retrievedText);
	}
	

	/**
	 * Error 3 - No number 0's in the output
	 * The following methods test the ability of the TestParseData class to correctly parse numbers
	 * The test sets the content of the class to a test string using the setTextContent method
	 * The test string consists of each of the numbers from 0 to 9
	 * Once the data is set, it is immediately retrieved using the getTextContent method
	 * An assertEquals is used to ensure the retrieved string is the same as the one that was initially set
	 * This addresses the error of the number 0 being unable to parse
	 * This is a perceived error because there are no 0's in the output of the crawler, despite them being present in the webpage
	 * In particular the webpage with URL http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2public/data.txt is badly effected by this error
	 */
	@Test (timeout = DEFAULT_TIMEOUT)
	public void numberParsingTest() {

		//Test string
		String originalInt = "0123456789"; 
		
		//Set the string to textParseData
		TextParseData textParseData = new TextParseData();
		textParseData.setTextContent(originalInt);
		
		//Retrieve the string from textParseContent
		String retrievedInt = textParseData.getTextContent();
		
		//test if the original string is the same as the retrieved string
		assertEquals("Test to check if numbers are properly parsed by TextParseData", originalInt, retrievedInt);
	}
	

	/**
	 * Error 4 - Unregistered outgoing links
	 * This tests the Page and HtmlParseData classes to ensure that all web links are recognised by the crawler
	 * It parses all the data from the webpage and checks the size of the outgoing links set that is created by the HtmlParseData class
	 * It then asserts true if the number of outgoing links recognised is more than 4 for any of the pages
	 * This addresses the error of number of outgoing links being restricted to 4. 
	 * This is the perceived error as not all links on the webpages are recognised despite the urls being in a valid format
	 * This error was spotted on the page with Url http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2public/Machine_learning.html
	 * On this page there are 6 outgoing links, but the crawler only records 4 of them 
	 * The links which are not recorded are http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2public/Reinforcement_learning.html and http://www.dcs.gla.ac.uk/~bjorn/sem20172018/ae2public/Unsupervised_learning.html
	 */
	@Test (timeout = DEFAULT_TIMEOUT)
	public void outgoingLinkTest() {
		
		//Does the number of outgoing links appear to be capped at 4?
		boolean linksNotCapped = true;
		
		for(Object data : datas) 
		{
			//Loop through all the pages that the crawler has visited
			@SuppressWarnings("unchecked")
			ArrayList<Page> pages = (ArrayList<Page>) data;
			for(Page page : pages) 
			{
				//Variable to store outgoing links
				int outgoingLinks = 0;

				//If the parseData is html
				if (page.getParseData() instanceof HtmlParseData) {
					
					//Get the number of outgoing links that have been recorded
					HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
					Set<WebURL> links = htmlParseData.getOutgoingUrls();
					outgoingLinks = links.size();
				} 

				//If there is less than 5 outgoing links
				if (outgoingLinks < 5) {
					//Set boolean to false and break loop
					linksNotCapped = false;
					break;
				}
			}
		}
		//Test if the number of outgoing links appear to be capped
		assertTrue("Number of outgoing links is capped", linksNotCapped);
	}
}
