# Javascript Nextgen Language Features
## Modules

```javascript
// person.js
const person = {
  name: 'Max'
}
export default person
```

```javascript
// utility.js
/**
 * No default export here so to use them they must be referred to by name
 */
export const clean = () => { // ... some function body
}
export const baseData = 10
```

```js
// client.js
import person from './person.js'
import { clean } from './utility.js'
// assign an alias when referencing a module export by its name
import { baseData as BaseTen } from from './utility.js'
// refer to both clean and baseData in one object with the following syntax
import { }
console.log(person.name)
```

## Classes, properties and functions

```javascript
// Classes can be written like this - with the constructor method initialising properties
class Human {
  // constructor is a default function that can be
  // added to any class and will execute on class initiation
  constructor()
  {
    this.name = 'Max';
  }  
  printMyName() {
    console.log(this.name);
  }
}

class Person extends Human {
  constructor()
  {
   super();
    this.name = 'Joe';
    this.gender = 'male';
  }  
  printMyGender() {
    console.log(this.gender);
  }
}

const jim = new Person();

jim.printMyName();
jim.printMyGender();

```
However ES7 supports setting field/ properties directly in a class.
```javascript
class Person extends Human {
  name = 'Joe';
  printMyGender = () => {
    // declaring methods with the arrow function syntax makes best use of 'this' disambiguation  
  }
}
```
## 'Spread' or 'Rest' Operators
`...`
Whether the 3 dots are a spread or rest operator depends on where it's used
### Spread
Used to split array elements or object properties
```javascript
const newArray = [...oldArray, 'new item 1', '2', '3'] a new array is created that takes an old one and adds new items to it   
```
```javascript
const newObject = {...oldObject, foo: 'bar', goo: 'far'} a new object is created built from an existing object and adds new ones (existing properties with matching keys are replaced).   
```
### Rest
Used to merge a list of (function) arguments into an array
```javascript
function sortArgs(...args) {
  return args.sort()
}
```
## Destructuring
extract single array elements, or object properties
different to spread or rest - which works on entire arrays and objects.
```javascript
{name} = {name : 'Joe', age: 32}
{age} = {name : 'Joe', age: 32}
[, x] = [3,2,4] // x = 2
[, y, x] = [3,2,4] // y = 2, x = 4
```
## Primitives and references in JS
Primitives in JS: Boolean, String, Number, Null, Undefined.  Assignment of a primitive value means the actual value is copied from one variable to another, e.g. var y = 3, z = y; // y and z are variables to 3, reassigning the y variable to another number won't affect z.   
Object and arrays are REFERENCE types.  So if one variable is assigned the object referenced stored in another variable, all that gets copied is the pointer to that object.  The 'spread' operator is very helpful in this case to clone or copy all the properties from one object to a new one.  

```javascript
var person = {name: 'jack'};
var personPointer = person;
personPointer.name = 'joe';
console.log(person.name, personPointer.name); // outputs 'joe' 'joe'
```
```javascript
var person = {name: 'jack'};
var clonedPerson = {...person};
clonedPerson.name = 'joe';
console.log(person.name, clonedPerson.name); // outputs 'jack' 'joe'
```
