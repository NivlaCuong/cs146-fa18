package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Heap Tree represents the searches based on the Keyword that the user wants to search
 */
public class HeapTree implements Comparable<HeapTree> {

    private String searchName;
    private ArrayList<Link> pageList;
    private int heapSize;
    private int totalSearch;

    /**
     * A Constructor creates a new HeapTree
     * @param searchName a searched Name for the HeapTree
     */
    public HeapTree(String searchName) {
        this.searchName = searchName;
        pageList = new ArrayList<>();
        pageList.add(null);
        heapSize = 0;
        totalSearch = 1;
        addLinksToPageList(searchName);
    }

    /**
     * Get and Set function of private variables
     */
    public String getSearchName() {
        return searchName;
    }

    public ArrayList<Link> getPageList() {
        return pageList;
    }

    public int getTotalSearch() {
        return totalSearch;
    }

    public void increaseTotalSearch() {
        this.totalSearch++;
    }

    /**
     * Bubble up the element if it's bigger than the parent
     * @param i: starting position
     */
    private void maxHeapify(int i) {
        int left = getLeftOf(i);
        int right = getRightOf(i);
        int largest;

        if (left <= heapSize && pageList.get(left).getPageRank() >= pageList.get(i).getPageRank()) {
            largest = left;
        } else {
            largest = i;
        }

        if (right <= heapSize && pageList.get(right).getPageRank() >= pageList.get(largest).getPageRank()) {
            largest = right;
        }

        if (largest != i) {
            swap(largest, i);
            maxHeapify(largest);
        }
    }

    /** Swap the Links at the two indices. */
    private void swap(int i1, int i2) {
        Link Link1 = getLink(i1);
        Link Link2 = getLink(i2);
        this.pageList.set(i1, Link2);
        this.pageList.set(i2, Link1);
    }

    /**
     * Returns the i of the Link to the left of the Link at i.
     */
    private int getLeftOf(int i) {
        return 2 * i;
    }

    /**
     * Returns the i of the Link to the right of the Link at i.
     */
    private int getRightOf(int i) {
        return 2 * i + 1;
    }

    /**
     * Returns the i of the Link that is the parent of the Link at i.
     */
    private int getParentOf(int i) {
        return i / 2;
    }

    /**
     * Get the Link at a specific index
     * @param i: index
     * @return the Link at that index
     */
    private Link getLink(int i) {
        if (i >= pageList.size()) {
            return null;
        } else {
            return pageList.get(i);
        }
    }

    /**
     * Build the Max HeapTree
     */
    private void buildMaxHeap() {
        for (int i = heapSize / 2; i > 0; i--) {
            maxHeapify(i);
        }
    }

    /**
     * Sort the Heaptree
     */
    public void heapsort() {
        buildMaxHeap();
        for (int i = heapSize; i > 1; i--) {
            swap(1, i);
            this.heapSize--;
            maxHeapify(1);
        }
    }

    /**
     * Insert a new Link to the HeapTree
     * @param link
     */
    private void maxHeapInsert(Link link) {
        this.heapSize++;
        pageList.add(link);
        heapIncreasePageRank(this.heapSize, link.getPageRank());
    }

    /**
     * Remove the first element of the tree
     * @return the maximum element of the HeapTree
     */
    public Link heapExtractMax() {
        if (this.heapSize < 1) return null;
        Link result = heapMaximum();
        swap(1, this.heapSize);
        this.heapSize--;
        maxHeapify(1);
        return result;
    }

    /**
     * Update the new key for the Element
     * @param i: the position of the key
     * @param newPageRank: new key
     */
    public void heapIncreasePageRank(int i, int newPageRank) {
        if (newPageRank < pageList.get(i).getPageRank()) {
            System.out.println("The new key is smaller than the current pageRank");
        }
        pageList.get(i).setPageRank(newPageRank);
        while (i > 1 && pageList.get(getParentOf(i)).getPageRank() < pageList.get(i).getPageRank()) {
            swap(getParentOf(i), i);
            i = getParentOf(i);
        }
    }

    /**
     * Get the first element of the HeapTree without removing it
     * @return the first element of the HeapTree
     */
    public Link heapMaximum() {
        return pageList.get(1);
    }

    /**
     * Display the top 10 results for the keyword
     * @return an array of top 10 search
     */
    public ArrayList<Link> displayTopTenResult() {
        int count = 1;
        ArrayList<Link> temp = new ArrayList<>();
        ArrayList<Link> result = new ArrayList<>();
        for (int i = 1; i < pageList.size(); i++) {
            temp.add(pageList.get(i));
        }
        Collections.sort(temp);
        for (int i = temp.size() - 1; i > 0; i--, count++) {
            temp.get(i).setPosition(count);
            if (count <= 10) result.add(temp.get(i));
        }
        return result;
    }

    /**
     * Connect to Google and get all the links and insert into tree
     * @param name: search keyword
     */
    private void addLinksToPageList(String name) {
        try {
            String url = "https://www.google.com/search?q=" + name + "&num=100";
            Document doc = Jsoup.connect(url).get();
            Elements newsHeadlines = doc.select("a[href]");
            for (Element headline : newsHeadlines) {
                if (!headline.select("h3").text().isEmpty()) {
                    String title = headline.select("h3").text();
                    String URL = headline.absUrl("href");
                    int pageRank = generateRandomPageRank();
                    Link newLink = new Link(title, URL, pageRank);
                    maxHeapInsert(newLink);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate 4 different factors for the pageRank
     * @return the total score for a page
     */
    private int generateRandomPageRank() {
        int factor1 = ThreadLocalRandom.current().nextInt(0, 100);
        int factor2 = ThreadLocalRandom.current().nextInt(0, 100);
        int factor3 = ThreadLocalRandom.current().nextInt(0, 100);
        int factor4 = ThreadLocalRandom.current().nextInt(0, 100);
        return factor1 + factor2 + factor3 + factor4;
    }

    /**
     * Compare the number of search for 2 different trees
     * @param o: 2nd HeapTree
     * @return 1 if it's less than the other tree
     */
    @Override
    public int compareTo(HeapTree o) {
        if (this.getTotalSearch() > o.getTotalSearch()) return -1;
        else if(this.getTotalSearch() < o.getTotalSearch()) return 1;
        return 0;
    }

    /**
     * Print out the Heap Tree
     * @return the list of elements in the HeapTree
     */
    @Override
    public String toString() {
        return pageList.toString();
    }
}
