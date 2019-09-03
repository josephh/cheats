# Advanced Javascript notes
 ## Javascript J.I.T compiler

 1. register variable declarations
 1. use LHS and RHS principles to assign or initialise values.  While LHS and RHS are typically seen either side of an equals sign, there are other ways to assign values that don't include an equal sign, for example, when passing a parameter value to a function local variable (or parameter).  In that case LHS typically refers to the _target_ of an assignment and RHS, the _source_.

 1. When not running in **_strict mode_**, if a variable assignment is made, in a local scope, for a variable that has not been previously declared, then the compiler will create a variable with **_global scope_**!  **Undefined** will be assigned to a variable with no initial assignment.
 1. **Undefined** is an actual value - one of Javascript's primitive types.
 1. Following a function object declaration, the subsequent variables referencing that object - provided they are not assigned to anything - are RHS references.  In the following example, the parentheses indicate that it should be executed.

 ```javascript
function bar(){
  console.log('some message');
}
bar();  // an RHS reference
```
Note, unlike LHS references that don't have a corresponding `var ...` declaration, if an RHS reference is given, in strict mode and non-strict mode, that has no corresponding function declaration, no reference-with-global-scope is created, instead a reference error is returned.

## function declaration
 - 'function' keyword is the first thing in the statement

e.g.
```javascript
function bar () { /* function body goes here */ }; // this is a 'named' function declaration
function () { /* function body goes here */ }; // this is an 'anonymous' function declaration
```

## The scope **difference** between function _declarations_ and **named** function _expressions_
 * a **named** function expression *does not* get added to the enclosing scope (as a function **declaration** does).  A **named** function can be referred to itself, within its own scope but not outside of it (as a function declaration can).
 ```javascript
 var foo = function bar(){ /* function body goes here */ }; // this is a 'named' function expression but 'bar' isn't part of any outer scope - it has its own scope only
 var foo = function(){ /* function body goes here */ }; // this is an 'anonymous' function expression
 ```

 Anonymous function expressions (versus _named_ function expressions) have the following drawbacks:
 - can't refer to itself (e.g. for recursion) from inside of itself without a name
 - debugging is hard without names
 - self-documenting, more-easily-readable code

## Function Invocation - the 4 constructs
1. methods
An object with a function assigned to one its properties, is invoked a method.  `this` is bound to the containing object
```javascript
let O = { i: 0 }
O.add = function (n) {
  this.i = this.i + n;
  return this.i;
}
```
2. constructors
```javascript
```
3. prototypal (`apply`)
```javascript
```
4. function
```javascript
```

## Scope miscellany
```javascript
 try{}catch(err){/* catch block has its own scope - meaning that err is not available outside of the catch block.*/}
 ```
 IE6 ignored the spec on this! (But later IE implementations fixed this).

 ## Lexical scope vs dynamic scope (and cheating lexical scope)
 Lexical scope is the main scope strategy in javascript (and most other languages); but dynamic scope is also available in bash, as an opt-in mode for perl and javascript.
  * Lexical scope = compile-time scope.  Global scope, functions create new units of scope etc.
  * **eval** allows developers to cheat the lexical scoping model.  _Eval_ treats string input as code... by modifying - and potentially disrupting - existing lexical scope and allowing dynamic variable declaration.  So in the following example, there is a _bar_ variable declared with global scope but use of eval within the function will result in a function-scoped _bar_ variable, (provided the method takes a parameter that will have that effect courtesy of **eval**).  `setTimeout('string syntax...')` should be avoided too - in this form - since it uses eval under the covers.

```javascript
var bar = "some-text";
console.log('the value of bar is ' + bar);

function baz(str) {
  eval(str);
  console.log('the value of bar is now ' + bar);
}

baz('var bar = 49;');
```

'Cheating' javascript compiler/ engine prevents any optimisation at all, resulting in slow running code.

:exclamation: avoid **_eval_** as a general rule.

 * **with** keyword is problematic too.  It is a shorthand style for using an object and its properties, without having to re-type the object name.  However, use of the keyword `with` produces a block scope, e.g.

```javascript
var obj = {
  a: 1, b: 3, c: 5
};

with(obj) {
 a = b + c;  // a is 8, ok
 d = 3; // error here - won't create an auto-global !!
}
```
 * **with** is especially bad as it produces a whole new lexical  scope at runtime; the compiler disables many optimisations when it comes across **with**.  See note below about **with** and strict mode.

## IIFE and 'scope' hiding
 * :warning: IIFE stands for 'immediately invoked function expression'.

Wrapping a function in parenthesis is one js technique for creating a new scope (similar to use of curly braces or block scopes in other languages).  The opening parenthese around the function keyword means it is an expression not a declaration, e.g.
```javascript
var foo = "foo";
(function iife() {
  var foo = "something different";
  console.log(foo);  // "something different"
})();  // ...and execute it.

console.log("foo");  // "foo"
```
IIFE-style code can aA more reader-friendly version of the above - since it's more explicit about what is private and what is global - would be,
```javascript
var foo = "foo";
(function iife(global) {
  var privateFoo = "some private string";
  global.publicFoo = "some public string";
})(window);
```

## Strict mode
 * Improves code optimisation.
 * **with** keyword is not permitted in strict mode.

## ES6
### **Let** keyword
 * Acts like the `var` keyword but **Let** _does not_ add the variable to the enclosing function (or global) scope but to the closest enclosing block scope (e.g. a **for** loop or **if** statement).
 * **Let** applies good software practise like the principle of 'least disclosure'.
 * **Let** with `{}` creates a block scope in ES6.

## Hoisting
During compilation, the javascript compiler will find all function declarations and register those.  Hoisting is a term that helps to understand which variables will be known at execution time and which values will have been assigned.

 If a script includes a function *declaration* similar to,
```javascript
function b() {
  return 'some value';
};
```
then the following will execute successfully because the b function will have been 'hoisted':
```javascript
console.log(b);
function b() {
  return 'some value';
};
```
However, for a function expression, like,
```javascript
var c = function b() {
  return 'some value';
}
```
The variable c is known to the compiler - via pre-compilation - but the assigned value will not be available until that line executes.

**Functions are hoisted above variables.**
Bitwise operators treat operands as a sequence of 32 bits - rather than decimal, hexadecimal or octal numbers.
Javascript does the operation on 32-bit representations of numbers, but returns a standard Javascript numerical value.
## Bitwise operations
### AND &
`var a = 9 /* 32 bit encoding 00000000000000000000000000001001 */, b = 10 /* 32 bit 00000000000000000000000000001010 * /, c = a & b` // (c is 8) Returns a one in each bit position for which the corresponding bits of BOTH operands are ones.
### OR |
`var a = 16 /* 32 bit 00000000000000000000000000001000 */, b = 3 /* 32 bit 00000000000000000000000000000011, c = a | b` // (c is 19) 	Returns a one in each bit position for which the corresponding bits of either or both operands are ones.
### XOR ^
`var a = 5 /* 32 bit 00000000000000000000000000000101 */, b = 2 /* binary 32 bit 00000000000000000000000000000010 */, c = a ^ b`  // (c is 7) Returns a one in each bit position for which the corresponding bits of either but not both operands are ones.
### NOT ~
`var a = ~ 19 /* 32 bit 00000000000000000000000000010011 */` // (a is (32 bit 00000000000000000000000000001100) 12) 	Inverts the bits of its operand.
### LEFT shift <<
`var a = 2 << b` // Shifts _a_ in binary representation _b_ (< 32) bits to the left, shifting in zeroes from the right. E.g. `(a is 8 (32 bit 00000000000000000000000000001000))`
### Sign propagating right shift >>
Shifts a in binary representation b (< 32) bits to the right, discarding bits shifted off.
E.g. `var a = 32 /* 32 bit 00000000000000000000000000100000 */ >> 2 (a is 8 (32 bit 00000000000000000000000000001000))`
### Zero-fill right shift >>>
Shifts a in binary representation b (< 32) bits to the right, discarding bits shifted off, and shifting in zeroes from the left.
### One's compliment = bitwise not + 1
e.g.
```javascript
var a = 314, b = ~a + 314 // (a is -314)
```
