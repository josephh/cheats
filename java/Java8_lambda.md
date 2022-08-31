# Java Lambdas

1. Anonymous— We say anonymous because it doesn’t have an explicit name like a method would normally have; less to write and think about!
1. Function — We say function because a lambda isn’t associated with a particular class like a method is. But like a method, a lambda has a list of parameters, a body, a return type, and a possible list of exceptions that can be thrown.
1. Passed around — A lambda expression can be passed as argument to a method or stored in a variable.
1. Concise — You don’t need to write a lot of boilerplate like you do for anonymous classes.
## Usage?
Where exactly can you use lambdas?
### Within functional interfaces
You can use a lambda expression in the context of a functional interface. In a nutshell, a functional interface is an interface that specifies exactly one abstract method. Comparator and Runnable are examples of functional interfaces.  An interface is still a functional interface if it has many default methods as long as it specifies **only one abstract method**.
## Syntax
basic syntax `(parameters) -> expression`
valid examples (Java 8)
```Java
// (optional parameters...) -> function body, implying return value.  If a return keyword appears, curly braces must be used.   
(String s) -> s.length()

(Apple a) -> a.getWeight() > 150

(int x, int y) -> { // curly braces also required for multi line bodies
    System.out.println("Result:");
    System.out.println(x + y);
}

() -> 42

(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())
```
:important: NOTE, void method invocations get special treatment in Java - even though they don't return a value they don't need curly braces.
### Function descriptor
The signature of the abstract method of the functional interface describes the signature of the lambda expression. We call this abstract method a function descriptor.
## @FunctionalInterface?
Java 8 class optional annotation (for documentation).  Also compiler will return a meaningful error when it sees this annotation but the class doesn't conform to a functional interface (i.e. only one abstract method).
## 'Execute around' pattern
1. parameterise behaviour
```java
// we want to pass a reader, read 2 lines and return them
String result = processFile((BufferedReader reader) -> reader.readLine() + reader.readLine());
// this emthod illustrates the lambda approach
```
1. use a functional interface - to pass behaviour
```Java
@FunctionalInterface
public interface BufferedReaderProcessor {
  String process(BufferedReader r) throws IOException; // single abstract method
}
```
implementations of this functional interface can now be passed into a 'processFile' method.
```Java
public String processFile(BufferedReaderProcessor p) throws IOException {
   ...
}
```
1. execute the function
```Java
public String processFile(BufferedReaderProcessor p) throws IOException {
    try (BufferedReader br =
                   new BufferedReader(new FileReader("data.txt"))) {
        return p.process(br);  // now runt he bahaviour
    }
}
```  
1. provide multiple functional interface instances
```Java
String oneLine = processFile((BufferedReader br) -> br.readLine());
String twoLines = processFile((BufferedReader br) -> br.readLine() + br.readLine());
```
## Common Java 8 Functional interfaces
* @FunctionalInterface java.util.function.Predicate<T>
boolean test(T t)
* @FunctionalInterface java.util.function.Consumer<T>
void accept(T t); // access an object of type T and perform some operations on it; note `void` return type
* @FunctionalInterface java.util.function.Function<T, R>
R apply(T t); // define a lambda that maps information from an input object to an output
### Primitive Specialisations
Avoid the performance cost adding by autoboxing (using up heap memory) by using e.g. java.util.function.IntPredicate (and similar for other primitive data types).
```Java
public interface IntPredicate {
    boolean test(int t);
}
IntPredicate evenNumbers = (int i) -> i % 2 == 0;
```
### Type Safety/ Type checking
Lambda 'type' is deduced from context of where it's used.  Is it assigned to a variable?  or passed as an argument to a method?  The expected type is called the `target type`.
The compiler will check any generics (and do type erasure when compiling); lambda itself must match the function descriptor - i.e. what is the single abstract method in the function descriptor (what does it accept as a param and what does it return?).  
If different functional interfaces have a compatible abstract method signature, then the same lambda expression can be supplied to
