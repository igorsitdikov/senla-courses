package fourth;

import utils.Printer;

public class DateTimePrinter extends Thread {
    private final Integer sec;

    public DateTimePrinter(final Integer sec) {
        this.sec = sec;
    }

    @Override
    public void run() {
        while (true) {
            Printer.printDateTime();
            try {
                Thread.sleep(sec * 1000);
            } catch (final InterruptedException e) {
                Printer.printException(Thread.currentThread(), e);
            }
        }
    }
}
