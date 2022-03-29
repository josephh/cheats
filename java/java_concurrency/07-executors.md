# Java Concurrency in Practise
## Notes from Brian Goetz textbook
## 07. Executors
## Task Execution (Chapter 6)
The notion of tasks simplifies program execution, facilitates error recovery and provides natural transaction boundaries.  For concurrency, tasks provide a natural structure for parallelizing work.  This is particularly helpful for server design.  Web servers, mail servers, ftp servers, EJB containers and database servers all accept requests via network connections from remote clients.
### Characteristics of servers
Servers should exhibit good throughput and good responsiveness under normal load, when overloaded they should degrade gracefully.  Good tasks boundaries with a sensible task execution policy are important for server design and implementation.  Using individual requests as a base 'unit' for tasks or task boundaries allow for independence of task handling and appropriate task sizing.
#### Single threaded web server -- poor responsiveness and throughput
[...can only handle one request at a time](https://github.com/josephh/jcip_executor/blob/master/src/main/java/SingleThreadWebServer.java) :confused: (this might suffice if the server only served one client and could complete processing and return immediately; but few real world servers can work in this way)
#### Thread per request server -- poor resource management
[...risks too many threads causing performance degradation and eventual out of memory](https://github.com/josephh/jcip_executor/blob/master/src/main/java/ExplicitThreadPerTask.java) :neutral_face: (main problem here is nothing puts a limit on the number of tasks created, other than the rate at which clients can throw requests at the server)
#### Executor framework
_bounded queues_ help prevent an overloaded application from running out of memory.  _Thread pools_ offer the same benefit for thread management - see `java.util.concurrent`'s thread pool implementation a.k.a **Executor framework**.  In this framework the primary abstraction for task execution is not Thread but the Executor interface's `void execute(Runnable command);`.  Executor is based on the producer-consumer pattern (producers submit tasks (units of work to be done), threads that do the work are the consumers).  So this abstraction is a nice approach to decoupling `task submission` from `task execution`.  
