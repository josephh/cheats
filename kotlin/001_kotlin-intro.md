# Kotlin 001 - Intro  Kotlin mainly targets the JVM but also has variants that target JavaScript or native code
> Kotlin introduces features that actually allow us to transform whole categories of run-time errors into compile-time errors (although, to be very clear, these features did not originate in Kotlin), and this is what I consider to be its most fundamental advantage — Kotlin will help you write safer code. Type-level nullability is the most commonly quoted example of such a feature, but there are many others. Sealed classes and value classes pave the way for strongly typed programming techniques, and the Result class provides tools to finally dispose of exceptions once and for all. While it might sound too good to be true, whole subcategories of the mistakes listed above (missing switch branches, etc.) can be transformed into compile-time errors.  

* you can use Java libraries in Kotlin
* you can use Junit.  Also Ktor comes with its own test framework (see later)
*  Kotlin/JVM compiles down to Java bytecode
  * `val` keyword is for final (immutable) variables
  * `var` for mutable variables
  *  Constructors and getters are generated at compile time, e.g. for
  ```Kotlin
    class Person(
    	val name: String,
    	val age: Int
    )
  ```
  * `equals()` and `hashCode()` - make class a data class and `equals()` and `hashCode()` are generated out of the box.
  ```Kotlin
  data class Person(
  	val name: String,
  	val age: Int
  )
  ```
* Syntax
 * Semicolons are optional (and never used in practice)
* Multi-threading
  * coroutines : to write asynchronous, non-blocking code.   The core idea is having functions that can be suspended; in other words, the computation can be suspended at some point and resumed later on. The best part is that when writing non-blocking code, the programming model does not really change so writing non-blocking code is essentially the same as writing blocking code.
  ```Kotlin
  fun sendRequest(): Int {
   /* do some heavy work: BLOCKING */
   return 1;
  }
  suspend fun sendRequest(): Int {
   /* do some heavy work: NON-BLOCKING */
   return 1;
  }
  ```
* Async Server/ services - Ktor framework
  * Example
  ```Kotlin
  fun main() {
  	embeddedServer(Tomcat, 8080) {
      	routing {
          	get { call.respond("Hello world!") }
      	}
  	}.start(wait = true)
  }
  ```
