import java.util.concurrent.locks.ReentrantLock;

public class CheckSection implements Runnable {
    private int [] arr;
    private int size;
    private int position;
    private int numberOfThreads;
    private ReentrantLock[] locks;
    private boolean done = true;

    public CheckSection(int[] arr, ReentrantLock[] locks, int numberOfThreads, int position) {
        this.arr = arr;
        this.size = arr.length;
        this.locks = locks;
        this.position = position;
        this.numberOfThreads = numberOfThreads;
    }

    private void swap(int pos) {
        int temp = arr[pos];
        arr[pos] = arr[pos+ 1];
        arr[pos + 1] = temp;
    }
    @Override
    public void run() {
        int times = 0, numberOfLocks = size%numberOfThreads == 0 ? numberOfThreads : numberOfThreads + 1 ;

        while (done) {
            int start, numberOfElements = size/numberOfLocks, end, check = (times - 1)*numberOfThreads;
            done = false;
            locks[position].lock();
            for (int i = position; i < numberOfLocks; i++) {
                if (i + 1 == numberOfLocks) {
                    start = i*numberOfElements;
                    end = size;
                }else {
                    start = i*numberOfElements;
                    end = (i+1)*numberOfElements;
                }
                int j = start;
                for (; j < end - 1; j++) {
                    if (j == size-check)
                        break;
                    if (arr[j] > arr[j + 1]) {
                        swap(j);
                        done = true;
                    }
                }
                if (i+1 == numberOfLocks) {
                    position = 0;
                    locks[i].unlock();
                }else {
                    locks[i+1].lock();
                    if (arr[j] > arr[j + 1]) {
                        swap(j);
                        done = true;
                    }
                    locks[i].unlock();
                }
            }
            if (times == 0) {
                done = true;
            }
            times++;
        }
    }
}
