# 04 - Java 8 - Streams
:warning:  the elements of a stream are only produced on demand; this saves on memory and also allows for 'infinite' streams :smile:

Collections in Java 8 support a new stream method that returns a stream (see interface definition `java.util.stream.Stream`).
Similarly to iterators, a stream can be traversed only once.
## Collections vs Streams?
> ...collections are data structures, they’re mostly about storing and accessing elements with specific time/space complexities.  
A collection is an in-memory data structure that holds **every** element currently in the data structure

versus
> ...streams are about expressing computations such as filter, map, reduce, find, match, sort ...etc  
A stream is a conceptually fixed data structure (you can’t add or remove elements from it) whose elements are computed on demand.  The idea is that a user will extract only the values they require from a stream and these elements are produced — invisibly to the user — only as and when required.  Another view is that a stream is like a lazily constructed collection: values are computed when they’re solicited by a consumer...

* Streams consume from a data-providing source such as collections, arrays, or I/O resources. __Note that generating a stream from an ordered collection preserves the ordering.__

## Characteristics
### Pipelining
Many stream operations return a stream themselves, allowing operations to be chained to form a larger pipeline. This enables certain optimizations that we explain in the next chapter, such as laziness and short-circuiting. A pipeline of operations can be viewed as a database-like query on the data source.

Stream operations that can be connected are called intermediate operations (they return a stream) and operations that close a stream are called terminal operations (they return a result).  Intermediate operations (`filter`, `map`, `sort`) don’t perform any processing until a terminal operation is invoked on the stream pipeline (they’re lazy). This is because intermediate operations can usually be merged and processed into a single pass by the terminal operation.
#### short circuiting
```Java
stream().filter(...).limit(3).collect(toList())) // shortcircuit after 3 items collected
```
#### loop fusion
where stream operations are pipelined together e.g. stream().filter(...).map(...).collect(toList()) the filter and map may be merged into one pass: compiler experts call this "loop fusion".
### Internal iteration
In contrast to collections, which are iterated explicitly using an iterator, stream operations do the iteration behind the scenes for you.

## Operations on Streams - filtering
### filter
...takes as argument a predicate (a function returning a boolean) and returns a stream including all elements that match the predicate.
### distinct
...removes duplicates
```Java
List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
numbers.stream()
       .filter(i -> i % 2 == 0)
       .distinct()
       .forEach(System.out::println);
```
### takeWhile [Java 9]
To get at elements of a stream that satisfy a particular predicate, `filter()` will involve looking through all values of a stream.  But where a stream is already sorted and you only want a portion of that stream, (for example, elements with a value below a certain threshold) `takeWhile` is useful...particularly where the stream is very large.  `takeWhile` stops when it finds a value that does not match.      
### dropWhile [Java 9]
The `dropWhile` operation is the complement of `takeWhile`. It throws away the elements at the start where the predicate is false. Once the predicate evaluates to true it stops and returns all the remaining elements, and it even works if there are an infinite number of remaining elements!
### limit
Streams support the limit(n) method, which returns another stream that’s no longer than a given size.
```Java
List<Dish> dishes = specialMenu.stream()
                        .filter(dish -> dish.getCalories() > 300)
                        .limit(3)
                        .collect(toList());
```
### skip
Discards the first (n) elements. If the stream has fewer than n elements, an empty stream is returned.

## Operations on Streams - mapping
### map
Applying a function to each element of a stream.  Streams support the map method, which takes a function as argument.  The function is applied to each element, mapping it into a **new** element (note **not** transforming an existing instance).  
Chain `map` functions together to perform consecutive mappings:
```Java
List<Integer> dishNameLengths = menu.stream()
                                   .map(Dish::getName)
                                   .map(String::length)
                                   .collect(toList());
```
### flatMap
...returns a stream consisting of the results of replacing each element of this stream with the contents of a mapped stream produced by applying the provided mapping function to each element.
```Java
flatMap method lets you replace each value of a stream with another stream and then concatenates all the generated streams into a single stream
```
## Finding and matching
short-circuiting
### anyMatch (Predicate p)
returns boolean, so is terminal op
### allMatch (Predicate p)
### noneMatch (Predicate p)
### findAny
```Java
Optional<Dish> dish =
  menu.stream()
      .filter(Dish::isVegetarian)
      .findAny();
```
### java.util.Optional
The Optional<T> class (java.util.Optional) is a container class to represent the existence or absence of a value.
```Java
  .isPresent() // returns true if Optional contains a value, false otherwise.
  .ifPresent(Consume r<T> block) // executes the given block if a value is present. i.e. the Consumer functional interface (lets you pass a lambda that takes an argument of type T and returns void).
  T get() // returns the value if present; otherwise it throws a NoSuchElement-Exception.
  T orElse(T other) // returns the value if present; otherwise it returns a default value.
```
### findFirst
## Reducing
In a reduction operation a stream is reduced to a value: queries combine all the elements in the stream repeatedly to produce a single value (such as an Integer).  (In functional programming-language jargon, this is referred to as a fold because you can view this operation as repeatedly folding a long piece of paper (your stream) until it forms a small square, which is the result of the fold operation).  
`reduce` takes two arguments:
1. An initial value, e.g. 0.
1. A `BinaryOperator<T>` to combine two elements and produce a new value; this functional interface can accept a Lambda, e.g.
```Java
int product = numbers.stream().reduce(1, (a, b) -> a * b);
```
A method reference is arguably a more elegant way to write this:
```Java
int sum = numbers.stream().reduce(0, Integer::sum);  // From Java 8 the Integer class has static 'sum' method to add two numbers
```
:warning: There’s also an overloaded variant of reduce that doesn’t take an initial value, but it returns an Optional object:
```Java
Optional<Integer> sum = numbers.stream().reduce((a, b) -> (a + b));
```
Use `reduce` to find min and maximum
```Java
Optional<Integer> max = numbers.stream().reduce(Integer::max);
Optional<Integer> max = numbers.stream().reduce(Integer::min);
```
:warning: Note, a stream also supports the methods `min` and `max` that take a Comparator as argument to specify which key to compare with when calculating the minimum or maximum, e.g.
```Java
Optional<Transaction> smallestTransaction = transactions.stream()
                                .min(comparing(Transaction::getValue));
```  
:question: How would you count the number of items in a stream using the map and reduce methods?  `map` each value to return 1, then use `reduce` to increment a count till the end of the stream is reached.
`map-reduce` pattern is similar to the built in `...stream().count()`.
Use concurrent processing ()`parallelStream`) to do this more efficiently,
```Java
int sum = numbers.parallelStream().reduce(0, Integer::sum);  
```
:warning: But beware! this code :point_up: MUST NOT change any state (or it won't be threadsafe and the operation needs to be associative and commutative so it can be executed in any order.
## Primitive stream specializations
These help avoid the overhead of boxing and unboxing - when, for example, a `map` operation returns a stream of Integers and a `reduce` operation then wants to do something like, `...reduce(Integer::sum)` :point_left: this is a lot of object creation :unamused:.  
Java 8 introduces three primitive specialized stream interfaces to tackle this issue, IntStream, DoubleStream, and LongStream, which respectively specialize the elements of a stream to be int, long, and double  
### Numerics
`mapToInt`, `mapToDouble`, and `mapToLong`  
`mapToInt` returns an `IntStream`.  You can then call `max`, `min`, and `averag` on the IntStream.
### Back to general stream...
To convert from a primitive stream to a general stream (each int will be boxed to an Integer) you can use the method `boxed`, as follows:
```Java
IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
Stream<Integer> stream = intStream.boxed();
```
### Default numbers (need to distinguish between 0 as the result versus zero in the absence of a value)
There’s a primitive specialized version of Optional for the three primitive stream specializations: `OptionalInt`, `OptionalDouble`, and `OptionalLong`.
```Java
OptionalInt maxCalories = menu.stream()
                              .mapToInt(Dish::getCalories)
                              .max();
                //then you can do...
int max = maxCalories.orElse(1);              
```
## Creating Streams
### ... from literal values
```Java
Stream stream = Stream.of("Modern ", "Java ", "In ", "Action");
stream.map(String::toUpperCase).forEach(System.out::println);
```
### ... an empty stream
```Java
Stream<String> emptyStream = Stream.empty();
```
### ... from (potentially) null values
e.g. System.getProperty returns null if there is no property with the given key.
```Java
String homeValue = System.getProperty("home");
Stream<String> homeValueStream
    = homeValue == null ? Stream.empty() : Stream.of(value);  // needs explicit nullcheck
// but Stream.ofNullable provides for simpler code
Stream<String> homeValueStream
    = Stream.ofNullable(System.getProperty("home"));:
```
### ... potentially null values with flatMap
This pattern can be particularly handy in conjunction with flatMap and a stream of values that may include nullable objects:
```Java
Stream<String> values =
   Stream.of("config", "home", "user")
         .flatMap(key -> Stream.ofNullable(System.getProperty(key)));
```
### ... from arrays
```Java
int[] numbers = {2, 3, 5, 7, 11, 13};
int sum = Arrays.stream(numbers).sum();
```
### ... from files
Java’s NIO API (non-blocking I/O), which is used for I/O operations such as processing a file, has been updated to take advantage of the Streams API. Many static methods in java.nio.file.Files return a stream. For example, a useful method is Files.lines, which returns a stream of lines as strings from a given file.
```Java
long uniqueWords = 0;
// try-with-resources (Stream interface is AutoCloseable)
try(Stream<String> lines =
          Files.lines(Paths.get("data.txt"), Charset.defaultCharset())){
uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                   .distinct()
                   .count();
}
catch(IOException e){

}
```
### ... from Functions (infinite streams)
The Streams API provides two static methods to generate a stream from a function: `Stream.iterate` and `Stream.generate`
