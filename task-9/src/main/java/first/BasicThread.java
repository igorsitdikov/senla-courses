package first;

public class BasicThread extends Thread {
    @Override
    public void run() {
        final Thread thread = Thread.currentThread();
        try {
            sleep(3000);
            synchronized (thread) {
                thread.wait();
            }
        } catch (final InterruptedException iException) {
            iException.printStackTrace();
        }
    }
}