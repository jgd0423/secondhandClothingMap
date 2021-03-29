package map.service;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class InstaParser {
	public static void main(String[] args) {
		InstaParser selTest = new InstaParser("oltremare.shop");
        selTest.crawl();
    }
	
    //WebDriver
    private WebDriver driver;
    
    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:/Users/jgd04/Downloads/selenium-java-3.141.59/chromedriver.exe";
    
    //크롤링 할 URL
    private String base_url;
    
    public InstaParser(String instaId) {
        super();
 
        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        
        //Driver SetUp
        driver = new ChromeDriver();
        base_url = "https://smihub.com/v/" + instaId;
    }
 
    public void crawl() {
        try {
            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
        	int waittime = 10;
            driver.get(base_url);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            Document doc = Jsoup.parse(driver.getPageSource());
            
            System.out.println("HTML TITLE : " + doc.title());
            Elements tables = doc.select(".container");
            Elements linksOnPage = tables.select(".content__img-wrap");
            int i = 1;
            for (Element page : linksOnPage) {
            	System.out.println("count : " + i++);
            	Element link = page.select("a").first();
            	String linkHref = link.attr("href");
            	System.out.println("a href : " + base_url + linkHref);
            	Element img = page.select("img").first();
            	String imgtag = img.outerHtml();
            	System.out.println("img : " + imgtag);
            	if (i == 9) {
            		break;
            	}
            }
            System.out.println("================================================================");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
 
    }
		
}
