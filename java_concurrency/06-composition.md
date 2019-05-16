# Java Concurrency in Practise
## Notes from Brian Goetz textbook
## 06. Composition
## (Chapter 4) Composing Objects
While it is handy to understand low-level aspects of thread safety and synchronization, ideally we want classes that support thread safety and don't require analysis of each and every memory access for safe usage.
### Designing a thread safe class
1. identify variables that form the object's state
1. Identify the invariants that constrain state variables
1. Establish a policy for managing concurrent access to the object's state
'State-space', i.e. the range of values a field can have, helps reason about an object's state.  In the extreme an immutable object can only ever be in a single state so is very straight-forward.  Final fields, used whenever possible, also help.
### Instance confinement (or encapsulation)
when an object is encapsulated within another object, all code paths that have access are known and so can be analyzed mionore easily.  Combining confinement with an appropriate locking discipline can ensure that otherwise non-thread-safe objects are used in a thread-safe manner.
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
