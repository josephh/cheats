# 05 - Java 8 - Collectors
## Predefined collectors,
* Reducing and summarizing stream elements to a single Value
* Grouping elements
* Partitioning elements
`collectors` are the parameter(s) to the `stream` method `collect()`
```Java
import static java.util.stream.Collectors.*;
...
long howManyDishes = menu.stream().collect(Collectors.counting());
// or more succinctly,
long howManyDishes = menu.stream().count();
```
### max/ min
`Collectors.minBy`, `Collectors.maxBy`
```Java
Comparator<Dish> dishCaloriesComparator =
    Comparator.comparingInt(Dish::getCalories);
Optional<Dish> mostCalorieDish =  // Optional<T> in case menu is empty!
    menu.stream()
        .collect(maxBy(dishCaloriesComparator));
```
### sum (or summarization)
`Collectors.summingInt`, `Collectors.summingLong` and `Collectors.summingDouble`
```Java
double avgCalories =
    menu.stream().collect(averagingInt(Dish::getCalories));
```
**more powerful methods - `Collectors.SummarizingInt`, `LongSummaryStatistics` and `DoubleSummaryStatistics` - return objects that include properties: count, sum, min, average, max.
### Joining Strings
```Java
String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));  //  internally makes use of a StringBuilder
```
## Generalized summarization with reduction
All the collectors we’ve discussed so far are, in reality, only convenient specializations of a reduction process that can be defined using the reducing factory method. The Collectors.reducing factory method is a generalization of all of them...
### reduce vs collect?
* reduce method is meant to combine two values and produce a new one
* collect method is designed to mutate a container to accumulate the result it’s supposed to produce.
ALWAYS USE COLLECT WHEN REDUCING ON A MUTABLE CONTAINER, IT CAN BE PARALLELIZED IN A THREAD-SAFE (where reduce can't)

## Grouping
The Collectors class provides another Collector through the mapping method that accepts a mapping function and another Collector used to gather the elements resulting from the application of that function to each of them.  
```Java
Map<Dish.Type, List<String>> dishNamesByType =
      menu.stream()
          .collect(groupingBy(Dish::getType,
                   mapping(Dish::getName, toList())));
```
You could also use a third Collector in combination with the groupingBy to perform a flatMap transformation instead of a plain map,
```Java
Map<Dish.Type, Set<String>> dishNamesByType =
   menu.stream()
      .collect(groupingBy(Dish::getType,
               flatMapping(dish -> dishTags.get( dish.getName() ).stream(),
                           toSet())));
```
### Adapting the collector result to a different type
Because the Optionals wrapping all the values in the Map resulting from the last grouping operation aren’t useful in this case, you may want to get rid of them. To achieve this, or more generally, to adapt the result returned by a collector to a different type, you could use the collector returned by the Collectors.collectingAndThen factory method, as shown in the following listing.

## . The main static factory methods of the Collectors class
|Factory method|Returned type| Used to|
|---|---|---|
|toList|`List<T>`|Gather all the stream’s items in a List.    **Example use:** List<Dish> dishes = menuStream.collect(toList());|
|toSet|`Set<T>`|Gather all the stream’s items in a Set, eliminating duplicates.  **Example use:** Set<Dish> dishes = menuStream.collect(toSet());|
|toCollection|`Collection<T>`|Gather all the stream’s items in the collection created by the provided supplier.    **Example use:** Collection<Dish> dishes = menuStream.collect(toCollection(), ArrayList::new);|
|counting|`Long`|Count the number of items in the stream.  **Example use:** long howManyDishes = menuStream.collect(counting());|
|summingInt|`Integer`|Sum the values of an Integer property of the items in the stream.|**Example use:** int totalCalories = menuStream.collect(summingInt(Dish::getCalories));
|averagingInt|`Double`|Calculate the average value of an Integer property of the items in the stream.  **Example use:** double avgCalories = menuStream.collect(averagingInt(Dish::getCalories));|
|summarizingInt|`IntSummaryStatistics`|	Collect statistics regarding an Integer property of the items in the stream, such as the maximum, minimum, total, and average. **Example use:** IntSummaryStatistics menuStatistics = menuStream.collect(summarizingInt(Dish::getCalories));||
|joining|	`String`|Concatenate the strings resulting from the invocation of the toString method on each item of the stream.  **Example use:** String shortMenu = menuStream.map(Dish::getName).collect(joining(", "));|
|maxBy|	`Optional<T>`|	An Optional wrapping the maximal element in this stream according to the given comparator or Optional.empty() if the stream is empty.  **Example use:** Optional<Dish> fattest = menuStream.collect(maxBy(comparingInt(Dish::getCalories)));|
|minBy|	`Optional<T>`|	An Optional wrapping the minimal element in this stream according to the given comparator or Optional.empty() if the stream is empty. **Example use:** Optional<Dish> lightest = menuStream.collect(minBy(comparingInt(Dish::getCalories)));|
|reducing	|_The type produced by the reduction operation_|	Reduce the stream to a single value starting from an initial value used as accumulator and iteratively combining it with each item of the stream using a BinaryOperator.  **Example use:** int totalCalories = menuStream.collect(reducing(0, Dish::getCalories, Integer::sum));|
|collectingAndThen|	_The type returned by the transforming function_|	Wrap another collector and apply a transformation function to its result.  **Example use:** int howManyDishes = menuStream.collect(collectingAndThen(toList(), List::size));|
|groupingBy|	`Map<K, List<T>>`|	Group the items in the stream based on the value of one of their properties and use those values as keys in the resulting Map.  **Example use:** Map<Dish.Type,List<Dish>> dishesByType = menuStream.collect(groupingBy(Dish::getType));|
|partitioningBy|	`Map<Boolean, List<T>>`s	|Partition the items in the stream based on the result of the application of a predicate to each of them.  **Example use:** Map<Boolean,List<Dish>> vegetarianDishes = menuStream.collect(partitioningBy(Dish::isVegetarian));|

## Collector interface
Custom implementations are allowed!
Listing 6.4. The Collector interface
```Java
public interface Collector<T, A, R> {
  Supplier<A> supplier();  // new result container
  BiConsumer<A, T> accumulator(); // new result appender
  Function<A, R> finisher(); // apply final tranformer
  BinaryOperator<A> combiner(); // merge 2 containers
  Set<Characteristics> characteristics();  // returns an immutable set of Characteristics : enum : {UNORDERED; CONCURRENT; IDENTITY_FINISH}
```
