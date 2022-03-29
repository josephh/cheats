# Java Concurrency in Practise
## Notes from Brian Goetz textbook
## 03. Race Conditions and Locking
### Race conditions  
Race conditions describe the commonly seen coding scenarios that may fall foul of unlucky timing in multithreaded apps.  Examples are 'check-then-act' (lazy initialisation) and 'read-modify-write' (postincrement).  Some programs may be able to tolerate the unwanted effects and lack of predictability where race conditions arise.  Other programming scenarios - such as guaranteeing uniqueness of a code-generated value - may not be able to cope with that.
'Compound actions' describes the complimentary, individual operations that must happen together without interruption - effectively be treated as 'atomic'.
### Locking
Java provides 'atomicity' with synchronized blocks (synchronized methods are actually a shorthand for a method entirely wrapped in a synchronized block).  
#### Intrinsic locks
Synchronized block has 2 parts
1. reference to an object to serve as the 'lock', and,
1. a code block
```Java
synchronized (lock) {
  // guarded block of code...
}
```
Every Java object can act as a lock for the purposes of synch'.  'Built-in' locks are known as _intrinsic_ or _monitor_ locks and are acquired automatically by the executing thread prior to entering the code block (and released automatically on exit).  Intrinsic locks - built-in to each object are a convenience that means code does not need to declare a lock for every synchronized block.
#### Reentrancy
When a thread tries to acquire a lock held by another thread, it will have to wait or _block_ until that other thread releases that lock.  However, if a thread tries to enter a code block that is guarded by a lock that _that_ thread already holds, it succeeds.  Reentrancy is achieved through
1. an **acquisition count** for each lock, and
1. an owning thread for each lock.
1. A zero acquisition count means the lock is no longer considered held.
Reentrancy is important in OO: for a class that extends another and calls its superclass's methods, a deadlock would arise if the thread was not considered to already hold the lock.  E.g.
```java
public class Widget {
    public synchronized void doSomething() {
      // ...
    }
}
public class LoggingWidget extends Widget {
    public synchronized void doSomething() {
      System.out.println(toString() + ": calling doSomething");
      super.doSomething(); // the intrinsic lock is shared by both LoggingWidget and its parent class Widget
    }
}  
```
#### Guarding state with locks
Compound actions on shared state (check-then-act, read-modify-write) need to be atomic - by holding a lock for the entire duration of the action.  It's important to note that, if synchronisation is used to coordinate access to shared state/variables, then synchronisation is needed everywhere those variable are accessed and the _same lock_ must be used wherever that variable is accessed.
