package second;

import utils.Printer;

public class BasicThread extends Thread {
    private final Locker locker;
    private Boolean toggle;

    public BasicThread(final Locker locker, final Boolean toggle) {
        this.locker = locker;
        this.toggle = toggle;
    }

    @Override
    public void run() {
        try {
            synchronized (locker) {
                for (int i = 0; i < 100; i++) {
                    while (locker.getFlag() != toggle) {
                        locker.wait();
                    }
                    Printer.printName(this);
                    Thread.sleep(1000);
                    locker.setFlag(!toggle);
                    locker.notifyAll();
                }

            }
        } catch (final Exception exception) {
            Printer.printException(this, exception);
        }
    }
}

