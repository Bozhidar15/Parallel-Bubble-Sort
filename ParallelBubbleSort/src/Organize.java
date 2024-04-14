import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Organize {

    private static int thrNumber, arrSize;
    private static ReentrantLock[] locks;

    private static void readFromConsole() {
        System.out.println("Enter number of threads and array size.\n");
        Scanner scanner = new Scanner(System.in);
        thrNumber = scanner.nextInt();
        arrSize = scanner.nextInt();
        System.out.println(thrNumber + " " + arrSize);
    }
    public static void createLocks() {
        readFromConsole();
        locks = new ReentrantLock[arrSize];
        for (int i = 0; i < arrSize; i++) {
            locks[i] = new ReentrantLock(true);
        }
        Thread[] threads = new Thread[thrNumber];
        boolean done = true;
        for (int i = 0; i < thrNumber; i++) {
            threads[i] = new Thread(new CheckSection(GenerateArray.generateRandomIntArray(arrSize), locks, i, thrNumber));
            threads[i].start();
        }
    }

}
