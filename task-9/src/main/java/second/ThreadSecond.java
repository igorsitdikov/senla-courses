package second;

import utils.Printer;

public class ThreadSecond extends Thread {

    private Locker locker;

    public ThreadSecond(final Locker locker) {
        this.locker = locker;
    }

    @Override
    public void run() {
        try {
            synchronized (locker) {
                for (int i = 0; i < 100; i++) {
                    while (locker.getFlag() != 2) {
                        locker.wait();
                    }
                    Printer.printName(this);
                    Thread.sleep(1000);
                    locker.setFlag(1);
                    locker.notifyAll();
                }
            }
        } catch (final Exception exception) {
            Printer.printException(this, exception);
        }
    }
}
