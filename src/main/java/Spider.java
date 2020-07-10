import com.sun.xml.internal.ws.handler.HandlerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Spider {
    //TODO :
    // - Make http request and extract urls from site url
    //     * Handle content type exceptions
    //     * Create a robots.txt file reader method (This will stop the spider from violating website crawling rules)
    //     * Look for XML sitemaps (They usually contain all the site urls)
    //     * Add crawled url to crawled_url list (if it exists in uncrawled url list delete it)
    // - Preprocess and Store each extracted url (DB -> CSV file):
    //      * Collect urls that are in the target domain [Optional]
    //      * Make sure it deos not exist in the crawled list
    // [Repeat]

    // TODO: Error Handling
    //  - java.io.IOException:
    //  - org.jsoup.HttpStatusException: HTTP error fetching URL. Status=400
    //  - javax.net.ssl.SSLHandshakeException:
    //  - java.net.MalformedURLException: Only http & https protocols supported

    public ArrayList<String> uncrawled_Urls = new ArrayList<String>();
    public ArrayList<String> crawled_Urls = new ArrayList<String>();

    public int NUM_OF_ERRORS = 0;
    public int TOTAL_NUM_OF_URLS = 0;
    public int NUM_OF_BAD_URLS = 0;

    public int i = 0;

    public Spider() {
        System.out.println("Initializing spider ...");
    }

    /**
     * -----------
     * crawl()
     */
    public void crawl(String url, String domain) {
        try {
            // Fetching and parsing HTMl ...
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]"); // Extracting all <a href=""> tags
            System.out.println(doc);

            if (!links.isEmpty()) {
                crawled_Urls.add(url);// Add current url to crawled list
                System.out.println((i++) + ": " + url);
                preProcess_urls(links, domain);
            } else {
                System.out.println("Empty_url: " + url);
            } // End of if else: !links.isEmpty()

        } catch (Exception e) {
            NUM_OF_ERRORS++;
            //Handle all Exceptions here ...
            System.out.println("Caught Error: " + e);

            //---------------------------
          //  this.crawlNextURL(domain); // Recursion point, if exception occurs
        }

        //-------------------------
       // this.crawlNextURL(domain); // Recursion point
    } // End of crawl()

    public void printCrawlStatus() {
        System.out.println(uncrawled_Urls.size() + " Uncrawled urls, " + crawled_Urls.size() + " Crawled urls");
        System.out.println("Total urls: " + TOTAL_NUM_OF_URLS + ", Bad urls: " + NUM_OF_BAD_URLS + ", Errors: " + NUM_OF_ERRORS);
    }


    /**
     * -------------------------------------------------------------------------------------------
     * Preprocess Urls
     * - url contains domain name e.g example.com, continue preprocessing
     * - url does not exist in uncrawled_url and crawled_url list, add it to the uncrawled list
     * - Remove it from the uncrawled list, if it exists
     ***/

    public void preProcess_urls(Elements links, String domain) {
        //- For each element OR <a href=""> tag ...
        for (Element link : links) {
            TOTAL_NUM_OF_URLS++;
            // Extract the absolute href attribute (it contains the url we need) ...
            String l = link.attr("abs:href");

            if (l.contains(domain) && l.contains("http")) {
                // TODO - ADD MORE PREPROCESSING CODE HERE ...

                // If url does not exist in uncrawled_url and crawled list ...
                if (!uncrawled_Urls.contains(l) && !crawled_Urls.contains(l)) {
                    uncrawled_Urls.add(l);//Then add it to unCrawled_url list
                }// BUT if url exists in uncrawled_url list ...
                else if (uncrawled_Urls.contains(l)) {
                    uncrawled_Urls.remove(l);  // Then remove it
                }

                // TODO - ADD MORE PREPROCESSING CODE HERE ...
            } else {
                NUM_OF_BAD_URLS++;
                System.out.println("Out_of_Domain: " + l);
            }
        }//for Each loop END
    }

    /**
     * ----------------------------------------------------------------
     * crawlNextUrl()
     */
    public void crawlNextURL(String domain) {
        //If uncrawled list is not empty ...
        if (!uncrawled_Urls.isEmpty()) {
            // Then generate random index (0 - list size)
            int randomIndex = (int) (Math.random() * uncrawled_Urls.size());
            // Then use random index to select element or url from list
            String new_url = uncrawled_Urls.get(randomIndex);
            uncrawled_Urls.remove(randomIndex); // After getting the url, remove it from the list

            //-----------------------------------------------
            this.crawl(new_url, domain);  // Begin recursion

        } else {
            System.out.println("Spider has terminated!!");
            this.printCrawlStatus();
            // TERMINATE = true;
        }
    }

    /**
     * Debug Scrapping Mode
     * */

    public void debugMode(String url){
        try {
            Document doc = Jsoup.connect(url).get();
            System.out.print(doc);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


