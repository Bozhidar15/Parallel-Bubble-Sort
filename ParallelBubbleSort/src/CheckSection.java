import java.util.concurrent.locks.ReentrantLock;

public class CheckSection implements Runnable {
    private int [] arr;
    private int size;
    private int position;
    private int numberOfThreads;
    private ReentrantLock[] locks;
    private boolean done = true;

    public CheckSection(int[] arr, ReentrantLock[] locks, int position,  int numberOfThreads) {
        this.arr = arr;
        this.size = arr.length;
        this.locks = locks;
        this.position = position;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void run() {
        int times = 0;
        while (done) {
            done = false;
            int i = 0;
            locks[i].lock();
            for ( ; i < size - 1 - position - times*numberOfThreads; i++) {
                {

                    locks[i+1].lock();
                    try {
                        if (array[i] > array[i + 1]) {
                            int temp = array[i];
                            array[i] = array[i + 1];
                            array[i + 1] = temp;
                            swapped = true;
                        }
                    } finally {
                        locks[i].unlock();
                    }
                }
            }
            locks[i+1].unlock();
            times++;
        }
    }
}
