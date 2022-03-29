# React
## Components
### Lifecycle hooks UPDATE STATE
Lifecycle for internal state changes
#### Functional Components
Functional components can get to lifecycle sort-of (React v16 onwards) via `useEffect(...)`.  Along with `useState`, `useEffect` is the second most import 'hook'.  `useEffect` combines all the functionality provided by the class-based lifecycle hooks.  Actually `useEffect` is a function available for adding to a functional component.   
```javascript
import React, { useEffect } from 'react'

const myComp = ( props ) => {

  useEffect(() => { // by default takes a function that runs for EVERY render cycle  
    setTimeout(() => {
      alert('something changed')
    }, 1000);
  })

}
```
How to control `useEffect` so that its function only fires when we want it to (rather than for every `componentDidMount` and `componentDidUpdate`)?  How to ensure work is done only once? Supply a second argument - an array of the data items that, when they are updated we want `useEffect`'s code to run:

```javascript
import React, { useEffect } from 'react'

const myComp = ( props ) => {

  useEffect(() => { // by default takes a function that runs for EVERY render cycle  
    setTimeout(() => {
      alert('something changed in props.person or props.anotherMadeUpProp')
    }, 1000);
  }, [props.person, props.anotherMadeUpProp])

}
```
How to control `useEffect` so that its function only fires on `componentDidMount`? Supply a second argument - an empty array **only**:   
```javascript
import React, { useEffect } from 'react'

const myComp = ( props ) => {

  useEffect(() => { // by default takes a function that runs for EVERY render cycle  
    setTimeout(() => {
      alert('componentDidMount...')
    }, 1000);
  }, [])

}
```   
Also see deletion life cycle for how useEffect can be used in tidying up.
