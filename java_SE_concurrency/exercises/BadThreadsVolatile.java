package exercises;

/**
 * Q. How to guarantee that all changes to 'message' will be visible in the
 * main thread?
 * A. 1. ...
 *    2. ...
 *    3. use volatile keyword
 */
public class BadThreadsVolatile  {

    static volatile String message;

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

        (new CorrectorThread()).start();
        message = "Mares do not eat oats.";
        Thread.sleep(1000);
        // Key statement 2:
        for(int i = 0; i < 20; i++) {
          System.out.println(message);
        }
    }
}
