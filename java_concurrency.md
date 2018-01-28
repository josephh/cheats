# Java Concurrency
## Notes from Brian Goetz textbook
## The benefits of multiple threads
1. servers - a single threaded server will need to use non-blocking I/O if it is to handle multiple requests (rather than all requests stalling when that thread is blocked). However, non-blocking, asynchronous I/O is complicated! So, being able to create a new thread to handle each new request and use synchronous, blocking code in that individual thread is simpler.
1. frameworks - like RMI and servlets - can create new threads for each request, with synchronisation at necessary points.  This allows for simpler component development.
1. Better, more responsive GUIs - like AWT and Swing.  Single-threaded applications that provide a GUI must either frequently poll for input events, or employ a 'main event loop'.  Where code called from the main event loop takes a long time to complete, the GUI appears to freeze. AWT and Swing avoid this by using an extra thread - an event dispatch thread - instead of an event loop.  Most GUI frameworks are still single-threaded subsystems - and so have an event loop - but that event loop runs in its own dedicated thread, under the control of the GUI toolkit rather than the application.
### Safety - 'nothing bad ever happens'
Threads share the same memory address space - i.e. heap -  and run concurrently so can access or modify shared variables.  This is massively convenient! But also massively risky, since sequential code becomes potentially non-sequential and harder to reason about the state and behaviour of an application.  For a multithreaded program's behaviour to be predictable, access to shared variables must be properly coordinated to avoid threads interfering with each other - with mechanisms like Java synchronisation. But synchronisation obliges a compiler, hardware and runtime to take 'liberties' with timing and ordering of applications - like caching variables in registers or processor-local caches - to ensure program optimisation.  Understanding this can help to ensure program correctness.
### Liveness - 'something good eventually happens'
An inadvertent, infinite loop is an example of a liveness problem that sequential and non-sequential problems both suffer from.  In multi-threaded apps, an example of liveness failure is when one thread waits for another thread to release a resource but it never does. Deadlock, livelock and starvation are all examples of liveness failures.
### performance hazards
multi-threaded, concurrent problems are intended to provide performance gains.  But multithreaded programs have overheads: a 'context switch' occurs each time a scheduler suspends an active thread temporarily to run another; it must save and restore execution context; lose locality; spend CPU time scheduling threads rather than running them...  
## What is a thread-safe class?
 * 'Correctness' : means conforms to its specification
 * 'Thread-safe classes' : means those that continue to behave correctly when accessed from multiple threads - regardless of any scheduling or interleaving of the execution of those threads by the runtime environment and with no additional synchronisation or other coordination needed on the part of the calling code.
Any Java class that runs in a single-threaded application can also run in a multi-threaded one.  If the class is correctly implemented against its spec - in terms of its invariants and postconditions - no sequence of operations that alter its state (reads/ writes of public fields, invocations of public methods) - should be able to violate those invariants and postconditions whether a program happens to be multi or single-threaded.
### Thread safety
1. Stateless classes are always threadsafe.  
1. Atomicity. 'UnsafeCountingFactorizerServlet' includes a private `count` variable (of type long).  The servlet's public `#service(...)` method increments `count` with a unary pre-increment e.g. `++count`.  Problem here is that the code is susceptible to 'lost updates' because `++` is not atomic (it is actually shorthand for _read-modify-write_).  With unlucky timing, thread _A_ can read the value of a shared piece of state just before thread _B_ writes a change to that same state back to memory - and hence _B_'s update to the shared state is lost when _A_ performs its own write.  Single fields can protect state through use of classes in the standard concurrency library, e.g. java.util.concurrent.atomic.AtomicLong#addAndGet(...)
### Race conditions  
Race conditions describe the commonly seen coding scenarios that may fail foul of unlucky timing in multithreaded apps.  Examples are 'check-then-act' (lazy initialisation) and 'read-modify-write' (postincrement).  Some programs may be able to tolerate the unwanted effects and lack of predictability where race conditions arise.  Other programming scenarios - such as guaranteeing uniqueness of a code-generated value - may not be able to cope with that.
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
Reentrancy is important in OO: for a class that extends another and calls its superclass's methods, a deadlock woult arise if the thread was not considered to already hold the lock.  E.g.
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
### Visibility
Memory _visibility_ is important to help ensure threads actually do see changes to shared data.  Proper synchronization is needed for whenever data is shared between threads.
Note, for 64-bit numbers (**double** and **long**) that ARE NOT declared volatile, the JVM is allowed to treat reads and writes as two separate operations - meaning if those occur in 2 different threads at the same time, the high 32 bits and low 32 bits from 2 different numbers may be returned.  
Requiring all threads to synchronize on the same lock guarantees that values written by one thread are visible to a subsequent thread acquiring the same lock.
#### Volatile
_volatile_ keyword is seen as a weaker form of synchronization.  Compiler and runtime are put on notice that a variable is shared and that operations on it should not be reordered with other memory operations.  But accessing volatile variable performs no locking and so cannot cause the executing thread to block.
Volatile variables are only recommended for use when simplifying implementation and verifying synchronization policy.  E.g. when there's a need to ensure visibility of a volatile's own state, or the state of the object they refer to, or indicating an important lifecycle event.
