package SortingAlgorithm;

public class main {
    public static void main(String[] args) {
        sortingTester tester = new sortingTester();

        tester.testingWorseCase();
        System.out.println("===================================");

        tester.testingBestCase();
        System.out.println("===================================");

        tester.testingAvgCase();
    }
}
