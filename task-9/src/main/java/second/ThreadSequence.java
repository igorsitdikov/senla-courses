package second;

public class ThreadSequence {
    public static void main(final String[] args) {
        final Locker locker = new Locker();

        final ThreadFirst threadFirst = new ThreadFirst(locker);
        final ThreadSecond threadSecond = new ThreadSecond(locker);

        threadFirst.setName("First thread");
        threadSecond.setName("Second thread");

        threadFirst.start();
        threadSecond.start();
    }

}
