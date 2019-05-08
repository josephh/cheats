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
#### Publication and escape
In programming scenarios, we may wish to hide all of an object and its internals outside of the scope where it is used (and thus avoid compromising encapsulation).  Alternatively we may want to publish an object for general use, in a thread-safe manner requiring synchronization.
#### Forms of publication and 'escape'
1. Reference to a public static field
1. Indirectly publishing one object via another, e.g. by adding an object reference to a set that is publicly iterable, or via the public fields of an object
1. Returning a reference from a non-private methods
1. Passing an object to an 'alien' method - point being calling code can't know what that alien code will do, whether it may retain a reference to the object that might be used later from another thread.  Risk of misuse can't be ignored!
1. Publish an inner class instance - inner class instances contain a hidden reference to the enclosing instance, i.e. the _this_ reference (an important special case of escape). Objects are only in a consistent, predictable state once constructors return.
#### safe construction
If(!) it is tempting to either start a thread from a constructor method (not advisable) or registering an event listener inside a constructor method (not advisable either) then a 'safe' approach is to make the constructor private and to provide a public, static factory method to create instances. In the case of registering an event listener, the event listener can be created in the constructor but should no be registered until the static factory method executes.
#### Thread confinement
Avoid the need for synchronization by not sharing data between threads.  Prevent a potentially shared object from being shared via thread confinement: an element of a program's design - not enforeceable by the JVM or language constructs.
#### Stack confinement
Local variables are intrinsically linked to an executing thread and for local primitives it is physically impossible to share these between threads.
#### ThreadLocal
ThreadLocal allows for formal thread confinement: associate a per-thread value with a value-holding object.  _ThreadLocal_ has **get** and **set** accessors that keep a separate copy of the value for each thread that uses it.  So `get` sees the most recent value passed to `set` from the currently executing thread.  ThreadLocals are very useful in designs implementing Singletons or global variables, e.g. for a global database connection, initialised at startup to avoid having to pass a `Connection` object to every method.  Another use-case is where a frequently used object requires an object to serve as temporary storage - such as a buffer - and wants to avoid reallocating the temporary object on each invocation.  (Thread local values are stored in the thread itself!)
#### Immutability
**Immutable objects are always thread-safe!**
For an object to absolutely be immutable, it must meet the following requirements:
1. its state __cannot__ be modified after construction
1. all its fields are **final**
1. it is __properly__ constructed (the `this` reference has not escaped during construction)
Note, final fields can't be modified.  They have special semantics under the Java Memory Model - guaranteeing initialisation safety - and which supports immutable objects being freely shared without need for synchronisation.  
Note, classes may be thread-safe - despite an absence of synchronisation/locking if immutability is used along with volatile variables.  For example, avoid race conditions when using multiple related variables by using an immutable object to hold all the variables; any thread that gets a reference to it need never worry about any other thread modifying its state.  If a __*volatile*__ field references an instance of an immutable object and that volatile reference is assigned to a new instance, that new data is immediately signalled to visible to other threads.
#### safe publication
ways to safely publish an object
1. initialize an object ref from a static initializer
1. store a ref to object into a volatile field or AtomicReference
1. Store a ref to object into a __final__ field of properly constructed object
1. Store a ref to object into a __final__ field properly guarded by a lock
## Chapter 4 - Composing Objects
While it is handy to understand low-level aspects of thread safety and synchronization, ideally we want classes that support thread safety and don't require analysis of each and every memory access for safe usage.
### Designing a thread safe class
1. identify variables that form the object's state
1. Identify the invariants that constrain state variables
1. Establish a policy for managing concurrent access to the object's state
'State-space', i.e. the range of values a field can have, helps reason about an object's state.  In the extreme an immutable object can only ever be in a single state so is very straight-forward.  Final fields, used whenever possible, also help.
### Instance confinement (or encapsulation)
when an object is encapsulated within another object, all code paths that have access are known and so can be analyzed more easily.  Combining confinement with an appropriate locking discipline can ensure that otherwise non-thread-safe objects are used in a thread-safe manner.
#### Java monitor pattern
...where an object encapsulates all its mutable state and guards it with the object's own intrinsic lock.
This has the advantage of being a very simple pattern.  But it is just a convention: any lock could be used provided it is used consistently and actually using a private field as a lock means client code cannot improperly acquire a lock it is not meant to use.
### Adding functionality to existing thread-safe classes
Many existing classes provide much of the thread-safe functionality we need.  But when there is something missing what to do?  E.g. A put-if-absent operation on a list.  List already has 'add' and 'contains' methods,  but a _check-then-act_ operation needs to be an atomic one.
* Safest thing to do is to modify the existing class, but that may not be possible if  you do not have access to the source code, or be free to modify the class.  IN modifying a class, the existing synchronization strategy so it can be enhanced in a consistent manner.  Adding a new method directly to the class means all code implementing the synchronization policy is still in one place, making comprehension and analysis more straightforward.
* Extend a class - assuming it was designed to support extension.  This is a more fragile approach: since implementation is now spread over multiple classes.  If an underlying classes changed its synchronisation policy, for example by locking on a different object to guard state, then the subclass would silently break.
#### Client-side Locking
...is tricky, entailing client-side code that - for example, through use of a wrapper - forces all use of a class through the client-controlled wrapper; but this approach dependsly on the client's wrapper code synchronizing on the same object as the wrapped class uses.  As a result client-side locking is even more fragile than extending an existing class, since locking code for class 'C' is going into classes (e.g. wrapper classes) that are totally unrelated to class C.  
#### Composition
This is a less fragile approach and involves an additional level owned and controlled by the controlling class.  The composing/ controlling class ignores the synchronisation policy of the composed object and provides its own, then delegating other, 'normal' operations to the composed object.

## "Building blocks" (chapter 5)
Java 5 and 6 provide some useful concurrent building blocks
### Synchronized collections
`Collections.synchronizedXxxx` classes wrap data structures such as java.util.Vector and java.util.Hashtable.  These classes achieve thread safety by encapsulating their state and synchronizing every public method so that only one thread at a time can access the collection state.  To do common compound actions, e.g.s fetch all elements until a collection is exhausted; find the next element after this one according to some sorting order; put-if-absent conditional operation; may require additional client-side locking to guard.  The thread safe collection classes are still thread safe but may not behave as expected if used by multiple threads concurrently.  For example, it's possible for one thread to remove an item from a collection in between
another thread checking collection size and acting on that (get-collection-size; get-last-element-by-index).  Using client-side locking can help with unreliable iteration - e.g. by holding a Vector's lock for the duration of the iteration but this will be at the cost of scalability, impairing concurrency.
### Iterators and ConcurrentModificationException
Collections are typically iterated with an `Iterator`, explicitly or via Java 5 onward's for-each loop syntax.  These iterators are fail-fast and not designed to deal with concurrent modification (if collection changes in between iteration beginning, they then throw `ConcurrentModificationException`. (they work by associating a modification count with the collection)).  Holding a lock on the collection for the duration of the iteration will help avoid ConcurrentModificationException but this is undesirable for a number of reasons: other threads waiting to access the collection will block; and they could wait for a long time if the collection is large.  Cloning a collection before operating further eliminates the chance of seeing ConcurrentModificationException - since the clone can be thread-confined - (although the original collection must still be locked while cloning the copy) but this has a performance cost that should be weighed against the benefit to concurrency.
### Hidden iterators
Some iteration occurs less obviously then might be expected.  For example when including an instance of  java.util.Set in a string concatenation (e.g. `log.debug("Value of set = " + setInstance)`) then string concatenation gets turned in to a call to `StringBuilder.append(Object)`, which in turn calls toString on element collection element.  Similar things happen for calls to hashCode() and equals() methods.  This sort of thing often appears in trace and debugging code which may not be subject to the same level of rigour as application code.  
## Concurrent collections
Java 5.0's concurrent collections as designed for concurrent access from multiple threads.  `ConcurrentHashMap` replaces synchronized hash-based map implementations; `CopyOnWriteArrayList` is a replacement for synchronized List implementations where traversal is the dominant operation; `ConcurrentMap` is an interface supporting compound actions like put-if-absent, replace, conditional remove.  Java 5.0 also added 2 new collection types, `Queue` and  `Blocking Queue`.  Queue operations do not typically block - if a queue is empty, the retrieval operation returns null.  Why were Queue classes added?  because eliminating the random access requirements of List admits more efficient concurrent implementations.     
## Task Execution (Chapter 6)
The notion of tasks simplifies program execution, facilitates error recovery and provides natural transaction boundaries.  For concurrency, tasks provide a natural structure for parallelizing work.  This is particularly helpful for server design.  Web servers, mail servers, ftp servers, EJB containers and database servers all accept requests via network connections from remote clients.
### Characteristics of servers
Servers should exhibit good throughput and good responsiveness under normal load, when overloaded they should degrade gracefully.  Good tasks boundaries with a sensible task execution policy are important for server design and implementation.  Using individual requests as a base 'unit' for tasks or task boundaries allow for independence of task handling and appropriate task sizing.
#### Single threaded web server
[...can only handle one request at a time](https://github.com/josephh/jcip_executor/blob/master/src/main/java/SingleThreadWebServer.java) :confused: (this might suffice if the server only served one client and could complete processing and return immediately; but few real world servers can work in this way)
#### Thread per request server
[...risks too many threads causing performance degradation and eventual out of memory](https://github.com/josephh/jcip_executor/blob/master/src/main/java/ExplicitThreadPerTask.java) :neutral_face: (main problem here is nothing puts a limit on the number of tasks created, other than the rate at which clients can throw requests at the server)
