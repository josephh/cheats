# Java Fundamentals
1. Java has primitives and static classes - so not truly an OO language
 1. boolean, char, int, short, long, double, float, short,  
1. Strings in Java - why are they immutable?
 1. memory efficiency.  Java language developers decided Strings were likely to appear in the language A LOT, therefore decided a string pool would be an efficient way to manage memory consumption for immutable strings.
 1. Collections - Strings are the keys for hashmaps, so can't be changed as hashmaps would break!
 1. Thread safety.  Strings can be shared more readily across threads if they can never be changed
1. StringBuffer is threadsafe, StringBuilder is not
1. abstract classes vs interfaces?
 1. interface methods are public by default
 1. interface methods are all abstract, abstract classes can mix abstract and concrete methods
 1. interfaces can have static and final properties only (cos they are not instantiable classes!); abstract classes can have non-final and non-static properties
 1. abstract classes single class inheritance ("extends" - that's the Java way!) vs interface "implements"
1. How is data encapsulation or "information hiding" implemented in Java? through private visibility scope and static methods
1. what is the JVM? JVM is a platform-dependent, abstract machine comprising of 3 specifications - document describing the JVM implementation requirements, computer program meeting the JVM requirements and instance object for executing the Java byte code and provide the runtime environment for execution.
1. HashMap is not synchronized and can support ordering, Hashtable is synchronized but order is not guaranteed
1. 'transient' is the keyword to prevent serialization of a property
1. absence of static keyword in a program main method (i.e. `static void main(String[] args){...}` ) = compile time ok but runtime error "NoSuchMethodError"
1. Java is a "pass by value" language; however when an object is passed as a param to a method, its address is copy and passed. So, if an object gets reassigned to a different set of values (residing in a different place in memory), the previously copied and passed memory address won't know about that new memory location, so will still point to the old one (and to the old value).  
1. HashSet vs TreeSet? One is sorted the other (TreeSet, using red-black tree) isn't.  HashSet performance is constant, so faster.  hashcode() and equals() for HashSet insertion and retrieval.  compare() and compareTo() for HashSet.
1. Moreover, exhaustion of the heap memory takes place if objects are created in such a manner that they remain in the scope and consume memory. The developer should make sure to dereference the object after its work is accomplished. Although the garbage collector endeavors its level best to reclaim memory as much as possible, memory limits can still be exceeded.  Keep object scope to a minimum!
1. "varargs" came with Java 5 `public void fooBarMethod(String... variables){ ...`
1. Java uses UTF-16 for String representation. With this Unicode, we need to understand the below two Unicode related terms:
Code Point: This represents an integer denoting a character in the code space.
Code Unit: This is a bit sequence used for encoding the code points. In order to do this, one or more units might be required for representing a code point. ...if a string contained supplementary characters, the length function would count that as 2 units and the result of the length() function would not be as per what is expected.
1. Anonymous class.  Anonymous classes are good for concise code.  Instantiate and initialise a class in one go (like a constructor, immediately followed by a code block).  A bit like a closure (function inside a function), anonymous classes have access to members in the enclosing class and any local variables, PROVIDED they are marked as 'final'.˜  
1. Lambda.  Lambdas are good for,  (1) encapsulating a single unit of behavior that you want to pass to other code (2) if you need a simple instance of a functional interface and none of the preceding criteria apply (for example, you do not need a constructor, a named type, fields, or additional methods).
1. Nested class: Use it if your requirements are similar to those of a local class, you want to make the type more widely available, and you don't require access to local variables or method parameters.
Use a non-static nested class (or inner class) if you require access to an enclosing instance's non-public fields and methods. Use a static nested class if you don't require this access.
