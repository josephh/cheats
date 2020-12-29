# C Programming
## stdio library
* Part of the C standard core libraries
### printf
output text
### scanf
reads from input stream (3 'streams' in C: standard error, standard in and standard out), scanning the input based on a user-provided format `%s`, `%d`, `%c` etc.
1. read from standard input
1. read from file // if the input stream is a file read
stdin is, by default, keyboard input read as text.
different to printf, rather than variables, constants and expressions as its arguments, scanf uses pointers to variables (which makes sense as we need plenty of allocated to store the input into).
Rules,
1. `scanf` returns the number of items it successfully reads.
1. if using `scanf` to read a value for a **basic** variable type, prepend the variable with `&` to access the pointer's location.
1. if using `scanf` to read input into an array, **do not** prepend the variable with `&`.
1. `scanf` uses whitespaces (space, tab, newlines) to break input up into different fields
