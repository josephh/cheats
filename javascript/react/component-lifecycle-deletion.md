# React
## Components
### Lifecycle hooks DELETION
1. Deletion
'Clean-up' work may be required, for example, when a component that setup lots of event listeners is being removed, or that perhaps had a connection to a server somewhere.
1. `componentWillUnmount()`
1. `useEffect()`
With useEffect we can **return** a function.  The anonymous returned function will run before the main useEffect() function runs but AFTER the first render cycle.  The presence - or lack of - second empty array argument determines whether it runs on: destroy (empty array), on every update cycle (no argument), or second argument array of data values to watch and trigger the clean up function when they change.
```javascript
import React, { useEffect } from 'react'

const myComp = ( props ) => {

  useEffect(() => { // by default takes a function that runs for EVERY render cycle  
    const timer = setTimeout(() => {
      alert('component did mount')
    }, 1000);

    return () => {
      clearTimeout(timer) // can cleanup timer as well!
      console.log('clean up work in useEffect')
    }

  }, [])

}
```    
