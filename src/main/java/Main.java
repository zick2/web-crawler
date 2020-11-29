import com.gargoylesoftware.htmlunit.WebClient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Websites for Testing:
 *  https://asoftmurmur.com/
 *  http://www.sciencefix.com/
 *  http://kauaimark.blogspot.com/
 *  https://www.miss-thrifty.co.uk
 *  https://www.piboco.com/
 *  http://sadiqweb.epizy.com
 * */

public class Main {
    public static void main(String[] args) throws IOException {
        //AppGUI appUI =


        String[] urls = {"https://asoftmurmur.com/",
                         " http://kauaimark.blogspot.com/",
                         " https://www.miss-thrifty.co.uk"};

        String[] start_url= {"https://asoftmurmur.com/"};

      //  Spider mySpider = new Spider();
       // mySpider.crawl(start_url, mySpider.extractDomains(start_url), true);
        new AppGUI();
      //  mySpider.debugMode("http://sadiqweb.epizy.com");
        // mySpider.closeRes();
    }

}
