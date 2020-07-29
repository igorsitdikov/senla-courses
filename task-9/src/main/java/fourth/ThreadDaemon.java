package fourth;

public class ThreadDaemon {
    public static void main(final String[] args) {
        final Integer sec = 5;
        final DateTimePrinter printer = new DateTimePrinter(sec);
        printer.setDaemon(true);
        printer.start();

        while (true) {

        }
    }
}
