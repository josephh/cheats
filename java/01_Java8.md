# Java 8
## New features
### Streams
Streams intended to make multi-core programming (i.e. concurrent) easier.  `sychronized` code - i.e. taking out a reentrant lock - is particularly expensive on multicore h/w architecture because each processor has its own fast memory cache but locks require that all cores use a relatively (!) slow, cache-coherency-protocol communication between them to ensure caches are all consistent or to avoid them 'breaking' thread safety.   (synchronization forces code to execute sequentially, which works against the goal of parallelism.)
Streams is the  direct cause of other Java 8 features:
1. concise techniques to pass code to methods (method references, lambdas) and  
1. default methods in interfaces.  

Streams allows for 'behaviour parameterization', akin to using callbacks as function params.  (Note: prior to Java 8 this was already possible with anonymous classes).  This is functional programming in essence.  
As an abstraction streams helps programmers think on a higher 'plane' e.g. "turn a stream of this into a stream of that...".  Also because developers think in terms of declaring desired behaviour, the VM and O/S can determine the most efficient implementation at runtime (making writing concurrent code easier).  Streams also helps exploit the Java 8 concept of passing behaviour as parameters.  

#### Concepts
##### Streams with declarative and functional programming
Using the analogy of Unix, a Unix command that operates on more than one file with more than one character in it, can act on ALL the input (i.e. the stream - considered to be a bit like an iterator) and can act concurrently - i.e. begin the sorting before the concatentation or translation has finished...
```bash
cat file1 file2  |  tr "[A-Z]"  "[a-z]"  |  sort  |  tail -3    
```
Java 8 adds a Streams API (note the uppercase S) in java.util.stream based on this idea; Stream<T> is a sequence of items of type T.  The Streams API has many methods that can be chained to form a complex pipeline just like Unix commands were chained in the previous example.
##### behavior parameterization (pass code to methods as arguments)
##### Parallelism and shared mutable data
“parallelism almost for free”.  this means writing code that doesn’t access shared mutable data to do its job (i.e. pure functions).
##### Method references - `::` syntax meaning, “use this method as a value”
Prior to Java 8, to filter just on hidden files in a directory, you had to write something like the following, where the programmer provides an anonymous class, extending file filter,  overriding the `accept` method:
```Java
File[] hiddenFiles = new File(".").listFiles(new FileFilter() {
  boolean accept(File f) {
    return f.isHidden();
  }
});
```
with Java 8 you can simply write,
```Java
File[] hiddenFiles = new File(".").listFiles(File::isHidden);
```
In the example above `File::isHidden` is the _method reference_
#### Default methods
Default methods were added to Java 8 largely to support library designers by enabling them to write more evolvable interfaces.  If a new version of an interface gets a new method, that addition is a breaking change, because existing implementations won't compile - for the new version of Java - until  that newly added method is implemented.  
The Java 8 solution is that an interface can now contain method signatures for which an implementing class doesn’t need to provide an implementation.  (The missing method bodies are given as part of the interface - hence 'default implementations' - rather than in the implementing class).   
For example, in Java 8 you can now call the sort method directly on a List (which used to be only available on java.util.Collections, (prior to Java 8, to sort a list one had to call the static, `Collections.sort(list)` (or `Collections.sort(list, comparator)`). java.lang.List now has its own default `default void sort` method (which internally calls the static method Collections.sort).  Note, multiple default implementations in multiple implementing interfaces, means you have a form of multiple inheritance in Java...
#### Optional<T>
Borrowing from common functional languages (SML, OCaml, Haskell) Java 8 helps avoid null pointer exceptions by explicit use of more descriptive data types. `Optional<T>` is a container object that may or not contain a value and includes methods to explicitly deal with the case where a value is absent, and as a result you can avoid NullPointer exceptions. (In other words, it uses the type system to allow you to indicate when a variable is anticipated to potentially have a missing value).
#### Pattern matching
...as an extended form of switch statements...
