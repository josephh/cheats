package exercises;

/**
 * Q. How would you guarantee that all changes to message will be visible in the
 * main thread?
 * A. 1. ...
 *    2. Encapsulate message in an object with synchronized methods
 *    3. ...
 */
public class BadThreadsSynchronized {

    static SynchronizedMessage message = new SynchronizedMessage("init string");

    private static class CorrectorThread
        extends Thread {

        public void run() {
            try {
                sleep(3000);
            } catch (InterruptedException e) {}
            // Key statement 1:
            message.setMessage("Mares do eat oats.");
        }
    }

    public static void main(String args[])
        throws InterruptedException {

        (new CorrectorThread()).start();
        message.setMessage("Mares do not eat oats.");
        Thread.sleep(3000);
        // Key statement 2:
        System.out.println(message.getMessage());
    }

    private static class SynchronizedMessage {

      private SynchronizedMessage(String m) {
        this.message = m;
      }

      private String message;

      private synchronized void setMessage(String msg) {
        this.message = msg;
      }

      private synchronized String getMessage() {
        return this.message;
      }

    }
}
