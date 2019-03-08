#Javascript Nextgen Language Features
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
const newArray = [...oldArray, 'new item 1', '2', '3'] an old array is added to a new one, with additional items added to it   
```
```javascript
const newObject = {...oldObject, foo: 'bar', goo: 'far'} an old object gets additional properties (existing properties with matching keys are replaced).   
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
Object and arrays are references types.  So if one variable is assigned the object referenced stored in another variable, all that gets copied is the pointer to that object.  The 'spread' operator is very helpful in this case to clone or copy all the properties from one object to a new one.  

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
# React
## JSX Restrictions
* Can't use 'class' inline in JSX - since it's actually Javascript - prefer className
* JSX elements are meant to have a single root element
## Components
### props
`props` are set from outside a component instance - and passed to it as parameters.
React will always pass a `props` parameter into the code that instantiates a react component.  This can easily be passed from JSX the tag corresponding to the react component, e.g. <NavigationCard title="Upload a Catch">  
### props.children
{props.children}
This is a way to push structured html into a component.   We can access all the child elements of a component - the value between the JSX Tags, e.g. <NavigationCard title="Upload a Catch">This is the value we are interested in between but it can be any type of content, including more markup</NavigationCard>
### state
`state` is a field in React's 'Component' class.
:alert react "hooks" let's u you use state in 'function' declared components (typically you need a class extending Component to do that).
### pass 'methods' as props
For example, if a click handler is defined in a class, it can be passed into another a contained component to have some effect back on the parent container.  This can be helpful to keep components dumb or stateless (i.e. presentation only) and restrict state mutation to discrete areas of the app.  
1. ```javascript
<Person
  title="{this.state.persons[0].name}"
  age="{this.state.persons[0].age}"
  click="{this.switchTitleHandler}"
  />
```

How to parameterise function calls that affect, for example, the state object of a React Component?  
1. ```<button onClick={this.switchTitleHandler.bind(this, 'new value')} /> // bind the class instance and add an additional argument```     
1. write an anonymous function with ES6 arrow syntax (n.b. this is inefficient in coding terms) ```<button onClick={ () => this.switchTitleHandler('new value') }/> // this syntax returns a function which returns a value...```
### Two-way bindings
```javascript
<input onChange="{}" />
```

### Conditional Logic in JSX
`{ if(test == 'some test') ... }` you can't use if statements inside JSX expressions but you can use ternary operators.
