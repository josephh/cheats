# React
## Components
### Lifecycle hooks CREATION
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
