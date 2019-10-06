import java.util.*;

/**
 * BSTree Class that store 30 Links of the searched keyword
 */
public class BSTree implements Comparable<BSTree> {

    /**
     * Instance variable of this BSTree
     */
    private Link root;                                      // The root of the tree
    private String searchKeyword;                           // The search keyword that map to this tree
    private ArrayList<Link> reversedInOrderArrayList;       // A list in ascending PageRank
    private ArrayList<Link> ArrayListByIndex;               // A list in ascending Index
    private int totalSearch = 1;                            // Number of search of this keyword
    private int originalIndex;                              // The size of the tree. Doesn't decrement when delete a Link

    /**
     * Constructor: Initialize the BSTree with the search keyword
     */
    public BSTree(String name) {
        searchKeyword = name;
        reversedInOrderArrayList = new ArrayList<>();
        ArrayListByIndex = new ArrayList<>();
    }

    /**
     * Initialize the ArrayList in ascending Index with PageRank
     * @return: the arraylist in ascending index order
     */
    public ArrayList<Link> getArrayListByIndex() {
        reversedInOrderArrayList = ArrayListOfTreeByScore();
        ArrayListByIndex = new ArrayList<>();
        ArrayListByIndex.addAll(reversedInOrderArrayList);
        Collections.sort(ArrayListByIndex);
        return ArrayListByIndex;
    }


    /**
     * Set the PageRank for all the Links based on the score from high to low
     * @return an ArrayList with all Links in ascending PageRank
     */
    public ArrayList<Link> ArrayListOfTreeByScore() {
        reversedInOrderArrayList = new ArrayList<>();
        reversedInOrderArrayList = inOrderTreeWalkByScore(root);
        for (int i = 0; i < reversedInOrderArrayList.size(); i++) {
            int index = i + 1;
            reversedInOrderArrayList.get(i).setPageRank(index);
        }
        return reversedInOrderArrayList;
    }

    /**
     * Helper Function that traverse the tree in reversed inOrder
     */
    private ArrayList<Link> inOrderTreeWalkByScore(Link nodeLink) {
        if (nodeLink != null) {
            inOrderTreeWalkByScore(nodeLink.getRight());
            reversedInOrderArrayList.add(nodeLink);
            inOrderTreeWalkByScore(nodeLink.getLeft());
        }
        return reversedInOrderArrayList;
    }

    /**
     * Insert a new link into the tree
     * @param inp: the new Link
     */
    public void insertByScore(Link inp) {
        originalIndex++;
        Link curr = root;
        Link temp = null;
        while (curr != null) {
            temp = curr;
            if (inp.getTotalScore() < curr.getTotalScore()) {
                curr = curr.getLeft();
            } else {
                curr = curr.getRight();
            }
        }
        inp.setParent(temp);
        if (temp == null) root = inp;
        else if(inp.getTotalScore() < temp.getTotalScore()) temp.setLeft(inp);
        else temp.setRight(inp);
    }

    /**
     * Search a specific PageRank
     * @param inp: the PageRank that user input
     * @return the Link at that pageRank
     */
    public Link searchByPageRank(int inp) {
        return search(root, inp);
    }

    /**
     * Helper function when search the Link of the specific pagerank
     * @param treeLink: The Link that is being traversed
     * @param inp: The pageRank that user input
     * @return: the Link at that pageRank
     */
    private Link search(Link treeLink, int inp) {
        if (treeLink == null || treeLink.getPageRank() == inp) {
            return treeLink;
        }
        else if (inp < treeLink.getPageRank()) {
            return search(treeLink.getRight(), inp);
        }
        else {
            return search(treeLink.getLeft(), inp);
        }
    }

    /**
     * Get the minimum element of the tree
     * @param root: The tree to be used to find the mimimum
     * @return a Link that has smallest score
     */
    private Link treeMin(Link root) {
        Link curr = root;
        while (curr.getLeft() != null) {
            curr = curr.getLeft();
        }
        return curr;
    }

    /**
     * remove the link out of the BST
     * @param link: Link to be deleted
     */
    public void remove(Link link) {
        if (link.getLeft() == null) transplant(link, link.getRight());
        else if (link.getRight() == null) transplant(link, link.getLeft());
        else {
            Link smallest = treeMin(link.getRight());
            if (smallest.getParent() != link) {
                transplant(smallest, smallest.getRight());
                smallest.setRight(link.getRight());
                smallest.getRight().setParent(smallest);
            }
            transplant(link, smallest);
            smallest.setLeft(link.getLeft());
            smallest.getLeft().setParent(smallest);
        }
    }

    /**
     * Re-construct the sub-tree
     * Helper method to remove the tree
     */
    private void transplant(Link u, Link v) {
        if (u.getParent() == null) root = v;
        else if (u == u.getParent().getLeft()) u.getParent().setLeft(v);
        else u.getParent().setRight(v);
        if (v != null) {
            v.setParent(u.getParent());
        }
    }

    /**
     * Get and Set method for all instance variable of the BST Tree
     */
    public String getSearchKeyword() {
        return searchKeyword;
    }

    public int getOriginalIndex() {
        return originalIndex;
    }

    public int getTotalSearch() {
        return totalSearch;
    }

    public void increaseTotalSearch() {
        totalSearch++;
    }

    public void setArrayListByIndex() {
        ArrayListByIndex = getArrayListByIndex();
    }

    public void setReversedInOrderArrayList() {
        this.reversedInOrderArrayList = ArrayListOfTreeByScore();
    }

    @Override
    public int compareTo(BSTree o) {
        return 0;
    }
}
