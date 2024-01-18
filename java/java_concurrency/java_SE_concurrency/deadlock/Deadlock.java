public class Deadlock {


    static class Friend {
        private final String name;
        public Friend(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        
        
        // when a thread calls a synchronized method, it uses that object's intrinsic lock.  Another thread that wants to call the same method on the same object must wait (block) until the lock is released when the thread that currently has the lock returns from the synchronized method.
        public synchronized void bow(Friend bower) {
            System.out.format("Thread: %s -> In object :: %s.  ", Thread.currentThread().getName(), this.hashCode());
            System.out.format("%s: %s has bowed to me!\n", this.name, bower.getName());
            System.out.format("Does %s hold %s (%s)'s lock? %s\n\n", Thread.currentThread().getName(), bower.getName(), bower.hashCode(), Thread.holdsLock(bower) ? "yes" : "no");  
            bower.bowBack(this);
        }
        
        public synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s has bowed back to me!\n\n",
                    this.name, bower.getName());
        }
    }

    public static void main(String[] args) {
        final Friend alphonse =
            new Friend("Alphonse");
        final Friend gaston =
            new Friend("Gaston");
            

        Thread t0 = new Thread(new Runnable() {
            public void run() { 
                alphonse.bow(gaston); 
            }
        });
        t0.start();
    
        Thread t1 = new Thread(new Runnable() {
            public void run() { 
                
                try { 
                    t0.join();
                } catch (InterruptedException e) {
                    // We've been interrupted: no more messages.
                    return;
                }
                
                gaston.bow(alphonse); 
            }
        });
        t1.start();
    
    }
}
