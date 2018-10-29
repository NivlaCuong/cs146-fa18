package hw2;

/**
 * A class has 2 sorting algorithms: Insertion and Merge Sort
 */
class sortAlgorithm {

    int firstPivot;
    /**
     * Run Quick Sort Algorithm on the specified list
     * @param A Aay of Integer
     * @return return a new sorted Aay in place (Space: O(1))
     */
    public void quickSort(int[] A, int lo, int hi) {
        if (lo < hi) {
            int q = partition(A, lo, hi);
            quickSort(A, lo, q - 1);
            quickSort(A, q + 1, hi);
        }
    }

    private int partition(int[] A, int lo, int hi) {
        int valPivot = A[hi];
        int index = lo - 1;
        for (int i = lo; i < hi; i++) {
            if (A[i] <= valPivot) {
                index++;
                swap(A, index, i);
            }
        }
        swap(A, index + 1, hi);
        return index + 1;
    }

    /**
     * Run Quick Sort Algorithm on the specified list
     * @param A Aay of Integer
     * @return return a new sorted Aay in place (Space: O(1))
     */
    public void quickSortDualPivot(int[] A, int lo, int hi) {
        if (lo < hi) {

            int secondPivot = partitionDual(A, lo, hi);
            int firstPivot = this.firstPivot;
            quickSort(A, lo, firstPivot - 1);
            quickSort(A, firstPivot + 1, secondPivot - 1);
            quickSort(A, secondPivot + 1, hi);
        }
    }

    private int partitionDual(int[] A, int lo, int hi) {
        if (A[lo] > A[hi]) {
            swap(A, lo, hi);
        }
        int firstPivotVal = A[lo];
        int secondPivotVal = A[hi];


        int indexLeft = lo + 1;
        int indexRight = hi - 1;

        for (int k = lo + 1; k <= indexRight; k++) {
            if (A[k] < firstPivotVal) {
                swap(A, indexLeft, k);
                indexLeft++;
            }

            else if (A[k] >= secondPivotVal) {
                while (A[indexRight] > secondPivotVal && k < indexRight)
                    indexRight--;
                swap(A, k, indexRight);
                indexRight--;
                if (A[k] < firstPivotVal) {
                    swap(A, k, indexLeft);
                    indexLeft++;
                }
            }
        }

        indexLeft--;
        indexRight++;

        swap(A, lo, indexLeft);
        swap(A, hi, indexRight);

        this.firstPivot = indexLeft;
        return indexRight;
    }
    
    void swap(int[] A, int first, int second) {
        int temp = A[first];
        A[first] = A[second];
        A[second] = temp;
    }


}
