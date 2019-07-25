
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
