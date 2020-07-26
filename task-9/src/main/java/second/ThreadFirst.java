package second;

import utils.Printer;

public class ThreadFirst extends Thread {
    private Locker locker;

    public ThreadFirst(final Locker locker) {
        this.locker = locker;
    }

    @Override
    public void run() {
        try {
            synchronized (locker) {
                for (int i = 0; i < 100; i++) {
                    while (locker.getFlag() != 1) {
                        locker.wait();
                    }
                    Printer.printName(this);
                    Thread.sleep(1000);
                    locker.setFlag(2);
                    locker.notifyAll();
                }

            }
        } catch (final Exception exception) {
            Printer.printException(this, exception);
        }
    }
}

