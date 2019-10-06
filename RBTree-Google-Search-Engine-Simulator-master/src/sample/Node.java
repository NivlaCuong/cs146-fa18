package sample;

/**
 * A class that store the Search Name and its total search
 */
public class Node implements Comparable<Node>  {
    private String searchName;      // search keyword that user used to search
    private int totalSearch;        // number of search that user searches the keyword

    /**
     * Constructor that initialize the search keyword with number of search of that keyword
     * @param name
     * @param totalSearch
     */
    public Node(String name, int totalSearch) {
        this.searchName = name;
        this.totalSearch = totalSearch;
    }

    /**
     * Get Method that is used to display to the Table View in the UI
     * @return
     */
    public int getTotalSearch() {
        return totalSearch;
    }

    public String getSearchName() {
        return searchName;
    }

    @Override
    public String toString() {
        return searchName + " - " + totalSearch + " search";
    }

    /**
     * Compare the number of search in Descending order so keyword with most search will appear on top
     * @param o: the compared Node
     * @return 1 if less, -1 if larger, 0 otherwis
     */
    @Override
    public int compareTo(Node o) {
        if (this.totalSearch > o.totalSearch) return -1;
        else if (this.totalSearch < o.totalSearch) return 1;
        return 0;
    }
}