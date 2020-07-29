package third;

import utils.Printer;

import java.util.Vector;

public class Consumer implements Runnable {
    private final Vector<Integer> buffer;
    private final int size;

    public Consumer(final Vector<Integer> buffer, final int size) {
        this.buffer = buffer;
        this.size = size;
    }

    @Override
    public void run() {
        while (true) {
            try {
                consume();
            } catch (final InterruptedException ex) {
                Printer.printException(Thread.currentThread(), ex);
            }
        }
    }

    private void consume() throws InterruptedException {
        synchronized (buffer) {
            while (buffer.isEmpty()) {
                Printer.printBufferEmpty(Thread.currentThread());
                buffer.wait();
            }
            final Integer consumedItem = buffer.remove(0);
            Printer.printConsumedItem(consumedItem);
            buffer.notifyAll();
            Thread.sleep(1000);
        }
    }
}
