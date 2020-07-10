public class Main {
    public static void main(String[] args) {

        /**
         * Websites for Testing:
         *  https://asoftmurmur.com/
         *  http://www.sciencefix.com/
         *  http://kauaimark.blogspot.com/
         *  https://www.miss-thrifty.co.uk
         * */

        String start_url = "https://www.tesla.com/";
        String domain = "tesla.com";

        Spider mySpider = new Spider();
      //  mySpider.crawl(start_url, domain);
        mySpider.debugMode(start_url);

    }
}
