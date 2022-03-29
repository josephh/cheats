
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
    // spec of each person in the list˜
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
