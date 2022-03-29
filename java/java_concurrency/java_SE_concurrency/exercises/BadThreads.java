package exercises;

/**
 * Q. The application should print out "Mares do eat oats." Is it guaranteed to
 * always do this?   If not, why not?
 * A. While it is likely to consistently print "Mares do eat oats" there is no
 * guarantee that key statement 1 will run before statement 2.  There is no
 * "happens-before" relationship - as described by the Java concurrency tutorials,
 * - which is all about shared object 'visibility' between threads.  So this code
 * should be fixed by making sure the value of message set by the new
 * CorrectorThread instance when it invokes its run method is visible to the main
 * thread.
 * Q.  Would it help to change the parameters of
 * the two invocations of Sleep?
 * A. No - this will only affect the sleep behaviour, not provide a happens-before
 * Q. How would you guarantee that all changes to message will be visible in the
 * main thread?
 * A. 1. Keep a ref to the corrector thread instance and invoke join() on it,
 * before accessing the 'message' field
 *    2. Encapsulate messages in an object with synchronized methods
 *    3. use volatile keyword
 */
public class BadThreads {

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

        (new CorrectorThread()).start();
        message = "Mares do not eat oats.";
        Thread.sleep(1000);
        // Key statement 2:
        System.out.println(message);
    }
}
