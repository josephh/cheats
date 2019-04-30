package guarded;

/**
 * Guarded blocks is a common idiom used where threads need to coordinate their
 * actions (see http://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html).
 * In a guarded block, one thread begins by polling a condition that must be
 * true before it can proceed.
 * The 'guard', rather than simply looping continuously until a test returns
 * true, invokes <code>Object.wait()</code> to suspend the current thread.
 * <code>Object.wait()</code> does not return until another thread has issued
 * a notification that some event may has occurred - though that <strong>may not
 * be</strong> the event this thread is waiting for!
 * Synchronization is used to ensure the thread has a lock - explicit or the
 * intrinsic lock. Furthermore if a thread invokes wait() without holding a lock
 * an error is thrown.
 * E.g.
  <pre>
    public synchronized void guardedJoy() {
      // This guard only loops once for each special event, which may not
      // be the event we're waiting for.
      while(!joy) {
          try {
              wait();
          } catch (InterruptedException e) {}
      }
      System.out.println("Joy and efficiency have been achieved!");
    }
  </pre>
 */
public class Drop {
     private String message; // message: producer to consumer

     // true means consumer should wait for producer's message
     // false means producer should wait for consumer to retrieve said message
     private boolean empty = true;

     public synchronized String take() {  // CONSUMER
         while (empty) {  // wait until message is available
             try {
                 wait();
             } catch (InterruptedException e) {
                 // we don't care about interrupts (we only care about the value of 'empty')
             }
         }
         empty = true;
         notifyAll(); // tell producer a message is expected
         return message;
     }

     public synchronized void put(String message) {  // PRODUCER
          while (!empty) { // wait until message has been received
             try {
                 wait();
             } catch (InterruptedException e) {
                 // we don't care about interrupts (we only care about the value of 'empty')
             }
         }
         empty = false;
         this.message = message; // store message
         notifyAll(); // tell customer that message is available
     }
}
