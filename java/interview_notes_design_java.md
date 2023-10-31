S:O:L:I:D

Single responsibility - harder to break, easier to reason about.  
Open /closed (Meyer)  
Liskov - classes of the same type be substituted in place of each other  
Interface segregation - avoid breaking changes.  
Dependency Injection (IoC).  Module high level code depends on abstraction (i.e. interface) NOT directly on low-level code.  low-level code also depends on the abstraction, rather than the parent code.  `SLF4J = D.I + Delegation, + loader`

## D.I. (Spring)
Dependency Injection with spring; inversion of control.  This supports business logic being written and tested in isolation. The framework will take care of instantiating objects.  supports polymorphism.  OO code helps LESS CODE TO BE WRITTEN.  LESS CODE MEANS LESS COST, LESS TESTING LESS MAINTENANCE

## Async/ non-block
Async, non-blocking - do more work while waiting; maximise hardward efficiency.  multithreaded concurrent code is hard to write: deadlocks, race conditions, safe publishing.  how to write threadsafe code?


Java questions!
===============
1. why are strings immutable?
a. the language designers asserted that Strings would get heavy use.  So, as a language optimisation feature they implemented a "string pool", to minimise memory consumption.
b. Mutable objects can't be safely shared, so for the benefit of multithreading, strings can be safely accessed from multiple threads when immutable.
c. HASH table keys are Strings! So hash tables would break if it was possible to reassign string variables in-flight.
!!!!Note strings stay on the heap until garbage collected (arbitrary amount of time...).  in the case of a memory dump strings may become visible to malicious users.  Use of a mutable character array is therefore advisable - where the entries can be set to blank or null, following use.
===============˜
2. Interface vs Abstract class?
a. interface = supports multiple "implements".
b. interface = method declarations only -  no implementation.
c. interfaces are not classes - so they can't have instance variables and non-final variables.  But interfaces CAN have static and final variables.
d. abstract classes - can have visibility modifiers; interfaces methods by default are "public"
===============
3. Method hiding/ data privacy/ encapsulation.
a. static methods belong to a class - they can't form part of inheritance
b. private methods are not visible outside of a class
===============
4. TreeSet vs HashSet
a. treeset is sorted by default, hashset isn't.
b. treeset performance is slower O(log n) (because of sort) hashset performance is O(1)
c. hashset compares entries via hashCode() and equals().  Treeset uses compare() and compareTo()
d. heterogeneous objects and null can go in Hashset but lead to runtime exception in treeset.
===============
5.  JVM consists of 3 things (JVM is a platform-dependent, abstract machine comprising of 3 specifications)
a. document describing the JVM implementation requirements,
b. computer program meeting the JVM requirements and
c. instance object for executing the Java byte code and providing the runtime environment for execution.
===============
