
# React with Redux and (Shared) State Management
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
### Redux Subscribers
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
### Redux Actions
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
1. Add action types to a constants file, e.g. store/actions.js:
```javascript
export const INCREMENT = 'INCREMENT';
export const DECREMENT = 'DECREMENT';
export const ADD = 'ADD';
```
Import the actions and use those in both the reducer and components, e.g.
```javascript
import * as actionType from 'store/actions'
// ...
const reducer = ( state = initialState, action ) => {
    switch ( actionType ) {
      case actionTypes.DECREMENT:
      // ... implementation ...
    }
}
```
1. When updating an array element of the redux state (or any state), prefer `concat()` - rather than `push()` for adding to an array.  The former is immutable, returning a new array, rather than touching the existing reference value.
For a simple immutable remove of an array element, the array could be cloned (assigned to a new array) with the Array.splice(...) function.  Alternatively Array.filter(...) can be used to provide a new filter and, of course, this is preferred.

### Multiple Reducers
1. Separate reducers are merged into a single reducer, so state keys managed by one reducer can be available from another.  Code amalgamating several reducers at the same time should use the redux package, `import { createStore, combineReducers } from 'redux';` and set a rootReducer to hold the result of the combining.  When combining reducers, each one is assigned to a property key in the object passed into the combined reducers.
Reducers **cannot** access the 'global' state - i.e. state managed inside a different reducer - since they are not 'wired-up' to the store.  If we need access in one reducer to some state value from another reducer, then that value should be passed to the reducer requiring the state value via an action's payload.  As an example use case from the tutorial, there is a counter reducer and a store reducer.  The counter reducer maintains the value of the counter and handles actions to add, subtract, increment, decrement. The store reducer does the save, delete, update of the results.   
