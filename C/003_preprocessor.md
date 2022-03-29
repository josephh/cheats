# C Programming
## Preprocessor
* Part of the C compilation process...
* Preprocessor statements are often at the top of a code module (e.g. `#include <stdio.h>`) but they can appear _anywhere in code!_.  Analogous to Java's `import` keyword.
* Preprocessor directive must be _first, non-whitespace char on line_.
* Preprocessor can,
  * create constants using `#define`
  * `#include`
  * `#ifdef`, `#endif`, `#else`, `#ifndef`

### #include preprocessor directive
Including header files - which must be referenced in a case-sensitive way - can be done in 2 different ways,
> #include <stdio.h>
> #include "stdio.h"

All C standard editions will come with a common set of base libraries, such as stdio.  
It is best practise to use conditional preprocessor directives `#ifndef` and `#define` to avoid including the same library twice.
### Header files
Header files typically are typically made up of function prototypes (implementation going in source code `~.c` files) but are also made up of comments, typedefs, #define statements, structure declarations.  
