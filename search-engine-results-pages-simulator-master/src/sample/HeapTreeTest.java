package sample;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class HeapTreeTest {

    @Test
    void heapsort() {
        HeapTree h1 = new HeapTree("Calvin");
        ArrayList<Integer> originalList = new ArrayList<>();        // Original List
        ArrayList<Integer> compareList = new ArrayList<>();         //  A new List with the same list in HeapTree

        for (int i = 1; i < h1.getPageList().size(); i++) {
            compareList.add(h1.getPageList().get(i).getPageRank());
        }
        Collections.sort(compareList);      // Sort the list compareList

        h1.heapsort();      // Heap Sort
        for (int i = 1; i < h1.getPageList().size(); i++) {
            originalList.add(h1.getPageList().get(i).getPageRank());
        }

        // Remove the first element to avoid NullPointer Exception
        originalList.remove(0);
        compareList.remove(0);

        // Check if the pagerank is compared in order or not
        assertEquals(originalList, compareList);
    }

    @Test
    void heapExtractMax() {
        HeapTree h1 = new HeapTree("Calvin");
        Link first = h1.heapMaximum();
        Link curr = h1.heapExtractMax();

        assertEquals(first, curr);
    }
}