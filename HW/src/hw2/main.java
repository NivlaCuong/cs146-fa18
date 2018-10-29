package hw2;

public class main {
    public static void main(String[] args) {
        sortingTester tester = new sortingTester();
        tester.testingCase("Worst", tester.getReversedList());

        System.out.println("===================================");
        tester.testingCase("Best", tester.getBestList());

        System.out.println("===================================");
        tester.testingCase("Average", tester.getAvgList());
        System.out.println();
    }
}
