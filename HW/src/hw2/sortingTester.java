package hw2;

class sortingTester {

    private int[] reversedList = new int[20];
    private int[] bestList = { 10, 40, 30, 20, 80, 60, 90, 70, 50, 150, 110, 140, 130, 120, 180, 160, 170, 190, 200, 100 };
    private int[] avgList = { 1120, 124, 1032, 84, 6, 5, 2411, 72, 1213, 194, 17, 166, 180, 49, 15, 818, 31, 200, 93, 1 };
    private sortAlgorithm sort = new sortAlgorithm();

    /**
     * Constructor: init the reversed and sorted list
     */
    sortingTester() {
        initReverseList();
    }

    /**
     * Testing Worst Case for both algorithm
     */
    void testingCase(String caseName, int[] list) {
        System.out.println(caseName + " Case");
        System.out.print("Original List: ");
        printList(list);
        System.out.println();

        getRunningTime(list, "One Pivot");
        getRunningTime(list, "Two Pivots");
    }

    /**
     * Calculate the time and print out the time for each algorithm
     * @param inp: array of integer which is used to test the time to execute algorithms
     */
    private void getRunningTime(int[] inp, String algorithmName) {
        int[] temp = inp.clone();   // Create a new array so it doesn't modify the original array
        long startTime = System.nanoTime();

        if (algorithmName.equals("One Pivot")) sort.quickSort(temp, 0, inp.length - 1);
        else sort.quickSortDualPivot(temp, 0, inp.length - 1);

        long runTime = System.nanoTime() - startTime;

        System.out.print("After Quick Sort with " + algorithmName + ": " );
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

    public int[] getBestList() {
        return bestList;
    }
}
