package hw1;

/**
 * A class has 2 sorting algorithms: Insertion and Merge Sort
 */
class sortAlgorithm {

    /**
     * Run insertionSort Algorithm on the specified list
     * @param A array of Integer
     * @return return a new sorted array in place (Space: O(1))
     */
    void insertionSort(int[] A) {
        for (int i = 1; i < A.length; i++) {
            int key = A[i];
            int j = i - 1;
            while (j >= 0 && A[j] > key) {
                A[j + 1] = A[j];
                j--;
            }
            A[j + 1] = key;
        }
    }

    /**
     * Split the array into half recursively until it reach to 1 element
     * Then, merge the array
     * @param A: array of integer
     * @param low: the starting number of the array
     * @param hi: the end of the array, sub-array
     * @return
     */
    void mergeSort(int[] A, int low, int hi) {
        if (low < hi) {
            int mid = (low + hi) / 2;
            mergeSort(A, low, mid);
            mergeSort(A, mid + 1, hi);
            merge(A, low, mid, hi);
        }
    }

    /**
     * Compare integers of the 2 sub-arrays
     * @param A: array of integer
     * @param low: starting index of first half
     * @param mid: starting index of the second half
     * @param hi: end index of the second half
     */
    private void merge(int[] A, int low, int mid, int hi) {
        int length1 = mid - low + 1;
        int length2 = hi - mid;
        int[] firstHalf = new int[length1 + 1];
        int[] secondHalf = new int[length2 + 1];
        for (int i = 0; i < firstHalf.length - 1; i++) {
            firstHalf[i] = A[low + i];
        }
        for (int i = 0; i < secondHalf.length - 1; i++) {
            secondHalf[i] = A[mid + i + 1];
        }
        firstHalf[length1] = (int) Double.POSITIVE_INFINITY;
        secondHalf[length2] = (int) Double.POSITIVE_INFINITY;
        int i = 0, j = 0;
        for (int k = low; k <= hi; k++) {
            if (firstHalf[i] <= secondHalf[j]) {
                A[k] = firstHalf[i];
                i++;
            } else {
                A[k] = secondHalf[j];
                j++;
            }
        }
    }
}
