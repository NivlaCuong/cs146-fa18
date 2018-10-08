package hw1;

class sortingTester {

    private int[] reversedList = new int[20], sortedList = new int[20];
    private int[] avgList = { 12, 14, 10, 8, 6, 5, 11, 7, 13, 19, 17, 16, 0, 4, 15, 18, 1, 2, 9};
    private sortAlgorithm sort = new sortAlgorithm();

    /**
     * Constructor: init the reversed and sorted list
     */
    sortingTester() {
        initReverseList();
        initSortedList();
    }

    /**
     * Testing Worst Case for both algorithm
     */
    void testingCase(String caseName, int[] list) {
        System.out.println(caseName + " Case");
        System.out.print("Original List: ");
        printList(list);
        System.out.println();

        getRunningTime(list,"Insertion");   // Insertion Sort
        getRunningTime(list,"MergeSort");   // MergeSort
    }

    /**
     * Calculate the time and print out the time for each algorithm
     * @param inp: array of integer which is used to test the time to execute algorithms
     * @param algorithmName: the name of the algorithm
     */
    private void getRunningTime(int[] inp, String algorithmName) {
        int[] temp = inp.clone();   // Create a new array so it doesn't modify the original array
        long startTime = System.nanoTime();

        if (algorithmName.equals("MergeSort")) sort.mergeSort(temp, 0, inp.length - 1);
        else sort.insertionSort(temp);

        long runTime = System.nanoTime() - startTime;

        System.out.print("After " + algorithmName + ": ");
        printList(temp);
        System.out.println("The running time is: " + runTime);
        System.out.println();
    }

    /**
     * Initialize the array in reversed order
     */
    private void initReverseList() {
        int count = 0;
        for (int i = reversedList.length - 1; i >= 0; i--) {
            reversedList[count] = i;
            count++;
        }
    }

    /**
     * Initialize the array in sorted order
     */
    private void initSortedList() {
        for (int i = 0; i < sortedList.length; i++) sortedList[i] = i;
    }

    /**
     * Print out element in the list
     * @param A: array of integer
     */
    private void printList(int[] A) {
        for (int i = 0; i < A.length; i++) {
            System.out.print(A[i] + " ");
        }
        System.out.println();
    }

    public int[] getAvgList() {
        return avgList;
    }

    public int[] getReversedList() {
        return reversedList;
    }

    public int[] getSortedList() {
        return sortedList;
    }
}
