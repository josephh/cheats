# S:O:L:I:D
1. S = Single responsibility.  Doesn't quite mean 1 function per class but it means just do one thing.  The few things a class is expected to do, the less likely it will be to change a lot (and need looking after).
1. O = Open-closed (Bertrand Meyer "OO Software Construction" 1988).  Classes should be "open for extension but closed for modfification".  Meyer proposed inheritance but unfortunately that has proven over time to result in tight-coupling, if subclasses depend on the implementation internals of their parents.  Prefer interfaces.  How to "swap" in a preferred interface implementation without rewriting code?  : you can use dependency injection, reflection or the service loader API to replace the instantiation of a specific implementation class.
1. L = Liskov principle (Barbara Liskov's conference keynote “Data abstraction” 1987).  "The principle defines that objects of a superclass shall be replaceable with objects of its subclasses without breaking the application".   Unfortunately, there is no easy way to enforce this principle. The compiler only checks the structural rules defined by the Java language, but it can’t enforce a specific behavior..   Don’t implement any stricter validation rules on input parameters than implemented by the parent class.
Apply at the least the same rules to all output parameters as applied by the parent class.
1. I = Interface segregation.  Robert C Martin, “Clients should not be forced to depend upon interfaces that they do not use.”  Reduce the side effects and frequency of required changes by splitting the software into multiple, independent parts.
Rather than having a large(r) interface that has abstract methods that some implementations use and some that certain implementing classes don't; keep interfaces as limited in scope as practically feasible.  Note this doesn't necessarily fix the problem of interfaces needing to change signature... but to avoid breaking changes the new required method signature could be addressed via a new method, rather than a change to existing.
1. D = Dependency inversion (aka Inversion of control).
 1. High-level modules should not depend on low-level modules. Both should depend on abstractions.
 1. Abstractions should not depend on details. Details should depend on abstractions.
also,
 1. the high-level module depends on the abstraction, and
 1. the low-level depends on the same abstraction.
 SLF4J?   Just provides the org.slf4j.impl.StaticLoggerBinder - a class in its namespace that isn't included in its jar file.  An implementation like `logback` includes the class and, courtesy of a service loader, the functionality magically appears.  D.I is used here: the outer client of the logging doesn't depend directly on logback; with interfaces the JVM can dynamically swap in the desired implementation without having to statically compile it ahead of runtime.  Delegation pattern (or forwarding to a composed instance) is used to call the implementing logger classes.    


OOP main 4 principles are,
1. Polymorphism ; method overloading (methods with same name but different parameter types and arity) and method overriding (a subclass replaces the behaviour of the thing in the superclass (i.e. methods with the same name)).
1. Inheritance ; reuse some or all of a superclass to minimise duplicating code and make it more maintainable.
1. Encapsulation ; keep fields private within a class as a protective barrier, reusability is more likely if something's state is harder to change from the outside.
1. Abstraction ; make things simpler by pulling 'up' the essentials (consider something theoretically or separately from (something else). extract or remove (something). ).  Turning the tv on doesn't require you to know the internals of how a tele works.
 1. Composition is including other classes to provide behaviour, rather than trying to get lots of behaviours into a class through inheritance
