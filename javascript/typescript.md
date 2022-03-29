# Typescript basics
1. types come after a colon ':'. e.g.s
```let sum: number = 1 + 1;```
```let anything: string = 'any' + 'thing';```
```let sum: number = 'any' + 'thing' // type error: Type 'string' is not assignable to type 'number' ```
1. Types always have to match.
```let b: boolean = true || false; // true```
```let b: boolean = 1 + 1; // type error```
