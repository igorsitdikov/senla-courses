package third;

import utils.Printer;

import java.util.Random;
import java.util.Vector;

import static utils.Printer.printBufferFull;

public class Producer implements Runnable {
    private Random random = new Random();
    private final static Integer RANDOM_MAX = 1000;
    private final Vector<Integer> buffer;
    private final int size;

    public Producer(final Vector<Integer> buffer, final int size) {
        this.buffer = buffer;
        this.size = size;
    }

    @Override
    public void run() {
        while (true) {
            try {
                produce();
            } catch (final InterruptedException ex) {
                Printer.printException(Thread.currentThread(), ex);
            }
        }
    }

    private void produce() throws InterruptedException {
        synchronized (buffer) {
            while (buffer.size() == size) {
                printBufferFull(Thread.currentThread(), buffer);
                buffer.wait();
            }
            final int producedItem = random.nextInt(RANDOM_MAX);
            Printer.printProducedItem(producedItem);
            buffer.add(producedItem);
            buffer.notifyAll();
            Thread.sleep(1000);
        }
    }
}
