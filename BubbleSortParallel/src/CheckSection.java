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

    private void swap(int pos) {
        int temp = arr[pos];
        arr[pos] = arr[pos+ 1];
        arr[pos + 1] = temp;
    }
    @Override
    public void run() {
        int times = 0;
        while (done) {
            done = false;
            int calc = times;//*numberOfThreads;
            int calc2 = times*numberOfThreads;
            if((size - calc2)/2 <= position)
                break;
            int i = 0;
            locks[i].lock();
            for ( ; i < size - calc - 1 ; i++) {

                locks[i+1].lock();
                if (arr[i] > arr[i + 1]) {
                    /*int q =i+1;
                    System.out.println("thread " + position + " index " + i + " with " + q +" elements "
                            + arr[i] + " "+ arr[i+1]);*/
                    swap(i);
                    done = true;

                    /*for (int z = 0; z < arr.length; z++) {
                        System.out.print(arr[z] + " ");
                    }
                    System.out.print(System.lineSeparator());*/
                }
                locks[i].unlock();

            }
            locks[i].unlock();
            times++;
        }
    }
}
