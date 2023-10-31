# 03 - Java 8 - Method References (shorthand lambdas!)
## Usage?
Where exactly can you use method references? - anywhere you can use a lambda!
## Syntax
When you need a method reference, the target reference is placed before the delimiter :: and the name of the method is provided after it. For example, Apple::getWeight is a method reference to the method getWeight defined in the Apple class. (Remember that no brackets are needed after getWeight because you’re not calling it at the moment, you’re merely quoting its name.)
### Examples
#### static methods
1. A method reference to a static method (for example, the method parseInt of Integer, written Integer::parseInt)
```Java
// this implementation of a functional interface...
Function<String, Integer> func = s -> Integer.parseInt(s);
// substitute the method ref into the lambda
Function<String, Integer> func = s -> Integer::parseInt; // static
// or even, if the method is returning the Function implementation - a lambda shorthand would be,
Function<String, Integer> myFunc() {
    // return () -> Integer.parseInt(s);
    return Integer:parseInt;
}
```  
#### arbitrary type, instance methods
1. A method reference to an instance method of an arbitrary type (for example, the method length of a String, written String::length) <-- referring to a method to an object that will be supplied as one of the parameters of the lambda.  
```Java
// this implementation of a functional interface...
Function<String, Integer> func = s -> s.length();
// substitute the method ref into the lambda
Function<String, Integer> func = s -> String::length;  // instance method on parameter passed into Lamdba
```  
#### existing object, instance methods
1. A method reference to an instance method of an existing object or expression (for example, suppose you have a local variable expensiveTransaction that holds an object of type Transaction, which supports an instance method getValue; you can write expensiveTransaction::getValue) <-- refers to a situation when you’re calling a method in a lambda to an external object that already exists
### Constructor references
You can create a reference to an existing constructor using its name and the keyword new as follows: `ClassName::new`. (It works similarly to a reference to a static method).  
```Java
// no args constructor
Supplier<Apple> c1 = () -> new Apple();
Apple a1 = c1.get();
// with arguments constructor
// If you have a constructor with signature Apple(Integer weight), it fits the signature of the Function interface, so you can do this
Function<Integer, Apple> c2 = Apple::new;
Apple a2 = c2.apply(110);
// 2 args constructor
// If you have a two-argument constructor, Apple (Color color, Integer weight), it fits the signature of the BiFunction interface, so you can do this:
BiFunction<Color, Integer, Apple> c3 = Apple::new;
Apple a3 = c3.apply(GREEN, 110);
```
### Common Usage patterns
#### java.util.Comparator
has functional interface, `int compare(T o1, T o2)` **BUT** also has default methods that allow for additional take on behaviour.  
```Java
// naive approach does full blown anonynous class implementation inline
List<Apple> sortedApples = appleCart.sort(new Comparator<Apple>() {
    public int compare(Apple a1, Apple a2){
        return a1.getWeight().compareTo(a2.getWeight());
    }
});
//  Comparator#compare() functional interface takes 2 args of the same genericised type (Object) and returns an int.  List#sort() takes a Comparator...
List<Apple> sortedApples = appleCart.sort((Apple a1, Apple a2) -> {
   return a.getWeight().compareTo(a2.getWeight());
 });
 // can be... with type implied from List's <T> type being 'Apple' (and return keyword ommitted)
List<Apple> sortedApples = appleCart.sort((a1, a2) -> a.getWeight().compareTo(a2.getWeight()));
 // and eventually, incorporating status Comparator#comparing() method, which takes just one arg - a Function extracting a Comparable key - and produces a Comparator object.
 import static java.util.Comparator.comparing;
 inventory.sort(comparing(apple -> apple.getWeight())); // Comparator#compare() is a func interface so can be replaced by a lambda, which in turn can be replaced by a metnod ref
```  
**Now to add behaviour without needing to amend the comparison func**.
```Java
// Sort in reverse order
appleCart.sort(comparing(Apple::getWeight).reversed());
// chaining more comparitors for finer detail sorting
appleCart.sort(comparing(Apple::getWeight)
         .reversed()
         .thenComparing(Apple::getCountry));
```
#### java,util.function.Predicate
```Java
// combine predicates
Predicate<Apple> redAndHeavyAppleOrGreen =
    redApple.and(apple -> apple.getWeight() > 150)
            .or(apple -> GREEN.equals(a.getColor()));  
```
#### java,util.function.Function
The Function interface comes with two default methods for this: `andThen` and `compose`, which both return an instance of Function.
```Java
// combine functions
Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = f.compose(g);
int result = h.apply(1);
```
