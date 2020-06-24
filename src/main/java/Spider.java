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
    // - Preprocess and Store each extracted url :
    //      * Collect urls that are in the target domain [Optional]
    //      * Make sure it deos not exist in the crawled list
    // [Repeat]

    ArrayList<String> unCrawled_Urls = new ArrayList<String>();
    ArrayList<String> crawled_Urls = new ArrayList<String>();

    public Spider() {
    }

    public void crawl(String url) {

        System.out.println("Crawling ...");

        try {
            // crawled_Urls.add(url);
                Document doc = Jsoup.connect(url).get(); // Fetching and parsing HTMl
                Elements links = doc.select("a[href]"); // Extracting all <a href=""> tags

                // For each <a href=""> tag extract the href attribute (it contains the urls we need)
                for (Element link : links) {
                    System.out.println("url : " + link.attr("abs:href"));
                }
                System.out.println(links.size() + " uls");
        } catch (IOException IO_error) {
            System.out.println("ERROR:");
            IO_error.printStackTrace();
        }
    }

}
