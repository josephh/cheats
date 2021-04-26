# C Programming
## Compilation (ref https://www3.ntu.edu.sg/home/ehchua/programming/cpp/gcc_make.html)
### quickstart: end-to-end compile
> gcc HelloWorld.c

:point_up: ...will perform all phases of compilation and output an executable file (named 'a.out' by default, on Linux-based)
### full compilation including output naming
> gcc -W all HelloWorld.c -o hello

:point_up: ...will use the source code and progress through the 4 phases of C program creation; '-W all' outputs all compiler warnings and errors.  The output executable file name is specified.
### end-to-end compile including output naming and preserving intermediate output
> gcc -W all -save-temps HelloWorld.c -o hello

:point_up: ...this will progress through the 4 phases of C program creation but leave all intermediary files in place.
### full compilation including debug instrumentation for use by `gdb`
> gcc HelloWorld.c -g -o outWithDebug
### Preprocessor to include headers and expand macros
> ???

:point_up: ...will create the object file `test.i`
### compile to object code
> gcc -c HelloWorld.c

:point_up: ...will create the object file `HelloWorld.o` (object file names default to the same name)
### compile to Assembly code
> gcc -S HelloWorld.c

:point_up: ...will create the assembly code file `HelloWorld.s`
### convert Assembly code to object code
> gcc HelloWorld.s -o testOut.o

:point_up: ...will create the assembly code file `testOut.o`
### link object file into an executable (using gcc or ld)
> gcc -o outFile testOut.o

:point_up: ...will create the object file `test.o`

**or** use the linker ("ld") to link object code with the library code to produce an executable file
> ld -o hello test.o ...libraries...
### Compilation Stages
1. Pre-processing (ends up in HelloWorld.i)
  * Removal of Comments
  * Expansion of Macros
  * Expansion of the included files (such as stdio.h - gone from ~.i files are now part of source code)
  * Conditional compilation
1. Compilation (ends up in HelloWorld.s)
  * intermediate compiled output file: assembly level instructions.
1. Assembly (ends up in HelloWorld.o)
  *  ~.s is taken as input and turned into ~.o by assembler: machine level instructions (only existing code is converted into machine language)
1. Linking (ends up in final output file)
  * linking of function calls with their definitions (where all these functions are implemented). Adds extra code to our program, e.g handling of command line input.  
