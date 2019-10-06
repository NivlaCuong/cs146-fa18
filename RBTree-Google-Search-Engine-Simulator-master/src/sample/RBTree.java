package sample;

import java.util.ArrayList;
import java.util.Collections;

/**
 * RBTree Class that store 30 Links of the searched keyword
 */
public class RBTree {

    /**
     * Instance variable of this RBTree
     */
    private Link root;                                      // The root of the tree
    private String searchKeyword;                           // The search keyword that map to this tree
    private ArrayList<Link> reversedInOrderArrayList;       // A list in ascending PageRank
    private ArrayList<Link> ArrayListByIndex;               // A list in ascending Index
    private int totalSearch = 1;                            // Number of search of this keyword
    private int originalIndex;                              // The size of the tree. Doesn't decrement when delete a Link
    private Link sentinel;

    /**
     * Constructor: Initialize the RBTree with the search keyword
     */
    public RBTree(String name) {
        searchKeyword = name;
        reversedInOrderArrayList = new ArrayList<>();
        ArrayListByIndex = new ArrayList<>();
        sentinel = new Link();
        root = sentinel;
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
            if (nodeLink.getTotalScore() > 0)
                reversedInOrderArrayList.add(nodeLink);
            inOrderTreeWalkByScore(nodeLink.getLeft());
        }
        return reversedInOrderArrayList;
    }

    /**
     * Rotate left the Sub-stree
     * @param link
     */
    public void leftRotate(Link link) {
        Link y = link.getRight();
        link.setRight(y.getLeft());
        if (y.getLeft() != sentinel) {
            y.getLeft().setParent(link);
        }
        y.setParent(link.getParent());
        if (link.getParent() == sentinel) {
            root = y;
        } else if (link == link.getParent().getLeft()) {
            link.getParent().setLeft(y);
        }
        else {
            link.getParent().setRight(y);
        }
        y.setLeft(link);
        link.setParent(y);
    }

    /**
     * Rotate right the Sub-stree
     * @param link
     */
    public void rightRotate(Link link) {
        Link y = link.getLeft();
        link.setLeft(y.getRight());
        if (y.getRight() != sentinel) {
            y.getRight().setParent(link);
        }
        y.setParent(link.getParent());
        if (link.getParent() == sentinel) {
            root = y;
        } else if (link == link.getParent().getRight()) {
            link.getParent().setRight(y);
        }
        else {
            link.getParent().setLeft(y);
        }
        y.setRight(link);
        link.setParent(y);
    }

    /**
     * Insert a new link into the tree
     * @param inp: the new Link
     */
    public void insertByScore(Link inp) {
        originalIndex++;
        Link curr = root;
        Link parent = sentinel;
        while (curr != sentinel) {
            parent = curr;
            if (inp.getTotalScore() < curr.getTotalScore()) {
                curr = curr.getLeft();
            } else {
                curr = curr.getRight();
            }
        }
        inp.setParent(parent);
        if (parent == sentinel) {
            root = inp;
        }
        else if(inp.getTotalScore() < parent.getTotalScore()) parent.setLeft(inp);
        else parent.setRight(inp);

        inp.setLeft(sentinel);
        inp.setRight(sentinel);
        inp.setColor("Red");

        rbInsertFixUp(inp);
    }

    /**
     * Helper Function of inserting a new URL
     * Follow the rule of RBTree. Have 3 cases, and it will rotate the tree based on the case of the tree
     * @param inp: the inserted URL
     */
    private void rbInsertFixUp(Link inp) {
        while (inp.getParent().getColor().equals("Red")) {
            if (inp.getParent() == inp.getParent().getParent().getLeft()) {
                Link uncle = inp.getParent().getParent().getRight();
                if (uncle.getColor().equals("Red")) {
                    inp.getParent().setColor("Black");
                    uncle.setColor("Black");
                    inp.getParent().getParent().setColor("Red");
                    inp = inp.getParent().getParent();
                } else {
                    if (inp == inp.getParent().getRight()) {
                        inp = inp.getParent();
                        leftRotate(inp);
                    }
                    inp.getParent().setColor("Black");
                    inp.getParent().getParent().setColor("Red");
                    rightRotate(inp.getParent().getParent());
                }
            } else {
                Link uncle = inp.getParent().getParent().getLeft();
                if (uncle.getColor().equals("Red")) {
                    inp.getParent().setColor("Black");
                    uncle.setColor("Black");
                    inp.getParent().getParent().setColor("Red");
                    inp = inp.getParent().getParent();
                } else {
                    if (inp == inp.getParent().getLeft()) {
                        inp = inp.getParent();
                        rightRotate(inp);
                    }
                    inp.getParent().setColor("Black");
                    inp.getParent().getParent().setColor("Red");
                    leftRotate(inp.getParent().getParent());
                }
            }
        }
        root.setColor("Black");
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
        if (treeLink == sentinel || treeLink.getPageRank() == inp) {
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
        while (curr.getLeft() != sentinel) {
            curr = curr.getLeft();
        }
        return curr;
    }

    /**
     * remove the link out of the RBT
     * @param link: Link to be deleted
     */
    public void rbDelete(Link link) {
        Link curr = link;
        Link temp;
        String currColor = curr.getColor();
        if (link.getLeft() == sentinel) {
            temp = link.getRight();
            rbTransplant(link, link.getRight());
        } else if (link.getRight() == sentinel) {
            temp = link.getLeft();
            rbTransplant(link, link.getLeft());
        } else {
            curr = treeMin(link.getRight());
            currColor = curr.getColor();

            temp = curr.getRight();

            if (curr.getParent() == link) {
                temp.setParent(curr);
            } else {
                rbTransplant(curr, temp);
                curr.setRight(link.getRight());
                curr.getRight().setParent(curr);
            }
            rbTransplant(link, curr);
            curr.setLeft(link.getLeft());
            curr.getLeft().setParent(curr);
            curr.setColor(link.getColor());
        }
        if (currColor.equals("Black")) {
            rbDeleteFixup(temp);
        }
    }

    /**
     * Re-construct the sub-tree
     * Helper method to remove the tree
     */
    private void rbTransplant(Link u, Link v) {
        if (u.getParent() == sentinel) root = v;
        else if (u == u.getParent().getLeft()) u.getParent().setLeft(v);
        else u.getParent().setRight(v);
        v.setParent(u.getParent());
    }

    /**
     * Helper Function of deleting the URL
     * Follow the rule of RBTree. Have 4 cases, and it will rotate the tree based on the case of the tree
     * @param inp: the deleted URL
     */
    private void rbDeleteFixup(Link inp) {
        while (inp != root && inp.getColor().equals("Black")) {
            if (inp == inp.getParent().getLeft()) {             // If it's left child
                Link sibling = inp.getParent().getRight();
                if (sibling.getColor().equals("Red")) {
                    sibling.setColor("Black");
                    inp.getParent().setColor("Red");
                    leftRotate(inp.getParent());
                    sibling = inp.getParent().getRight();
                }
                if (sibling.getLeft().getColor().equals("Black") && sibling.getRight().getColor().equals("Black")
                        || (sibling.getRight() == sentinel && sibling.getLeft() == sentinel)) {
                    sibling.setColor("Red");
                    inp = inp.getParent();
                } else {
                    if (sibling.getRight() == sentinel) {
                        sibling.getLeft().setColor("Black");
                        sibling.setColor("Red");
                        rightRotate(sibling);
                        sibling = inp.getParent().getRight();
                    } else if (sibling.getRight().getColor().equals("Black")) {
                        sibling.getLeft().setColor("Black");
                        sibling.setColor("Red");
                        rightRotate(sibling);
                        sibling = inp.getParent().getRight();
                    }
                    sibling.setColor(inp.getParent().getColor());
                    inp.getParent().setColor("Black");
                    sibling.getRight().setColor("Black");
                    leftRotate(inp.getParent());
                    inp = root;
                }
            } else {                                        // If it's a right child
                Link sibling = inp.getParent().getLeft();
                if (sibling.getColor().equals("Red")) {
                    sibling.setColor("Black");
                    inp.getParent().setColor("Red");
                    rightRotate(inp.getParent());
                    sibling = inp.getParent().getLeft();
                }
                if (sibling.getRight().getColor().equals("Black") && sibling.getLeft().getColor().equals("Black") || (sibling.getRight() == sentinel && sibling.getLeft() == sentinel)) {
                    sibling.setColor("Red");
                    inp = inp.getParent();
                } else {
                    if (sibling.getLeft() == sentinel) {
                        sibling.getRight().setColor("Black");
                        sibling.setColor("Red");
                        leftRotate(sibling);
                        sibling = inp.getParent().getLeft();
                    } else if (sibling.getLeft().getColor().equals("Black")) {
                        sibling.getRight().setColor("Black");
                        sibling.setColor("Red");
                        leftRotate(sibling);
                        sibling = inp.getParent().getLeft();
                    }
                    sibling.setColor(inp.getParent().getColor());
                    inp.getParent().setColor("Black");
                    sibling.getLeft().setColor("Black");
                    rightRotate(inp.getParent());
                    inp = root;
                }
            }
        }
        inp.setColor("Black");
    }

    /**
     * Get and Set method for all instance variable of the RBT Tree
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
}
