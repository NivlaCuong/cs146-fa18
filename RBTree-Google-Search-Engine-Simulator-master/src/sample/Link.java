package sample;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The class to describe each URL that is inserted into the PA3.BSTree
 */
public class Link implements Comparable<Link> {
    private String title, URL;                  // Title and URL of the Link
    private int pageRank, totalScore, index;    // The integer of this Link
    private Link parent, left, right;           // The parent, left, right Link of this current Link
    private String color;

    /**
     * Empty Constructor that initialize a new Link as a leave
     * This Link will not be presented in display
     */
    public Link() {
        this.color = "Black";
        this.totalScore = -1;
    }
    /**
     * A Constructor creates a new Link Object with specific factors
     * @param item: Title of the URL
     * @param URL: the URL
     * @param index: The position when it is inserted into the PA3.BSTree
     * @param factor1: Factor 1
     * @param factor2: factor 2
     * @param factor3: factor 3
     * @param factor4: factor 4
     */
    public Link(String item, String URL, int index, int factor1, int factor2, int factor3, int factor4) {
        this.title = item;
        this.URL = URL;
        this.totalScore = factor1 + factor2 + factor3 + factor4;
        this.pageRank = 0;
        this.index = index;
        this.color = "Red";
    }

    /**
     * A Constructor creates a new Link Object
     * @param item: The title of the Link
     * @param URL: The URL of the Link
     */
    public Link(String item, String URL, int index) {
        this.title = item;
        this.URL = URL;
        this.totalScore = generateRandomScore();
        this.pageRank = 0;
        this.index = index;
        this.color = "Red";

    }

    /**
     * Generate 4 different factors for the pageRank
     * @return the total score for a page
     */
    private int generateRandomScore() {
        int factor1 = ThreadLocalRandom.current().nextInt(0, 100);
        int factor2 = ThreadLocalRandom.current().nextInt(0, 100);
        int factor3 = ThreadLocalRandom.current().nextInt(0, 100);
        int factor4 = ThreadLocalRandom.current().nextInt(0, 100);
        return factor1 + factor2 + factor3 + factor4;
    }

    /**
     * Get and Set function of all necessary private variables
     */

    public Link getLeft() {
        return left;
    }

    public Link getRight() {
        return right;
    }

    public Link getParent() {
        return parent;
    }

    public void setParent(Link parent) {
        this.parent = parent;
    }

    public void setLeft(Link left) {
        this.left = left;
    }

    public void setRight(Link right) {
        this.right = right;
    }

    public int getIndex() {
        return index;
    }

    public int getPageRank() {
        return pageRank;
    }

    public void setPageRank(int pageRank) {
        this.pageRank = pageRank;
    }

    public String getURL() {
        return URL;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "[" + index + "] " + "Title: " + this.title + "----URL: " + this.URL + " ----- Page Rank: " + pageRank + " ---------Total Score: " + totalScore;
    }

    /**
     * Compare the index of the link
     * @param o: the Link to compared
     * @return 1 if this Link's index is larger, -1 if lesser, 0 otherwise
     */
    @Override
    public int compareTo(Link o) {
        if (this.index < o.index) return -1;
        else if (this.index > o.index) return 1;
        return 0;
    }

}