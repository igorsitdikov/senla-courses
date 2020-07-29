package second;

import utils.Printer;

public class BasicThread extends Thread {
    private final Locker locker;
    private Boolean id;

    public BasicThread(final Locker locker, final Boolean id) {
        this.locker = locker;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            synchronized (locker) {
                for (int i = 0; i < 100; i++) {
                    while (locker.getFlag() != id) {
                        locker.wait();
                    }
                    Printer.printName(this);
                    Thread.sleep(1000);
                    locker.setFlag(!id);
                    locker.notifyAll();
                }

            }
        } catch (final Exception exception) {
            Printer.printException(this, exception);
        }
    }
}

