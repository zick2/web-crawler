public class Main {
    public  static void main(String[] args){

        /**
         * Websites for Testing:
         * 1) http://www.sciencefix.com/
         * 2) http://kauaimark.blogspot.com/
         * 3) https://www.miss-thrifty.co.uk
         * */

        String start_url = "http://kauaimark.blogspot.com/";

        Spider mySpider = new Spider();
        mySpider.crawl(start_url);

    }
}
