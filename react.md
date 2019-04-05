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
```javascript
<Person
  title="{this.state.persons[0].name}"
  age="{this.state.persons[0].age}"
  click="{this.switchTitleHandler}"
  />
```

How to parameterise function calls that affect, for example, the state object of a React Component?  
1. ```<button onClick={this.switchTitleHandler.bind(this, 'new value')} /> // bind the class instance and add an additional argument```     
1. write an anonymous function with ES6 arrow syntax (n.b. this is inefficient in coding terms but often preferred!) ```<button onClick={ () => this.switchTitleHandler('new value') }/> // this syntax returns a function which returns a value...```
### Two-way bindings
```javascript
<input onChange="{}" />
```

### Conditional Logic in JSX
`{ if(test == 'some test') ... }` you can't use if statements inside JSX expressions but you can use ternary operators.
However, ternary operators can lead to hard to follow code, a better approach is to put the logic inside the render() function but _outside_ the JSX blocks.  The render() method is called for a number of different trigger reasons, one of which is a state change.  So when assigning variables in the render() method - for subsequent use inside some JSX - then this can be done following a check of the current state.    

## Working with Lists
To use Javascript arrays inside JSX blocks, these arrays must be convered to JSX expressions.  We can do this with `Array.map()`

### Manipulating arrays
When updating state in React, it is bad practise to write code that deals directly with references to the state.  Such as
```javascript
deleteCardHandler = (cardsIndex) => {
  const cards = this.state.cards
  cards.splice(cardsIndex, 1) // this mutates the state via the cards ref
  this.setState({cards: cards})
}
```
better to copy by value (IMMUTABLE), update that and _then_ set state. E.g.

```javascript
deleteCardHandler = (cardsIndex) => {
  const cards = this.state.cards.slice() // no args means new copy of array
  cards.splice(cardsIndex, 1) // IMMUTABLE approach
  this.setState({cards: cards})
}
```
Alternatively use the spread operator

```javascript
deleteCardHandler = (cardsIndex) => {
  const cards = [...this.state.cards] // 'spread' creates a new array
  cards.splice(cardsIndex, 1) // IMMUTABLE approach
  this.setState({cards: cards})
}
```
### `key` Properties on React Components
Key properties help make react virtual DOM comparison quicker - when comparing current and future state changes.  An array index is a _bad_ identifier.  Try and find a unique identifier, such as a database generated key.

## Summary
With react, when conditionally outputting objects, use javascript - same for lists.  Create variables that are checked ahead of building JSX objects to return from render().  For lists, use the map object to return a new area, similarly of JSX objects.  'Map()' provides access to the index as well as the element itself - so use that if necessary to, for example, remove an element.  Add a key to JSX elements to support efficient updates to the react state and re-rendering   

# Redux and (Shared) State Management
How to deal with a scenario such as an 'is authenticated' bit of state, where multiple collaborating components all need that piece of information?  A naive approach is to use routing with query parameters that pass state 'forwards' from page to page, or component to component.  Another approach is long chains of 'props' being sent between components.  We can't use a global javascript variable either, since React's reactivity system doesn't react to changes in arbitrary global variable changes.  Redux does however, enable a global _store_ that can help.

## Redux
React integrates with the 3rd party, Redux library, which keeps a central - a giant js object - that stores the entire application state.  Redux defines the 'process' of how the app state can change.  Because the management of shared state is shifted from inside a component, we no longer rely on internal mutation of `this.state` but depend on the `props` passed into a component by the redux framework.   
It works similar to the following,
1. Redux Actions - dispatched from a React app's own components.  An Action is an info package with a 'type' (or description) and a 'payload'.  These actions don't reach the store, they are just value objects or messages.
1. Redux Reducer(s).  These 'route reducers' (multiple reducers can be merged into one) _are_ connected to the store.  A reducer receives the Action and the old state as input; it returns the new state.  A reducer is a single function that must execute synchronously only (with no side effects and no other operations, such as http requests).  The reducer function replaces the old state in the store and must do so in an immutable fashion.
1. Subscribers to the store.  Components subscribe to store updates to be notified of state changes.

### Add Redux to a Project
1. `npm install redux --save`
1. `import { createStore } from 'redux';`
1. provide a store folder and a reducer.js file, e.g. at store/reducer.js.  Write a reducer function code and export it, e.g.
```javascript
const initialState = {
  counter: 0
}
function reducer(state = initialState, action){
    return {
      ...state,
      counter: state.counter + action.value
    }
}
export default reducer
```
1. in the app.js that is to use the store, `import reducer from './store/reducer';` and then use the reducer in the call to setup the store, `const store = createStore(reducer)`
1. `npm install react-redux --save`
1. `import { Provider } from 'react-redux';`
1. Add a Provider 'wrapper' JSX element to connect the store to the application.  'Provider' expects a store property to be passed in.  E.g.
```javascript
function render () {
    ReactDOM.render(
    (
      <Provider store={store}>
          <App />
      </Provider>
    ), document.getElementById('root'));
}
```
1. Setup subscribers to connect container elements to the store.  Rather than use the programmatic subscribe, we `import { connect } from 'react-redux'` into components that read and write shared application state.  'connect' is a higher order function, which takes arguments ([mapStateToProps], [mapDispatchToProps], [mergeProps], [options]) and returns a function,  The returned function _takes the container component as its function argument_ e.g. `connect(mappings, actions)(TableOfCover)`, e.g.
```javascript
import React, {Component} from 'react'
import {connect} from 'react-redux'
class TableOfCover extends Component {
  // ... internals
}
// specify how 'state managed by redux is mapped to props of this container'.
// we define a function here that can be passed to redux to act as a callback later on, when needed.  The names of fields in the newly created object will result in prop names for this component to use internally
const mapStateToProps = state => {return {ctr: state.counter} }
// actions?
export default connect(mapStateToProps, ??? actions ??? )(TableOfCover)
```
1. How to dispatch actions from components?
Using redux standalone, we can just use `store.dispatch({type: 'ADD', 1})`.  Using it here we have access to the store thanks to use of the `connect()()` higher order function: this allows us to pass a mapping between actions and props (indicating what sort of actions this component or container wants to dispatch).  The dispatch function we can call back on, is essentially a helper function that will eventually call `store.dispatch`.  E.g.
```javascript
import React, {Component} from 'react'
import {connect} from 'react-redux'
class TableOfCover extends Component {
  // ... internals
}
// specify how 'state managed by redux is mapped to props of this container'.
// we define a function here that can be passed to redux to act as a callback later on, when needed.  The names of fields in the newly created object will result in prop names for this component to use internally
const mapStateToProps = state => {return {ctr: state.counter} }
const mapDispatchToProps = dispatch => {
      return {
        onIncrementCounter: () => dispatch({type: 'INCREMEMENT'})
      }
  }
export default connect(mapStateToProps, mapDispatchToProps)(TableOfCover)
// if we only care about just actions of just state, the other argument can be null
// e.g. export default connect(null, mapDispatchToProps)(TableOfCover)
```
