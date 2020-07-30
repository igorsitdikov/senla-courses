package first;

import utils.Printer;

public class ThreadStates {
    public static void main(final String[] args) {
        final Thread basicThread = new BasicThread();
        Printer.printState(basicThread);
        basicThread.start();
        Printer.printState(basicThread);
        try {
            boolean keepRunning = true;
            int count = 1;
            while (keepRunning) {
                Thread.sleep(2000);
                Printer.printState(basicThread);
                count++;
                if (count == 3) {
                    synchronized (basicThread) {
                        Thread.sleep(1000);
                        basicThread.notify();
                        Printer.printState(basicThread);
                    }
                }
                if (Thread.State.TERMINATED == basicThread.getState()) {
                    keepRunning = false;
                }
            }
        } catch (final InterruptedException e) {
            Printer.printException(basicThread, e);
        }
    }
}