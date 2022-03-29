REACT WILL RE-RENDER IF A PROP OR STATE CHANGES!

# lifecycle

Mounting
--------
constructor()
getDerivedStateFromProps()  
this.props and this.state in a class.  {useState} hook.  `const [color, setColor] = useState("");` takes an initial state value and returns, (1) the current state and (2) a function to update that piece of state.  useState triggers a re-render.
render()  
{useEffect} hook.  runs on every render UNLESS an array is supplied as a second argument. (empty array means run on first render only).  Include a return function at the end if needed - e.g. to use as a callback following the effect being applied (preventing memory leaks etc)
componentDidMount()

Updating
--------
getDerivedStateFromProps()
shouldComponentUpdate()
render()
getSnapshotBeforeUpdate()
componentDidUpdate()

Unmounting
----------
componentWillUnmount

HOOKS!
Hooks can only be called inside React function components.
Hooks can only be called at the top level of a component.
Hooks cannot be conditional

{useContext} - wrap a ContextProvider to do global state management
{useRef} - to get to the state before the most recent render
{useReducer} - for keeping track of multiple pieces of state that rely on complex logic.  Pass a reducer function and initial state when calling useReducer.  Get an array of 2 elements returned: 'current state' and a 'dispatch' method.  Use the dispatch method when you want to update the state managed by the reducer.
