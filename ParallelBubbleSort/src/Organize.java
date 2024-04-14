import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class Organize {

    private static int thrNumber, arrSize;
    private static ReentrantLock[] locks;

    private static int readInteger(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                // Consume the invalid input
                scanner.next();
                System.out.println("Invalid input. Please enter an integer.");
                System.out.print("Try again: ");
            }
        }
    }

    private static void validation() {
        if(thrNumber <1 || arrSize <1) {
            throw new InvalidEnteredParametersException("number of threads and length of array must be positive numbers ");
        }
        if(thrNumber > arrSize/2) {
            thrNumber=arrSize/2;
        }

    }

    private static void readFromConsole() {
        System.out.println("Enter number of threads and array size." + System.lineSeparator());
        Scanner scanner = new Scanner(System.in);
        thrNumber = readInteger(scanner);
        arrSize = readInteger(scanner);
        validation();
        System.out.println(thrNumber + " " + arrSize + System.lineSeparator());
    }


    public static void createLocks() {
        readFromConsole();
        locks = new ReentrantLock[arrSize];
        for (int i = 0; i < arrSize; i++) {
            locks[i] = new ReentrantLock(true);
        }

        Thread[] threads = new Thread[thrNumber];
        int arr[] = GenerateArray.generateRandomIntArray(arrSize);

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.print(System.lineSeparator());

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < thrNumber; i++) {
            threads[i] = new Thread(new CheckSection(arr, locks, i, thrNumber));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        long sortingTime = endTime - startTime;
        System.out.println("Sorting time: " + sortingTime + " milliseconds.");

        boolean p=true;
        int i=0;
        for (; i < arr.length-1; i++) {
            if (arr[i]>arr[i+1])
                p=false;
            System.out.print(arr[i] + " ");
        }
        System.out.print(arr[i] + " "+ System.lineSeparator() + p);
    }

}
