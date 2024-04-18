import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class CheckSection implements Runnable {
    private int [] arr;
    private int size;
    private int position;
    private int numberOfThreads;
    private ReentrantLock[] locks;
    private boolean done = true;
    private AtomicInteger calc;

    public CheckSection(int[] arr, ReentrantLock[] locks, int position, int numberOfThreads, AtomicInteger calc) {
        this.arr = arr;
        this.size = arr.length;
        this.locks = locks;
        this.position = position;
        this.numberOfThreads = numberOfThreads;
        this.calc = calc;
    }

    private void swap(int pos) {
        int temp = arr[pos];
        arr[pos] = arr[pos+ 1];
        arr[pos + 1] = temp;
    }
    @Override
    public void run() {
        while (done) {
            done = false;
            int i = 0;
            locks[i].lock();
            for ( ; i < size - calc.get() - 1 ; i++) {

                locks[i+1].lock();
                if (arr[i] > arr[i + 1]) {
                    swap(i);
                    done = true;
                }
                locks[i].unlock();

            }
            locks[i].unlock();
            calc.getAndIncrement();
        }
    }
}
