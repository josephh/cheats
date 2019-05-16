# Java Concurrency in Practise
## Notes from Brian Goetz textbook
## 02. Thread Safety
## What is a thread-safe class?
 * 'Correctness' : means conforms to its specification
 * 'Thread-safe classes' : means those that continue to behave correctly when accessed from multiple threads - regardless of any scheduling or interleaving of the execution of those threads by the runtime environment and with no additional synchronisation or other coordination needed on the part of the calling code.
Any Java class that runs in a single-threaded application can also run in a multi-threaded one.  If the class is correctly implemented against its spec - in terms of its invariants and postconditions - no sequence of operations that alter its state (reads/ writes of public fields, invocations of public methods) - should be able to violate those invariants and postconditions whether a program happens to be multi or single-threaded.
### Thread safety
1. Stateless classes are always threadsafe.  
1. Atomicity. 'UnsafeCountingFactorizerServlet' includes a private `count` variable (of type long).  The servlet's public `#service(...)` method increments `count` with a unary pre-increment e.g. `++count`.  Problem here is that the code is susceptible to 'lost updates' because `++` is not atomic (it is actually shorthand for _read-modify-write_).  With unlucky timing, thread _A_ can read the value of a shared piece of state just before thread _B_ writes a change to that same state back to memory - and hence _B_'s update to the shared state is lost when _A_ performs its own write.  Single fields can protect state through use of classes in the standard concurrency library, e.g. java.util.concurrent.atomic.AtomicLong#addAndGet(...)
