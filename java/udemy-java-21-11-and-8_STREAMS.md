# STREAMS Java 21, 11 and 8.  Advanced (notes). 

* Streams are not data structures!! They allow processing of data as it is read (but not stored).
* A stream pipeline provides an efficient way to transform and process data via intermediary and terminal operations.
* A stream can only be used once.  Once read and processed if you want to process the same data again via streams, the stream must be recreated. 
* Stream 'laziness'.  If all items in a source of data do not need to processed in the stream the JVM will try and avoid that. E.g. if a `.limit(2)` method is chained to the string, no more than 2 data items will be streamed.

## Creating Streams
### ...from an array (`Arrays.stream(...)`)
```java
Double numbers[] = {1.2, 2.2, 3.3};
Stream<Double> s1 = Arrays.stream(numbers);
long n = s1.count();  // this method call kicks off the stream (and terminates it)
System.out.println("number of stream items: " + n);
```
### ...from a collection (`Collection.stream()`)
```java
List<String> aList = Arrays.asList("cat", "dog", "rabbit");
Stream<String> s2 = Arrays.stream();
System.out.println("number of stream items: " + s2.count());   // this method call kicks off the stream (and terminates it)

Turn a dataset - that is not a collection - into a collection then srtam it,
Map(String, Integer> namesToAges = new HashMap<>;
namesToAges.put("Mike", 22); 
namesToAges.put("Mary", 23); 
namesToAges.put("Jake", 12); 
System.out.println("number of stream entries: " + namesToAges
        .entrySet()              // turn hashmap into a set of values 
        .stream()
        .count());   // this method call kicks off the stream (and terminates it)
```
### Stream `of` static generic method (`Stream<T>.of(T... values)`) 
... generically typed, static method, takes a var args input of typed items and returns stream of same type
### ...from a file (`Files.lines()`)
Static `lines` method takes a Path and returns a stream of string
```java
List<Cat> cats = new ArrayList<>;
try(Stream<String> stream = Files.lines(Paths.get(someFilename))) {
  stream.forEach(line -> { // for each kicks off the stream
    String[] catsArray = line.split("some delimiter in file input lines");
    cats.add(new Cat(catsArray[0], catsArray[1]);  // imagine the file structure - one cat per line. Cats can't be changed: it's used inside a Lambda so it's "effectively final"; but! provided you don't change the pointer to the array it can have side-effects, such as adding to the array.
  });
} catch (IOException ioe) { // do something in error handling} 
  Files.lin
forEach() // initiates the stream pipeline
```
### Infinite streams
#### Stream.generate - takes a supplier
```java
  Stream<Integer> infiniteIntStream = Stream.generate(() -> (int) (Math.random() * 10;);
  infiniteIntStream.forEach(System.out::println);
```
#### Stream.iterate - takes 2 parameter - seed to start from and a function 
```java
Stream<Integer> infIntStream = Stream.iterate(2, n -> n + 2).limit(10).forEach(System.out::println);
```
Note! Infinite streams can be turned into finite streams with short-circuiting, stateful, intermediary operations like `limit()`.
### Terminal operations
#### reduce
on immutable objects only. 
`reduce()` typically takes 2 generically typed args: an identifier and a generically typed binary operator. The return type, the identity and the type of BinaryOperator are all of the same type. Note the identity is the value returned if no operations complete - it's not an Optional<>. Accumulation is the operation performed by the reduce() operation.     
If the reduce does not take an identity and only takes on arg - a BinaryOperator<T> - its return type is an Optional<T>. 
Another reduce takes 3 args: an identity, a BiFunction and a BinaryOperator.  This is helpful when the process wants to perform intermediate work on the stream before ultimately combining it.
#### collect
similar  to reduce but for efficiency uses mutable objects.  `collect()` - ignoring static utility classes like Collectors.collect - take function 3 args: a Supplier (the supplier), a BiConsumer (the accumulator) and another BiConsumer (the combiner). 
### Collectors static methods
`toMap()`, `.groupingBy()`, `.partitioningBy`.  
`toMap` takes 2 functions - one to map the key and another to map the value.  Another method takes a 3rd function - a "merge" which specifies what to do in the case of duplicate keys (which would otherwise lead to an 'IllegalStateException'.
`groupingBy` is used with `collect` function to determine what keys are used in the map returned from collect. We can also provide an overloaded downstream collector as a second parameter.  For example if we wanted a map that keyed into a `set` as its value.
`partitioningBy` splits a group into 2 groups, keyed either by true or false.
### Intermediate operations
`filter`, `distinct`, `map` all return a stream.
`limit`, `sorted` are short-circuiting, stateful intermediate operations: i.e. they need to see ALL the data before they can apply their functions.
### Primitive streams
Java provides dedicated streams for primitives: so, `IntStream` is not the same as Stream<Integer>. They can be created in different ways, e.g.s
```java
int[] ia = {9,3,1,2};
IntStream iStream1 = Arrays.stream(ia);
IntStream iStream2 = IntStream.of(3,2,1,5,3,553);
```
Dedicated streams have additional methods that "know" how to perform computations, e.g. `sum()` returns the same type as the number type in the stream; `average()`, `max()`, `min()` all return an Optional.
#### Mapping streams
Mapping from a stream to a primitive stream is another way to create a primitive stream.  `mapToObj`, `mapToLong` 
