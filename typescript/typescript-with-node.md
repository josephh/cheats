How to use TypeScript in Node.js
First, you'll need Node.js and TypeScript installed. This article expects that you have a package.json, and that you're using TypeScript installed locally in a directory, not globally. (If you follow our article on installing TypeScript, everything here should work.)

Once TypeScript is installed, you'll find that code written for Node.js won't compile, even when it's correct. For example, here's Node's own sample program, a web server:

const http = require('http');

const hostname = '127.0.0.1';
const port = 3000;

const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  res.end('Hello World');
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});
We recommend using TypeScript's "strict" mode; see our article installing TypeScript for some details on that. In strict mode, this code has three compile errors.

The TypeScript compiler's output is shown below. The first line is our shell command, beginning with the shell prompt $ (which isn't part of the command itself).

$ $(npm bin)/tsc --noImplicitReturns --noFallthroughCasesInSwitch --strict --noUnusedLocals test.ts
test.ts:1:14 - error TS2580: Cannot find name 'require'. Do you need to install type definitions for node? Try `npm i @types/node`.

1 const http = require('http');
               ~~~~~~~

test.ts:6:35 - error TS7006: Parameter 'req' implicitly has an 'any' type.

6 const server = http.createServer((req, res) => {
                                    ~~~

test.ts:6:40 - error TS7006: Parameter 'res' implicitly has an 'any' type.

6 const server = http.createServer((req, res) => {
                                         ~~~


Found 3 errors.
The first error is:

test.ts:1:14 - error TS2580: Cannot find name 'require'. Do you need to install type definitions for node? Try `npm i @types/node`.
As is standard for Node, our require('http') is a CommonJS module import. Without passing any compiler options related to modules, the TypeScript compiler only understands ES6 modules, which look like import * as http from "http".

The remaining errors are:

test.ts:6:35 - error TS7006: Parameter 'req' implicitly has an 'any' type.
test.ts:6:40 - error TS7006: Parameter 'res' implicitly has an 'any' type.
The TypeScript compiler can't find type definitions for the parameters req and res.

We could do the work to write type definitions for Node ourselves by reading the Node documentation, but that would be slow and error-prone. Thankfully, the community already maintains type definitions for Node.

Installing @types/node

The DefinitelyTyped project provides types for many popular libraries, including Node. They publish a package @types/node that includes type definitions for all of Node. You can install those types with:

npm install @types/node
Match your @types/node version to your Node version

Node development is ongoing, so there are multiple major versions of Node available at any one time, sometimes with significant API differences. That means that types for one version will be incorrect for other versions.

Node isn't installed with npm, so npm can't keep the versions of node and @types/node in sync for us. Unfortunately, we have to do that ourselves.

The rule is: install the version of @types/node that matches your Node major version. You can find your Node version with node --version. For example, our Node version as we're writing this is v12.15.0. We'll install our types as:

npm install @types/node@12
If you're reading this in the future, your Node version may be 14 or 16 or higher, so make sure to check! Installing the wrong version of @types/node can cause very confusing errors.

Using types from @types/node

TypeScript will see every file under node_modules/@types automatically. We only need to change the import style from require to an import for the compiler to find the types in the http package.

import * as http from "http";

const hostname = '127.0.0.1';
const port = 3000;

const server = http.createServer((req, res) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  res.end('Hello World');
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});
Now this compiles with no errors! TypeScript can see the type of createServer. The compiler now knows that req is an http.IncomingMessage and res is an http.ServerResponse. It also knows that we're calling methods that actually exist, and with the correct arguments, like res.setHeader('Content-Type', 'text/plain').

Be careful with type definitions

Some packages ship with TypeScript types maintained by the package authors. In those cases, a well-maintained package will usually have well-maintained types. When using packages that don't publish their own types, you'll find yourself relying on third-party types from @types.

Popular, well-maintained packages like Node and React almost always have high-quality types, even if they're third-party. Packages with few users are more of a gamble. In general, the fewer users a package has, the more likely you are to find bugs in the types, just like you're more likely to find bugs in the package itself.

Execute Program's server component is written in Node, so we use @types/node extensively, but we've never found a bug in it! However, we have found bugs in other third-party types.

Managing type definitions is one of the very real costs of using TypeScript. You may want to read more about this problem and other problems that exist with TypeScript.
