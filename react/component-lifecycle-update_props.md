#React
## Components
### Lifecycle hooks UPDATE PROPS
1. `static getDerivedStateFromProps(props, state)` // best to avoid this
1. `shouldComponentUpdate(nextProps, nextState)` // this is where you can decide whether or not to continue with render/re-rendering of components.  quite handy as it can improve performance by avoiding unnecessary update cycles.  :warning: Note this method must return a boolean value.  Undefined is falsey so blocks an update.
1. `render()` // : producing virtual DOM...will also update child component props // followed by all child component code being re-rendered if props or state have changed
1. `getSnapshotBeforeUpdate(prevProps, prevState)` // generally not needed; helpful for operations such as returning to a previous scroll position on a web page, after some redrawing of visual components
1. `componentDidUpdate()` // this is OK now for operations with potential side-effect, e.g. calls to external http endpoints and will probably be the most used lifecycle hook.  however :warning: beware danger of infinite loops.

### shouldComponentUpdate
If a component is part of a component 'tree', then it will get re-rendered as part of general rendering, e.g. in App.js's main render() function.  To avoid unnecessary work we can include the lifecycle method `shouldComponentUpdate`:

```javascript
import React, { useEffect } from 'react'

const myComp = ( props ) => {

  shouldComponentUpdate(nextProps, nextState) {
    console.log('[myComp] shouldComponentUpdate')
    if(nextProps.someProp !=== this.props.someProp) {
      return true; // only re-render if something has changed
    }
    else {
      return false;
    }
  }

}
```    
:warning: Beware comparing pointers to in-memory objects and arrays, they will be equal (but a deep equals comparison is needed!)
