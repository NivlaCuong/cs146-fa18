package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Web Crawler that will use JSOUP Library to scan through the page and get all the elements of the page
 */
public class WebCrawler {

    /**
     * Connect to Google and get 100 Links and insert only 30 URLs into tree
     * @param name: Search keyword
     * @return a PA3.RBTree of the search keyword
     */

    public RBTree createRBT(String name) {
        RBTree root = new RBTree(name);
        int index = 0;
        String url = "https://www.google.com/search?q=" + name + "&num=100";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements newsHeadlines = doc.select("a[href]");
            for (Element headline : newsHeadlines) {
                if (!headline.select("h3").text().isEmpty() && index < 30) {
                    String title = headline.select("h3").text();
                    String URL = headline.absUrl("href");
                    index++;
                    Link newLink = new Link(title, URL, index);
                    root.insertByScore(newLink);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
