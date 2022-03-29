# Java Concurrency in Practise
## Notes from Brian Goetz textbook
## 05. Publication and Escape
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
