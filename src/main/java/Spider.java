import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Spider {
    //TODO :
    // - Make http request and extract urls from site url
    //     * Create a robots.txt file reader method (This will stop the spider from violating website crawling rules)
    //     * Look for XML sitemaps (They usually contain all the site urls)
    //     * Add crawled url to crawled_url list (if it exists in uncrawled url list delete it)
    // - Preprocess and Store each extracted url (DB -> CSV file):
    //      * Collect urls that are in the target domain [Optional]
    //      * Make sure it deos not exist in the crawled list
    // [Repeat]

    ArrayList<String> uncrawled_Urls = new ArrayList<String>();
    ArrayList<String> crawled_Urls = new ArrayList<String>();
    public boolean TERMINATE = false;

    public Spider() {
    }

    public void crawl(String url) {

        System.out.println(url);

        try {
            if (!TERMINATE) {
                // uncrawled_Urls.add(url);
                Document doc = Jsoup.connect(url).get(); // Fetching and parsing HTMl
                crawled_Urls.add(url);// Add current url to crawled list

                Elements links = doc.select("a[href]"); // Extracting all <a href=""> tags

                //- For each element OR <a href=""> tag ...
                for (Element link : links) {
                    // Extract the absolute href attribute (it contains the url we need) ...
                    String l = link.attr("abs:href");

                    /********************************************************************************************
                     * Preprocess Urls
                     * - url contains domain name e.g example.com, continue preprocessing
                     * - url does not exist in uncrawled_url and crawled_url list, add it to the uncrawled list
                     * - Remove it from the uncrawled list, if it exists
                     ***/

                    // ADD MORE PREPROCESSING CODE HERE ...

                    // If url does not exist in uncrawled_url and crawled list ...
                    if (!uncrawled_Urls.contains(l) && !crawled_Urls.contains(l)) {
                        uncrawled_Urls.add(l);//Then add it to unCrawled_url list
                    }// BUT if url exists in uncrawled_url list ...
                    else if (uncrawled_Urls.contains(l)) {
                        uncrawled_Urls.remove(l);  // Then remove it
                    }

                    // ADD MORE PREPROCESSING CODE HERE ...

                }//for Each loop END

                /******************************
                 *  Selecting new url to crawl
                 *
                 * */

                //If uncrawled list is not empty ...
                if (!uncrawled_Urls.isEmpty()) {
                    //And also, if uncrawled list has more than 1 element or urls ...
                    if (uncrawled_Urls.size() > 1) {
                        // Then generate random index (0 - list size)
                        int randomIndex = (int) (Math.random() * uncrawled_Urls.size());
                        // Then use random index to select element or url from list
                        String new_url = uncrawled_Urls.get(randomIndex);
                        crawl(new_url);  // Begin recursion
                    }
                } else {
                    TERMINATE = true;
                    System.out.println("No more urls in uncrawled list: \n Spider is terminating ...");
                }

            } // End of while loop

            System.out.println(uncrawled_Urls.size() + " urls uncrawled ");
            System.out.println(crawled_Urls.size() + " urls crawled ");

        } catch (IOException IO_error) {
            System.out.println("ERROR:");
            IO_error.printStackTrace();
        }
    }

}
