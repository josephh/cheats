How to install TypeScript
Installing npm

The easiest way to install TypeScript is with the Node Package Manager, npm.

npm comes packaged with Node.js. You can install both from the Node.js website. (If you're already working in JavaScript, you may have Node and npm installed already.)

Node.js is also available in your package manager of choice. On Mac OSX, if you're using the Homebrew package manager, you can install Node.js with brew install node.

Installing TypeScript using npm

To create a new project using TypeScript, run npm init inside your project folder to create a package.json file. Then use npm install typescript to install TypeScript in that folder only. That way, you can have many projects using different versions of TypeScript.

Using the TypeScript compiler

The TypeScript compiler is named tsc. It takes as input a file containing TypeScript code, and outputs a file containing JavaScript code. Running the command

$(npm bin)/tsc myCode.ts
will check the types of your program. If it's successful, it will then create a file in the same folder called myCode.js. You can then run that file as JavaScript using Node.js by typing node myCode.js.

Our TypeScript compiler options

You can see a complete list of TypeScript compiler options by running $(npm bin)/tsc --help. Here are the relevant options we use in our TypeScript course. We also use these same options to develop Execute Program itself.

--strict. This enables all strict type checking options, including --noImplicityAny and several others.
--noImplicitReturns. This reports an error when a function has a declared return type, but some code paths don't return a value. Without this option, your functions may return undefined values without you realizing it.
--noFallthroughCasesInSwitch. With this option, the compiler will tell you when you accidentally write a case statement with no break or return.
--noUnusedLocals. This options prevents unused local variables, which are usually a mistake.
All together, we recommend that you compile TypeScript code with

$(npm bin)/tsc --noImplicitReturns --noFallthroughCasesInSwitch --strict --noUnusedLocals myCode.ts
When you move on to building more complex applications in TypeScript, you can move those options into your tsconfig.json file.

Using a TypeScript command prompt

Often, you'll need to check the result of a single expression. It's most convenient to use a command prompt, where you can type an expression and get immediate output:

> 3 * 6
18
For that, we recommend ts-node. You can install it with npm install ts-node.

Using a TypeScript linter

We strongly recommend you use a linter for TypeScript. A linter will catch several kinds of errors that the compiler can't see. Also, it can keep your code stylistically consistent. We recommend typescript-eslint.

Back to TypeScript course
