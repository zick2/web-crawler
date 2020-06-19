import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public  static void main(String[] args){
        System.out.println("Crawler ... ");

        /**
         * Websites for Testing:
         * 1) http://www.sciencefix.com/
         * 2) http://kauaimark.blogspot.com/
         * 3) https://www.miss-thrifty.co.uk
         * */
        String start_url = "http://www.sciencefix.com/";

        try{
            //Fetching and parsing HTMl file ...
            Document doc = Jsoup.connect(start_url).get();
            //Extracting all <a href=""> tags
            Elements links = doc.select("a[href]");
            //For each tag extract the href attribute (it contains the urls we need)
            for(Element url : links){
                System.out.println("url : "+url.attr("abs:href"));
            }

        }catch (IOException IO_error){
            System.out.println("ERROR:");
            IO_error.printStackTrace();
        }

    }



}
