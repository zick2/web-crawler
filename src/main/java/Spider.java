import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Spider {
    //TODO :
    // - Make http requests and extract urls recursively from sites
    // - process and Store each url
    // - scrape general and specific data [tables, and specified CSS selectors or HTML tags]
    // - Create a robots.txt file reader method (This will stop the spider from violating website crawling rules)

    public Spider(){}

    public void crawl(String url){
        System.out.println("Crawling ...");
        try{
            //Fetching and parsing HTMl ...
            Document doc = Jsoup.connect(url).get();
            //Extracting all <a href=""> tags
            Elements links = doc.select("a[href]");
            //For each <a href=""> tag extract the href attribute (it contains the urls we need)

            for(Element link : links){
                System.out.println("url : "+ link.attr("abs:href"));
            }
            System.out.println( links.size() +" uls");
        } catch (IOException IO_error){
            System.out.println("ERROR:");
            IO_error.printStackTrace();
        }
    }

}
