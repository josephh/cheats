
# React Routing

## npm install react-router-dom
To use react routing, it's necessary to wrap everywhere that we'd like to do routing with the 'browser router', e.g. in App.js or index.js.
```javascript
import { BrowserRouter } from 'react-router-dom';
```

## declare routes
E.g. in App.js, import
```javascript
import { Route } from 'react-router-dom';

  render() {
    return (
      <div>
        <Route path="" component={} />
      </div>
    )
  }
```
In React a path="" entry is treated as a prefix, so the following code would result in both the 'welcome' and the 'login' components getting loaded.  
```javascript

  <Route path="/" component={Welcome} />
  <Route path="/login" component={Login} />
```
The first match is used, so we could reorder the route entries, or guarantee the order of route matching through use of the `exact` keyword, or by importing `Switch` from react-router-dom and wrapping the route entries in the <Switch> jsx component.

When components are loaded through a `<Route path="" component={???} />` entry, (i.e. within the scope of routable parts of the application), then those components can use the 'match', 'location' and 'history' props.
These special props **are usually not** available to nested or composed components used by that routed component (if related components needed those they would need those props passing to them manually).  In fact, it is possible to import a 'higher-order' component from react-router-dom, e.g. `import { withRouter } from 'react-router-dom'` then wrap an exported component with that, i.e. `export default withRouter(MyComponent)`.  MyComponent will then get the routing details for the closest matching route.

## amend route
Those routed components (that have access to props: 'match', 'location' and 'history') can trigger, for example
inside a click event handler by calling either `props.history.goBack()` or `props.history.replace('/some/path')`.

## pass values between routes as parameters
use the `push` function to add values into a navigation change. e.g.
```javascript

// client code

  const queryParams  = []
  for (let i in this.state.details) {
    queryParams.push(encodeURIComponent(i) + '=' + encodeURIComponent(this.state.details[i])); // build the url query param entries
  }
  const queryStr = queryParams.join('&')

  this.props.history.push({
    pathname: '/checkout',
    search: '?' + queryStr // arbitrary object
  })
```
```javascript
// consumer code

  // the route receiving the requests will get redrawn (it's not contained in another component, in this case there is no way to route to it without it getting remounted)
  componentDidMount() {
    const query = URLSearchParams(this.props.location.search);
    const details = {};
    for (let param of query.entries) {
      // ["key", "value"] - add the query params back into the new object
      details[param[0]] = +param[1] // use + prefix to convert to a number (if datatype is a number)
    }
    this.setState({details: details});
  }

```

## Nested routes
Nested routes go _inside_ another component
```javascript
import { Route } from 'react-router-dom'
import { AnotherComponent } from './another-component'

  <Route path={this.props.match.url} />

    // routes can also take a component arg
  <Route
    path={this.props.match.path + '/form'}
    component={AnotherComponent}/>
```

### Passing data between pages
Passing data from one context to another can be achieved with a similar approach to the nested path that calls in a `component`.  But rather than `component`, the `render` property can be called, e.g.
```javascript
import { Route } from 'react-router-dom'
import { AnotherComponent } from './another-component'

  // routes can also use the render property, which then allows props to be passed
  <Route
    path={this.props.match.path + '/form'}
    render={() => <AnotherComponent inputVals={this.state.someState} />)}/>
```
Using `ComponentWillMount(...)` allows for state to be set ahead of rendering a component and is a convenient time to handle incoming request parameters.
When using the router to move to included a nested component, the `history` prop will not be available to the nested object.  In this case it's necessary to either use the `withRouter(...)` method, or to include the current props and pass those on with the spread operator to the nested object, i.e.
```javascript
  // routes can also use the render property, which then allows props to be passed
  <Route
    path={this.props.match.path + '/form'}
    render={() => <AnotherComponent inputVals={this.state.someState} {...props}/>)}/>
```
this means that the nested element can then access `this.props.history` and call the pertinent methods (like, `history.push()`) on that.  (This is handy, for example, when doing some navigation at the end of a click event handler, where the event handling happens in the nested component).

## Navigation Links
React router provides navigation links as part of the library.
```javascript
import { NavLink } from 'react-router-dom'; // NavLink assigns a default css class name 'active' to style the active link (this is configurable)
```
The configurable, default NavLink active class is important when using css modules.  Since CSS modules adds additional hash values into class names dynamically at runtime, a CSS class selector like a.active won't work with the dynamically created class name.  `NavLink` provides a property, `activeClassName`= so we can use a classes property assigned via an ES6 module import, e.g.
```javascript
import { NavLink } from 'react-router-dom';
import classes from './AnotherComponent.css'

<NavLink
  to="/somePath"
  activeClassName={classes.active} exact>To Some Path</NavLink>
```
Note, include the keyword 'exact' to avoid **all** links ending up all styled as active (since routes are treated as a prefix so if the root path is ever treated - in code - as active, then that will apply to all paths).
