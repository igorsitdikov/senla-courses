package second;

public class ThreadSequence {
    public static void main(final String[] args) {
        final Locker locker = new Locker();

        final ThreadFirst threadFirst = new ThreadFirst(locker, false);
        final ThreadFirst threadSecond = new ThreadFirst(locker, true);

        threadFirst.setName("First thread");
        threadSecond.setName("Second thread");

        threadFirst.start();
        threadSecond.start();
    }

}
