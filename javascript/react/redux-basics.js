/* running with node only - so use the node 'require' syntax */
const redux = require('redux')
const createStore = redux.createStore // get a ref to the function
const initialState = { // doesn't have to be an object but usually is!
  counter: 0
}

// reducer (just a function!!).  State param can be assigned a default value via this es 6 feature function(x = 'some default value', y)
const rootReducer = (state = initialState, action) => {
  if (action.type === 'INC_COUNTER') {
    return {
      ...state,
      counter: ++state.counter
    }
  }
  if (action.type === 'ADD_COUNTER') {
    return {
      ...state,
      counter: state.counter + action.value
    }
  }
  return state // return the old state if neither if statement is entered
}

// store
const store = createStore(rootReducer) // reducer is needed on store creation
console.log('on init: ', store.getState())

// Subscription
// subscribe() takes a function callback - that will be executed whenever state is updated
store.subscribe(() => {
  console.log('subscription : ', store.getState())
})

// action dispatcher - invoked by calling dispatch on the store
// signature: store.dispatch({type: 'INCREMENT_COUNTER', payload}, ) // ({type: ALL_CAPS})
store.dispatch({
  type: 'INC_COUNTER'
})
store.dispatch({
  type: 'ADD_COUNTER',
  value: 10
})
console.log('after actions : ', store.getState())
