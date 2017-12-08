package exercises;

/**
 * Q. How would you guarantee that all changes to message will be visible in the
 * main thread?
 * A. 1. Keep a ref to the corrector thread instance and invoke join() on it,
 * before accessing the 'message' field
 *    2. ...
 *    3. ...
 */
public class BadThreadsJoin {

    static String message;

    private static class CorrectorThread
        extends Thread {

        public void run() {
            try {
                sleep(1000);
            } catch (InterruptedException e) {}
            // Key statement 1:
            message = "Mares do eat oats.";
        }
    }

    public static void main(String args[])
        throws InterruptedException {

        Thread corrector = new CorrectorThread();
        corrector.start();
        message = "Mares do not eat oats.";
        Thread.sleep(100);
        // Key statement 2:
        corrector.join();
        System.out.println(message);
    }
}
