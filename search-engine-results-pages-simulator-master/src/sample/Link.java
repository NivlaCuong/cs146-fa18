package sample;

/**
 * The class for each new page Link
 */
public class Link implements Comparable<Link> {
    private String title;
    private String URL;
    private int pageRank;
    private int position;

    /**
     * A Constructor creates a new Link Object
     * @param item: The title of the Link
     * @param URL: The URL of the Link
     * @param pageRank: The current score of the Link
     */
    public Link(String item, String URL, int pageRank) {
        this.title = item;
        this.URL = URL;
        this.pageRank = pageRank;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Get and Set function of private variables
     */
    public int getPageRank() {
        return pageRank;
    }

    public void setPageRank(int pageRank) {
        this.pageRank = pageRank;
    }

    public String getURL() {
        return URL;
    }

    @Override
    public String toString() {
        return "[" + position + "]" + "Title: " + this.title + "----URL: " + this.URL + " -----Page Rank: " + pageRank;
    }

    @Override
    public int compareTo(Link o) {
        if (this.pageRank < o.pageRank) return -1;
        else if (this.pageRank > o.pageRank) return 1;
        return 0;
    }
}