
# React
## Components
Where a component doesn't need to maintain any state, those can be written as functional components.  I.e. rather than writing  
```javascript
import React from 'react';  
class NewCatch extends Component {...}
export default NewCatch
```
we can write
```javascript
import React from 'react';  
const NewCatch = ( props ) => {...}
export default NewCatch
```  
Note, through use of React Hooks, we can also write components as function declarations, that also do internal state management but it is a common coding practise to use class-based components for state management and functional components for presentation-only components.
```javascript
import React from 'react';
// with es6 we can omit the 'return' keyword, provided the parentheses to hold the jsx begins on the same line
const persons = (props) => (
  // jsx goes here
)
```
### JSX root elements
React 16 expects JSX blocks to have a 'root' node, typically a div. However it is OK to return a list from a render() method, e.g.
```javascript
import React from 'react';
const persons = (props) => props.persons.map( ( person, index) =>
  return <Person ...
    // spec of each person in the list
  >
)
export default persons
```
### Class-based vs functional components
#### class-based
```javascript
import React, {Component} from 'react';
class Y extends Component {
  // operations...
}
```
* access state via `setState(...)`
* In class-based components, State and Props are 'properties' of the component class object, so must be accessed via the `this` pointer
#### functional
```javascript
import React, {Component} from 'react';
const Y = props => {
  // operations...
}
```
* needs react hooks (since v16) to get to `useState(...)`
* props is passed to functional components as a normal argument so is used as-is (no point to the `this` pointer)
* NO ACCESS to lifecycle hooks
##### Lifecycle hooks CREATION
1. Creation
AVOID SIDE-EFFECTS in these methods, which may block rendering
`constructor(props)` // NOT a react feature default ES6
Constructor needs to call `super(props)` if added.
(Setting state in the constructor is an explicit coding approach to what now happens in React automatically, when `state = ...` is used in the body of the class).
1. `static getDerivedStateFromProps(props, state)` // since react v16.3. When props change for a class-based component, in edge cases you may want to trigger updating some internal state as a result.
`static` keyword is needed!
1. `render()` // return JSX, INCLUDING rendering all child components and their life-cycle hooks executing...
1. `componentDidMount()` //...executes ONLY ONCE render() and rendering of all child components completes!  componentDidMount can be used to trigger side-effects, e.g. call out to other services but should NOT be used to do SYNCHRONOUS setting of state
Note, `componentWillMount()` was previously available (and still supported) but discouraged from use because of it was easy to use incorrectly.
##### Lifecycle hooks UPDATE
1. `static getDerivedStateFromProps(props, state)` // best to avoid this
1. `shouldComponentUpdate(nextProps, nextState)` // this is where you can decide whether or not to continue with render/re-rendering of components.  quite handy as it can improve performance by avoiding unnecessary update cycles
1. `render()` // : producing virtual DOM...
1. update child component props // followed by all child component code being re-rendered if props or state have changed
1. `getSnapshotBeforeUpdate(prevProps, prevState)` // generally not needed; helpful for operations such as returning to a previous scroll position on a web page, after some redrawing of visual components
1. `componentDidUpdate()` // this is OK now for operations with potential side-effect, e.g. calls to external http endpoints.  however :warning: beware danger of infinite loops
