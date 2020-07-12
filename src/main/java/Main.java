
/**
 * Websites for Testing:
 *  https://asoftmurmur.com/
 *  http://www.sciencefix.com/
 *  http://kauaimark.blogspot.com/
 *  https://www.miss-thrifty.co.uk
 *  https://www.piboco.com/
 *
 * */
public class Main {
    public static void main(String[] args) {
        String[] urls = {"https://asoftmurmur.com/",
                         " http://kauaimark.blogspot.com/",
                         " https://www.miss-thrifty.co.uk"};

        String[] start_url = {"https://www.piboco.com/"};

        Spider mySpider = new Spider();
         mySpider.crawl(start_url, mySpider.extractDomains(start_url), true);

        //mySpider.debugMode(start_url);
      //  extractDomain(urls);

    }

}
