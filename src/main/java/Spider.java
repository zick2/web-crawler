import com.sun.xml.internal.ws.handler.HandlerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
    public int NUM_OF_REDUNDANT_URLS = 0;

    public int i = 0;

    public Spider() {
        System.out.println("Initializing spider ...");
    }

    /**
     * crawl():
     *   urls: []
     *  - domains: []
     *  - crawlWithinDomains: true
     */
    public void crawl(String[] urls, String[] domains, boolean crawlWithinDomains) {
        String url = urls[0];
        if (urls.length > 1) {
            uncrawled_Urls.addAll(Arrays.asList(urls).subList(1, urls.length-1));
        }
        try {
            //Fetching and parsing HTMl ...
            Document doc = Jsoup.connect(url).get();
            Elements pageLinks = doc.select("a[href]"); // Extracting all <a href=""> tags

            if (!pageLinks.isEmpty()) {
                crawled_Urls.add(url);// Add current url to crawled list
                preProcess_urls(pageLinks, domains, crawlWithinDomains);
            } else {
                NUM_OF_REDUNDANT_URLS++;
                System.out.println("Empty_url: " + url);
            } // End of if else: !links.isEmpty()

        } catch (Exception e) {
            NUM_OF_ERRORS++;
            //Handle all Exceptions here ...
            System.out.println("Caught Error: " + e);
            //---------------------------
            this.crawlNextURL(domains, crawlWithinDomains); // Recursion point, if exception occurs
        }
        //-------------------------
        this.crawlNextURL(domains, crawlWithinDomains); // Recursion point
    } // End of crawl()

    public  void printCrawlStatus() {
        System.out.println("Uncrawled urls: " + uncrawled_Urls.size() + "\nCrawled urls: " + crawled_Urls.size());
        System.out.println("Total urls: " + TOTAL_NUM_OF_URLS + "\nBad : " + NUM_OF_BAD_URLS + "\nRedundant: " + NUM_OF_REDUNDANT_URLS + "\nErrors: " + NUM_OF_ERRORS);
    }


    /**
     * Preprocess Urls
     * - url contains domain name e.g example.com,
     * - url does not exist in uncrawled_url and crawled_url list, add it to the uncrawled list
     ***/

    public void preProcess_urls(Elements pgLinks, String[] domains, boolean crawlWithinDomains) {
        for (Element pgLink : pgLinks) {
            TOTAL_NUM_OF_URLS++;
            // Extract the absolute href attribute (it contains the url we need) ...
            String l = pgLink.attr("abs:href");

            if (crawlWithinDomains) {
                for (String domain : domains) {
                    if (l.contains(domain) && l.contains("http")) {
                        // TODO - ADD MORE PREPROCESSING CODE HERE ...

                        if (!uncrawled_Urls.contains(l) && !crawled_Urls.contains(l)) {
                            System.out.println(i++ + ": Good -> " + l);
                            uncrawled_Urls.add(l);
                        } else {
                            NUM_OF_REDUNDANT_URLS++;
                            //uncrawled_Urls.remove(l);
                            System.out.println(i++ + ": Redundant -> " + l);
                        }
                    } else {
                        NUM_OF_BAD_URLS++;
                        System.out.println(i++ + ": Bad -> " + l);
                        // Todo: System.out.println("Out_of_Domain: " + l);
                    }
                }
            } else {
                if (l.contains("http")) {
                    // TODO - ADD MORE PREPROCESSING CODE HERE ...

                    if (!uncrawled_Urls.contains(l) && !crawled_Urls.contains(l)) {
                        System.out.println(i++ + ": Good -> " + l);
                        uncrawled_Urls.add(l);
                    } else {
                        NUM_OF_REDUNDANT_URLS++;
                        //uncrawled_Urls.remove(l);
                        System.out.println(i++ + ": Redundant -> " + l);
                    }
                } else {
                    NUM_OF_BAD_URLS++;
                    System.out.println(i++ + ": Bad -> " + l);
                    // Todo: System.out.println("Out_of_Domain: " + l);
                }
            }

        }//for Each loop END
    }

    /**
     * crawlNextUrl(): - Selects next url in line from the uncrawled list to be crawled
     */
    public void crawlNextURL(String[] domains, boolean crawlWithinDomains) {
        String[] new_url = new String[1];
        int index = 0;
        //If uncrawled list is not empty ...
        if (!uncrawled_Urls.isEmpty()) {
            new_url[0] = uncrawled_Urls.get(index);
            uncrawled_Urls.remove(index);
            //-----------------------------------------------
            this.crawl(new_url, domains, crawlWithinDomains);  // Begin recursion

        } else {
            System.out.println("Spider has terminated!!");
            this.printCrawlStatus();
        }
    }

    /**
     * extractDomains():
     */

    public String[] extractDomains(String[] urls) {
        String[] domains = new String[urls.length];
        int count = 0;
        for (String url : urls) {
            int index = url.indexOf("://") + 3;
            int len = url.length();
            StringBuilder domain = new StringBuilder();

            while (index != len) {
                if (url.charAt(index) != '/') {
                    domain.append(url.charAt(index));
                    index++;
                } else {
                    index = len;
                }
            }
            //  System.out.println(domain);
            domains[count] = domain.toString();
            count++;
        }
        // System.out.println(domains.length);

        return domains;
    }

    /**
     * debugMode():
     */

    public void debugMode(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            System.out.print(doc);
            Elements links = doc.select("a[href]");
            System.out.println("Size: " + links.size());
            for (Element link : links) {
                TOTAL_NUM_OF_URLS++;
                // Extract the absolute href attribute (it contains the url we need) ...
                String l = link.attr("abs:href");
                System.out.println("Link: " + l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


