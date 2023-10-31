# Unleash Flag - implement & test
1. what is unleash and why do we need it?   
   1. client-server
   1. client can read only  
1. implementation approach  
1. tests fail, why?!
1. `@DirtiesContext` is needed to guarantee the state of the Spring container
1. awkward to solve because
   1. tests pass in local - gradle cache/ gradle daemons
   1. Other apps e.g. Fluent mediator also using fake unleash - beware copy n paste!
