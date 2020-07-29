package third;

import java.util.Vector;

public class ProducerConsumer {
    private static Vector buffer = new Vector();
    private final static int SIZE = 4;

    public static void main(final String[] args) {
        final Producer producer = new Producer(buffer, SIZE);
        final Thread prodThread = new Thread(producer, "Producer");
        final Consumer consumer = new Consumer(buffer, SIZE);
        final Thread consThread = new Thread(consumer, "Consumer");
        prodThread.start();
        consThread.start();
    }
}

