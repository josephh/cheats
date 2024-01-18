# FUNCTIONAL & LAMBDA Java 21, 11 and 8.  Advanced (notes). 

## Interfaces revisited
* Compiler will ensure implementing concrete classes have implementations for __each__ abstract method.
* Since Java 8, interfaces can include 'default' and 'static' concrete methods.  Inheriting classes do not have to provide implementations of default or static methods.  Default methods are inheritable but static methods are not. 
* top level interface methods can have public or package-private visibility but are implicitly public.
* you can't instantiate an interface but you CAN use the new keyword with an interface when providing an interface as an inner anonymous class.
## Functional Interfaces
...have a SAM (Single Abstract method), ignoring abstract and default methods as well as methods inherited from java.lang.Object.
`@FunctionalInterface` annotations are used to mark these interfaces.
## Lambdas
Lambda expressions are INSTANCES OF CLASSES that implement a functional interface.  They can only be used with functional interfaces, since the compiler has to treat the lambda as a concrete implementation of an interface and has no way to tell one abstract method from another.
* Lambda syntax doesn't need curly braces for a single line method implementation.
## Predicate and BiPredicate
`java.util.function.Predicate.java` is a functional interface that has one abstract method: `boolean test(T t)`. This method is typically used in the following way: create a concrete Predicate instance, with a Lambda, then execute its `test` method.  (It's commonly used as a filter). E.g.
```java
Predicate<String> p = s -> s.contains("Hello");
p.test("Hello World!"); // returns true
```
BiPredicate takes 2 generic inputs, e.g.
```java
BiPredicate<String, Integer> checkLength = (s, i) -> s.length() > i;
p.test("Hello World!", 5); // returns true
```
## Supplier
`Supplier` functional interface has a single `get` method and is useful for supplying values despite an absence of input.
It has one method: `get`, takes no arguments and returns a type.
Some examples,
```java
Supplier<StringBuilder> sb = () -> new StringBuilder(); // never any input args
sb.get().append("some chars to add to string builder");

Supplier<Double> random = () -> Math.random();
Double aNewRandomDouble = random.get();
```
## Consumer and BiConsumer
functional interfaces that take input and return nothing, its one abstract method is `void accept(T t)`. E.g.
```java
Consumer<String> printC = s -> System.out.println(s);
printC.accept("Hello world");

// BiConsumer is popularly used with maps - as it takes 2 inputs, suitable for use as a key-value pair.
var m = new HashMap<String, String>();
// HashMap#put returns the corresponding value if the key was already present in the map, or null if not, but the return value is simply discarded by the Lambda since it has a void return type.
BiConsumer<String, String> capitals = (k, v) -> m.put(k, v);
```
## Function and BiFunction
java.util.Function its method takes a generically-typed input and returns a generically-typed response value.  `R apply(T t)`
When instantiating a Function, the type parameter order is input first and return type as 2nd type param, e.g.
```java
Function<String, Integer> strLen = s -> s.length();
strLen.apply("hello"); // returns 5
```
BiFunction takes 2 inputs and returns a value.  Of its generic types the return type is always the last one.
## UnaryOperator and BinaryOperator functions
These functions are closely related to Function and BiFunction - they take 1 and 2 inputs respectively - but there is only 1 type parameter as they must return a value of the same type as the input.  
## Final and effectively final
this is to do with local method-scoped variables that get used in a Lambda.  Lambdas are like "snapshots" of behaviour that can be passed around, so variable values cannot change once a Lambda is using it - since there's no certainty of when the Lambda will be used and execute.
## Method references
If all a Lambda does is call one method, then it's an opportunity to use method references.
Method references help make code more concise with a 'shorthand' where the use of variables can be omitted since those values are implied.  The following comparison illustrates this,
```java
List<String> names = Arrays.asList("joe", "jon", "jack");
names.forEach(name -> System.out.println(name));
// since we know the argument to Consumer functional interface (accept method) is the name variable, we can omit it.
names.forEach(System.out::println); // this is the method ref equivalent. Note! no method parentheses used in the syntax of method refs!
```
Compiler turns method ref into a Lambda in the background.
There are 4 types of method reference,
1. bound - instance known at compile time
1. unbound - instance not known until runtime
1. static
1. constructor
### 'Bound' method refs
...these are when we have an object instance to work with (i.e. this)
comparing lambda vs instance method ref for a known instance:
```java
String name = "JOHN";
Supplier<String> toLowerLambda = () -> name.toLowerCase(); 
Supplier<String> toLowerMethodRef = name::toLowerCase; // 'bound' method ref. Note! no method parentheses used in the syntax of method refs!
System.out.println(toLowerMethodRef.get()); // john
```
### 'Unbound' methods refs
```java
Function<String, String> toUpperLambda = s -> s.toUpperCase(); 
Function<String, String> toUpperMethodRef = s -> String::toUpperCase; 
System.out.println(toUpperMethodRef.apply()); // JOHN

BiFunction<String, String, String> concatLambda = (s, t) -> s.concat(t); // last generic param type is the return type, first 2 are types of args to the bi function 
BiFunction<String, String, String> concatMethodRef = (s, t) -> String::concat; 
System.out.println(concatMethodRef.apply("app", "ples")); // apples
```
### 'Static' method refs are also considered 'unbound'
e.g.
```java
Consumer<List<Integer>> sortLambda = list -> Collections.sort(list); // the list variable is redundant here and could be replaced with a method ref
Consumer<List<Integer>> sortMethodRef = Collections::sort; 
List<Integer> ints = Arrays.asList(2,5,45,21,9);
sortMethodRef.accept(ints);
System.out.print("sorted ints now : " + ints);  // 2, 5, 9, 21, 45
```
### Constructor method refs
```java
Function<Integer, List<String>> arrayListLambdaNew = i -> new ArrayList(i);
Function<Integer, List<String>> arrayListMethodRefNew = ArrayList::new;
List<String> stringList = arrayListMethodRefNew.apply(10);
stringList.add("joe");
```
