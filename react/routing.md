
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
```javascript
import { Route } from 'react-router-dom'

  <Route path={this.props.match.url} // or alternatively path={this.props.path + '/contact-data'}
```
