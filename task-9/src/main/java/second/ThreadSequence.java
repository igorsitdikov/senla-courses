package second;

public class ThreadSequence {
    public static void main(final String[] args) {
        final Locker locker = new Locker();

        final BasicThread threadFirst = new BasicThread(locker, false);
        final BasicThread threadSecond = new BasicThread(locker, true);

        threadFirst.setName("First thread");
        threadSecond.setName("Second thread");

        threadFirst.start();
        threadSecond.start();
    }

}
