package fourth;

import utils.Printer;

public class ThreadDaemon {
    public static void main(final String[] args) {
        final Integer sec = 5;
        final DateTimePrinter printer = new DateTimePrinter(sec);
        printer.setDaemon(true);
        printer.start();
        try {
            Thread.sleep(sec * 100);
        } catch (InterruptedException e) {
            Printer.printException(printer, e);
        }
    }
}
