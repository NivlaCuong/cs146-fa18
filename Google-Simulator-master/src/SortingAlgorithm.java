import java.net.*;
import java.util.*;

/**
 * A class has 2 sorting algorithms: quicksort and BucketSort
 */
class SortingAlgorithm {
    /**
     * Run Quick Sort Algorithm on the specified list
     * @param A Aay of Integer
     * @return return a new sorted Aay in place (Space: O(1))
     */
    public void quickSort(ArrayList<Link> A, int lo, int hi) {
        if (lo < hi) {
            int q = partition(A, lo, hi);
            quickSort(A, lo, q - 1);
            quickSort(A, q + 1, hi);
        }
    }

    /**
     * Swap the element in the array in-place
     * @param A: ArrayList to be swap
     * @param lo: the starting position
     * @param hi: the ending position
     * @return index that separate 2 sub-arrays
     */
    private int partition(ArrayList<Link> A, int lo, int hi) {
        int valPivot = A.get(hi).getPageRank();
        int index = lo - 1;
        for (int i = lo; i < hi; i++) {
            if (A.get(i).getPageRank() <= valPivot) {
                index++;
                swap(A, index, i);
            }
        }
        swap(A, index + 1, hi);
        return index + 1;
    }

    /**
     * Swap 2 Links' position in the array
     * @param A: ArrayList to be swap
     * @param first: first Link to be swapped
     * @param second: second Link to be swapped
     */
    void swap(ArrayList<Link> A, int first, int second) {
        Link temp = A.get(first);
        A.set(first, A.get(second));
        A.set(second, temp);
    }

    /**
     * Sort the List of Links by its domain Name
     * @param A: Array to be sorted
     * @return a new sorted Array by domain Name
     * @throws URISyntaxException
     */
    public ArrayList<Link> bucketSortByDomainName(ArrayList<Link> A) throws URISyntaxException {
        HashMap<Character, ArrayList<Link>> map = new HashMap<>();
        ArrayList<Link> result = new ArrayList<>();
        for (Character i = 'a'; i <= 'z'; i++) map.put(i, new ArrayList<>());
        for (Link curr : A) map.get(getDomainName(curr.getURL()).charAt(0)).add(curr);
        for(ArrayList<Link> list : map.values()) {
            insertionSort(list);
            result.addAll(list);
        }
        return result;
    }

    /**
     * Run insertionSort Algorithm on the specified list
     * @param A array of Integer
     * @return return a new sorted array in place (Space: O(1))
     */
    void insertionSort(ArrayList<Link> A) throws URISyntaxException {
        for (int i = 1; i < A.size(); i++) {
            String domainNameKey = getDomainName(A.get(i).getURL());
            int j = i - 1;
            Link key = A.get(i);
            while (j >= 0 && getDomainName(A.get(j).getURL()).compareTo(domainNameKey) > 0) {
                A.set(j + 1, A.get(j));
                j--;
            }
            A.set(j + 1, key);
        }
    }

    /**
     * Get the domain of the long URLs
     * StackOverflow source: https://stackoverflow.com/questions/9607903/get-domain-name-from-given-url
     * @param url: the whole url
     * @return the domain name of the URL
     * @throws URISyntaxException
     */
    public String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}