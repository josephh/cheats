
# React
## Functional Components
Lifecycle hooks like 'shouldComponentUpdate' are only available for use in class-based component.  So how to optimise functional components?

If a component only needs to update when internal prop values change, then the exported component can be 'wrapped' using React.memo; (memoisation acting like  a cache of the props, so that no rendering is triggered, unless the props change via memo)
```javascript
import React from 'react';  
class NewCatch extends Component {...}
export default React.memo(NewCatch)
```  
Note, not every component should always check whether they should update - either via memo() or shouldComponentUp - if that sort of 'guard' is added everywhere it will result in a thick wodge of additional code execution.
