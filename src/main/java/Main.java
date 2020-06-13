import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main {
    public  static void main(String[] args){
        System.out.println("Hello ... ");

        //Getting some simple HTML ...
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p id=\"txt\">Parsed HTML into a doc.</p></body></html>";
        //Parsing HTML using Jsoup ...
        Document doc = Jsoup.parse(html);

        //Extracting an element using Id ...
         Element elem = doc.getElementById("txt");
        System.out.print("Extracted element: \n"+ elem);
    }



}
