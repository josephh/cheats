# Kotlin 002 - Functions
* Unlike Java, function declarations can live outside classes
```Kotlin
// Unlike Java, types are specified after declarations. Visually, this nicely aligns function names into the same column.
// Unlike Java, arguments can have default values. Those values can be expressions.
fun appendInt(str: String, b: Int = 0): String {
    return str + b.toString()
}

fun appendStringAndGetLength(str: String, b: String): Int {
    return (str + b).length
}

fun appendix(str: String): String {
    return str + "x"
}
```
* Functions which are only called for their side effects (i.e. which do not return a value) return the type Unit.  Unit is similar to void in Java, except it is an actual type: e.g. you can write Kotlin, `if(x is Unit)`.
* Functions which consist of a single expression can be written as such:
`fun multiply(x: Int, y: Int): Int = x * y`
* Kotlin supports named arguments, which eliminates the need for builders,
```Kotlin
fun buildComplicatedThing(
	name: String,
	surname: String,
    // This is a nullable type. More on this later.
	phoneNumber: String? = null,
	age: Int,
    // Yeah, you can do that. More on this later
	ageSinceAdult: Int = if(age - 18 > 0) age - 18 else 0,
	height: Int
) {
    // ...
}

fun main() {
    // This basically eliminates the need for builders
    buildComplicatedThing(
  		name = "Arnosht",
  		age = 20,
  		surname = "Qleechek",
  		height = 180
    )
}
```
