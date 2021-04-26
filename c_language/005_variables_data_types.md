# C Programming
## Variables and Data Types
### Naming Rules
1. begin with letter or underscore, plus any combination of letters, underscores or digits 0-9
1. can't use reserved words  

### Declaration
1. syntax = type specifier, followed by identifier.
1. multiple variables of same type can appear on same line, separated by commas.
1. Variables *must* be declared in C before usage.

### Assignment  
1. `=` operator assigns a value to a variable.

### Initialisation  
1. can be done on same line, or subsequent lines.  

## Data Types  
### Numeric Data Types  
* `int`  // may be up to 32 bits (4 bytes)
Integral, signed values only (whole numbers, positive, zero or negative).  `int`s can be assigned hexadecimal (base 16) notation/ representations of number, identified by the prefix `0x...`.  E.g. `int rgbColor = 0xFFEFOD`.   
* `float` // floating point (decimal place).  Can also be represented in scientific notation, e.g. `1.7e4` being 1.7 x 10 to the power of 4 ('e' meaning exponent)
* `double` // twice the precision of 'float' (usually 8 bytes (64 bits)).  All floating point constants are stored considered by the C compiler to be of type 'double'.  To explicitly indicate a float, append 'f' or 'F' to the number, e.g. `1.24F`
* C offers 3 additional numeric qualifiers, to go on ints, floats and doubles (and these can also be used as data types definitions on their own): 'short', 'long' and 'unsigned'.  This allows program optimisations.
* `_Bool`.  0 is stored to represent false, 1 true.   From C99 onwards, the `<stdbool.h>` library means you can avoid the C89 standard `_Bool` type and avoid the 0 and 1 values, and instead use `bool myBoolean = true;`.  The header file must be included in the   
### Enum and Char Data Types  
* `enum`.  Syntax = `enum _variableName_ {_e_, _e1_, _e2_, _en_}`. e.g.
> enum month {January, February, March};  

compiler actually stores enum constants as an array index.  Enum definitions can also have a numeric value assigned to it at creation time.  E.g.
> enum direction {up, down, left = 10, right};


:point_up: This produces the slightly unexpected effect that up has the value 0 and down has the value 1 - their array indices - left has the explicitly assigned value of 10 :warning: but **right has the value 11, since it follows left (10)!**     
* `char` represents a **single** character (always in single quotes - double quotes in C means a string; a string can't be assigned to a char).  If a number is assigned to a char, e.g. `char c = 65;` then the ASCII table is used :warning: but this is  not great coding style!.
* C escape sequences:   
char c = '\n'; // new line


## Data types
### Basic types
arithmetic
1. integer
|type|storage|size|
|--------|:---------:|:-----:|
|char|1 byte|-128 to 127 or 0 to 255|
|unsigned char|1 byte|0 to 255|
|signed char|1 byte|-128 to 127|
|int|2 or 4 bytes|-32,768 to 32,767 or -2,147,483,648 to 2,147,483,647|
|unsigned int|2 or 4 bytes|0 to 65,535 or 0 to 4,294,967,295|
|short|2 bytes|-32,768 to 32,767|
|unsigned short|2 bytes|0 to 65,535|
|long|4 bytes|-2,147,483,648 to 2,147,483,647|
|unsigned long|4 bytes|0 to 4,294,967,295|  

`sizeof(__type__ or __variable__)` operator outputs a **value in bytes**.  
1. floating-point
|type|storage size|value range|precision|
|--------|:---------:|:-----:|:-----:|
|float|4 bytes|1.2E-38 to 3.4E+38|6 decimal places|
|double|8 bytes|2.3E-308 to 1.7E+308|15 decimal places|
|long double|10 bytes|3.4E-4932 to 1.1E+4932|19 decimal places|

### Enumerated types
1. discrete, arithmetic
### `void`
_no value available_
### Derived types
1. pointers
1. array(s)
1. struct(s)
1. function(s)

## Format specifiers
### Printf
'%x' - percentage specifier indicates a variable and its data type
`%i` integer
`%f` float
`%e` double
Format specifiers can be more complicated, e.g. and include numbers of decimal places,
```C
float myFloat  = 12.45e6;
printf("floating var = %.2f\n", myFloat); // 2 decimal place precision in output
```
