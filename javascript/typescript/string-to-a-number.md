# How to convert a string to a number in TypeScript
The solution

function stringToNumber(s: string): number | undefined {
  const n = parseFloat(s)
  if (isNaN(n)) {
    return undefined
  }
  return n
}
Why we need to do this

JavaScript has a function parseFloat() that converts strings into numbers.

> parseFloat('3')
3
 > parseFloat('2.71828')
2.71828
parseFloat() may seem like a complete solution on its own, but there's a big problem here. If we call parseFloat with something that's not a number, it will return NaN, which means "Not a Number".

> parseFloat('three')
NaN
Any operation on a NaN returns another NaN. Unfortunately, that means that NaNs will propagate through our code, with every operation on them returning yet another NaN.

> parseFloat('a3') + 1
NaN
> 1 / (parseFloat('a3') + 1 * 20)
NaN
NaN is defined to be equal to nothing, not even itself. That means we can't use the equality operator === to check for whether parseFloat() returned NaN.

> parseFloat('three') === NaN
false
Instead, we can check the result of parseFloat() with the isNaN() function, which will tell us whether we actually parsed a number or not. isNaN() will return true if called on NaN.

> isNaN(NaN)
true
> isNaN(parseFloat('a3'))
true
> isNaN(parseFloat('5'))
false
Now we've seen all of the components of our stringToNumber function. It uses parseFloat to parse the string into a number, then isNaN to make sure that it actually got a number out. If the parse fails, it returns undefined.

function stringToNumber(s: string): number | undefined {
  const n = parseFloat(s)
  if (isNaN(n)) {
    return undefined
  }
  return n
}
This function parses numbers correctly!

> stringToNumber('3')
3
> stringToNumber('3.1415926')
3.1415926
It returns undefined when the result is not a number.

> stringToNumber('three')
undefined
> stringToNumber(null)
undefined
Type safety when parsing numbers

When we use this function, the TypeScript compiler makes us check for undefined before using the result as a number.

> stringToNumber('3') + 1
type error: Object is possibly 'undefined'.
> const n = stringToNumber('3')
  n !== undefined ? n + 1 : undefined
4
The TypeScript compiler won't let us pass in inputs that don't make sense, like non-string values.

> stringToNumber({})
type error: Argument of type '{}' is not assignable to parameter of type 'string'.
