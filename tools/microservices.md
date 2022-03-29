Hadoop
'Distributed computing of large datasets across clusters of computers with simple programming model'

Microservices - foster incremental development = GOOD!
Force out-of-process service boundaries - microservices communicate across lightweight mechanisms (like http)
organised around business processes.
Teams at BG are split into a sometimes confusing matrix of technology and business concern, e.g. single page app (front end UI/ single page App) aligned to - coarse - online account management; - journey specific - home move; functionality - get a quote.
1. javascript
1. CMS
1. server-side
1. database
-> this siloed approach builds in waste (policy-driven waste: complexity, economies of scale, separate decision-making from work, wishful thinking, technical debt); partitions between teams bakes-in delay; batch-and-queue mentality = get as much work together as possible to make max use of the appropriate speciailist's time.  We are over preoccupied with full utilisation so end up with a work management approach that struggles to absorb variety or ad hoc, necessary or desirable changes or enhancements.
Lean software promotes 'economy of flow' over 'economy of scale'. Fred Brooks 'no silver bullet'.

## Microservices at BG
Some good principles are compromised.
E.g. the 'team' purportedly has a dev ops culture.  I.e. everyone knows the physical architecture and has the necessary skills to support and enhance the CI environment and scripts, everyone can monitor the status of our services, deploy and suspend services.  This is not the case in practise unfortunately.  Historic working arrangements and active contracts with suppliers (service level agreeements, production support teams) means 'low-value' work is sent offshore.  Team members with experience in sysadmin 'own' the configuration work and squirrel away that information.  The result is ambiguity and correcting problems becomes harder.


## Data structures
- Array.  Collections of items that can be located by index
- Set.  Collection of values, unordered and with no repeating values (a 'finite set').
- List.  Collection or ordered, countable values.  (Linked lists are linearly ordered but the ordered items and not necessarily placed side-by-side in memory (instead pointers to memory location provide the linkage).  Linked lists can be used to implement other abstract data types, such as lists, queues, stacks)
- Stack. Last in first out LIFO
- Queue. First in first out FIFO (supporting operations: enqueue, dequeue and peek (peek returns the next value to be dequeued without dequeuing it)).  The BlockingQueue interface in the Java util concurrency package has a method  `get` that blocks when the queue is empty and a `put` method that blocks when the queue is full.  Similarly the SynchronousQueue interface has a capacity of zero - items can only be pushed to the queue when another thread tries to immediately take it from the take.

BDD - aka specification by example - stemming and refining practises in TDD
'given...when...then'
It's a conceptual approach - requiring no particular tools

British gas cloud
vagrant (manage virtual environments)/ nomad/ virtual box/ docker
docker containers
docker compose = multicontainer

Microservice architecture
Netflix Zuul - proxy - provides unified interface for consumers - edge server
Consul - service registration and discovery
uaa - token services/ authentication and authorisation
