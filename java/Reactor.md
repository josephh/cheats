# Reactor core
In order for an application to be reactive, the first thing it must be able to do is to produce a stream of data.  Reactive Core gives us two data types that enable us to do this: Flux and Mono.  First, it should be noted that both Flux and Mono are implementations of the Reactive Streams `Publisher` interface.  But the need for the 2 different implementations is driven by the different use cases that they support.  
The core difference between Reactive and a Java 8 __Stream__ `collect`, is the model: Reactive is a push model and Java 8 Streams a pull. Using the reactive approach, events are pushed to subscribers.  Streams also has a termination point - i.e. all data is received and then a result is returned.  With Reactive there can be an infinite stream coming in from an external resource, with multiple subscribers attached and removed on an ad hoc basis. Streams can be combined, throttled, and backpressure applied.
## Flux
Flux is a stream that can emit 0..n elements.
```Java
Flux<Integer> just = Flux.just(1, 2, 3, 4);
// or...
Publisher<Integer> just = Flux.just(1, 2, 3, 4);
```
## Mono
Mono is a stream of 0..1 elements.
```Java
Mono<Integer> just = Mono.just(1);
// or...
Publisher<Integer> just = Mono.just(1);
```
### Stream Subscriber
Once a stream of data is provided, a subscribe is needed for those elements to be emitted from the stream.
```Java
List<Integer> elements = new ArrayList<>();

Flux.just(1, 2, 3, 4)
  .log()
  .subscribe(elements::add);

assertThat(elements).containsExactly(1, 2, 3, 4);
```
### Operations on a stream
#### Map data  
...apply a transformation
```Java
Flux.just(1, 2, 3, 4)
  .log()
  .map(i -> i * 2) // <- this is the operation `onNext()` will execute
  .subscribe(elements::add);
```
#### Combine 2 Streams
```Java
Flux.just(1, 2, 3, 4)
  .log()
  .map(i -> i * 2)
  .zipWith(Flux.range(0, Integer.MAX_VALUE), // <- create another Flux that keeps incrementing by one and streaming it together with our original on
    (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
  .subscribe(elements::add);
```  

## Hot Streams
'Hot streams' are always running - with no predictable end (an example might be mouse moves in a UI).  One way to create a hot stream is by converting a cold stream into one. The below Flux lasts forever, outputting the results to the console, thereby simulating an infinite stream of data coming from an external resource:
```Java
ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
    while(true) {
        fluxSink.next(System.currentTimeMillis());
    }
})
  .publish(); // <- publish() creates a ConnectableFlux and means that calling subscribe() won't cause it to start emitting, allowing us to add multiple subscriptions.
publish.subscribe(System.out::println);        
publish.subscribe(System.out::println);
// nothing will happen until connect() is called, then the Flux will start emitting:
publish.connect();
```
### Throttling
Is needed to prevent the process being overwhelmed (but consider other strategies such as such as 'windowing' and 'buffering')
```Java
ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
    while(true) {
        fluxSink.next(System.currentTimeMillis());
    }
})
  .sample(ofSeconds(2)) // <- limit rate of input
  .publish();
```
