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

    public ArrayList<String> uncrawled_Urls = new ArrayList<String>();
    public ArrayList<String> crawled_Urls = new ArrayList<String>();
    public boolean TERMINATE = false;

    public int i = 0;

    public Spider() {
        System.out.println("Initializing spider ...");
    }

    /**
     * crawl()
     */
    public void crawl(String url, String domain) {
        if (!TERMINATE) {
            try {
                // uncrawled_Urls.add(url);

                // Fetching and parsing HTMl ...
                Document doc = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .get();

                if (doc != null) {
                    crawled_Urls.add(url);// Add current url to crawled list
                    Elements links = doc.select("a[href]"); // Extracting all <a href=""> tags

                    System.out.println((i++) + ": " + url);

                    /**-------------------------------------------------------------------------------------------
                     * Preprocess Urls
                     * - url contains domain name e.g example.com, continue preprocessing
                     * - url does not exist in uncrawled_url and crawled_url list, add it to the uncrawled list
                     * - Remove it from the uncrawled list, if it exists
                     ***/

                    //- For each element OR <a href=""> tag ...
                    for (Element link : links) {
                        // Extract the absolute href attribute (it contains the url we need) ...
                        String l = link.attr("abs:href");

                        if (l != "" && l.contains(domain)) {
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
                            System.out.println("Url out of domain: " + l);
                        }

                    }//for Each loop END

                    //--------------------------

                    this.crawlNextURL(domain);
                } else {
                    System.out.print("Null Document");
                } // End of if else: null checkpoint
            }// TODO: Error Handling
             //  - java.io.IOException:
             //  - org.jsoup.HttpStatusException: HTTP error fetching URL. Status=400
             //  - javax.net.ssl.SSLHandshakeException:
            catch (Exception e) {
                //Handle all Exceptions here ...
                System.out.println("Caught Error: "+ e);
            } finally {
                //System.out.println("Fixed error");
               // this.crawlNextURL(domain);
            }
            //---------------------------------------
//            catch (IOException IO_error) {
//                this.printCrawlStatus();
//                System.out.println("IO_error");
//                IO_error.printStackTrace();
//            } catch (HandlerException http_error) {
//                this.printCrawlStatus();
//                System.out.println("http_error");
//                http_error.printStackTrace();
//            }

        } else {
            this.printCrawlStatus();
        } // End of if else:  termination point
    }

    public void printCrawlStatus() {
        System.out.println(uncrawled_Urls.size() + " urls uncrawled ");
        System.out.println(crawled_Urls.size() + " urls crawled ");
    }

    /**
     * crawlNextUrl()
     */
    public void crawlNextURL(String domain) {
        //If uncrawled list is not empty ...
        if (!uncrawled_Urls.isEmpty()) {
            //And also, if uncrawled list has more than 1 element or urls ...
            if (uncrawled_Urls.size() > 1) {
                // Then generate random index (0 - list size)
                int randomIndex = (int) (Math.random() * uncrawled_Urls.size());
                // Then use random index to select element or url from list
                String new_url = uncrawled_Urls.get(randomIndex);
                this.crawl(new_url, domain);  // Begin recursion
            }
        } else {
            TERMINATE = true;
            System.out.println("No more urls in uncrawled list: \n Spider is terminating ...");
        }
    }
}


