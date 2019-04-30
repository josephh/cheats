# React - Styling Components

## How to style a component?
### Inline in JSX
We can apply style in a component via inline JSX, as follows, however, we can't use pseudo-selectors with this approach.  It is advantageous, in the sense that styling is only applied to the component, doesn't 'pollute' other components and can't be affected by changes to styling in other components (other than global styles).

```javascript

class CatchData extends Component {

  const style = {
      margin: '20px auto',
      width: '80%',
      'text-align': 'center',
      'box-shadow': '0 2px 3px #ccc',
      border: '1px solid #eee',
      padding: '10px',
      'box-sizing': 'border-box'
  }

  render() {
    // ...snippet
    return (
      <div style={style}>
        <h4> Upload your Catch Data </h4> { form }
      </div>
    );
  }

}
```
### In component-scoped CSS
### How to change style dynamically?
Use the component state to drive styling, e.g.
```javascript
if(form.isValid) style.backgroundColor = 'red';
```
Use some other indicator, e.g. number of elements in an array, to adjust the state.  Add classes to the component's CSS file, e.g. `.red : {}; .blue : {}; .bold: {};` and match these with an array of names in the javascript joined with an empty space (to turn it into a css class list that can be assigned via the javascript/JSX), e.g. `let classes = ['red', 'bold'].join(' ')`.  Bind the classes concatenated string to a JSX element using the curly brace syntax, e.g. `<p className={classes}>Some Element Text</p>`.
Improve the coding approach with some conditional logic, e.g.
```javascript
let classes = []
if(this.state.persons.length <= 2) {
  classes.push('red'); // classes now [red]
}
if(this.state.persons.length <= 1) {
  classes.push('bold'); // classes now [red, bold]
}
if(this.state.persons.length > 2) {
  classes.push('green');
}
```
### Radium
It is possible to use pseudo styles, e.g. `button:hover` and media queries, e.g. `@media (min-width: 1044px) { ... }` with the third party library 'Radium'.  Add the library to your component, import it where you wish to use it and wrap the component with the library, e.g
```javascript
import {Component} from 'react'
import Radium from 'radium'

class MyComponent extends Component {

  const style = {
    backgroundColor: 'green',
    color: 'white'
    // this is how you use Radium - all valid pseudo-selectors are supporter
    ':hover': {
      backgroundColor: 'lightgreen',
      color: 'black'
    }
  }

  render() {
    // ...
  }

  // tweak the pseudo-selector style in some conditional way
  if(state.buttonIsClicked) {
    style[':hover'] = {
      backgroundColor: 'red',
      color: 'white'
    }
  }

}

export default Radium(MyComponent)
```
Radium also lets us write stuff like the following, `const style = { '@media (min-width: 500px)': { width: '450px' } }` but to do this, you must `import { StyleRoot } from 'radium' and wrap the **whole application** - i.e. App.js - in it 
