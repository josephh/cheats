
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
To use Javascript arrays inside JSX blocks, these arrays must be converted to JSX expressions.  We can do this with `Array.map()`

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
