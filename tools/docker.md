# Docker cheat sheet

## Build a docker image
## Build applying the instructions in a local dockerfile, incorporating everything in the local folder
docker build -t author:0.9.1 . (the 'dot' is needed to specify the local Dockerfile)

## Debugging
> The exit code from docker run gives information about why the container failed to run or why it exited. When docker run exits with a non-zero code, the exit codes follow the chroot standard, see https://docs.docker.com/edge/engine/reference/run/#exit-status
* docker info
* docker logs aem

### Start a container with tty emulation (overriding CMD instruction to invoke bash)
docker run --name aem -p 4502:4502 -p 9200:9200 -p 8081:8081 -it  aemtest /bin/bash
### Start a container with tty emulation as root (`-u 0`) (overriding CMD instruction to invoke bash)
docker run --name aem -u 0 -p 4502:4502 -p 9200:9200 -p 8081:8081 -it  aemtest /bin/bash

## Run
• `-P` will publish all the exposed container ports to random ports on the Docker host.  Next you can see the ports by running the `docker port ~container~` command.

### Attach
e.g. `docker exec -it aem bash`

## Networks
which networks are up and available?
> docker network ls

what about the specifics of a network?
> docker network inspect aemdockercompose_webnet

how to join a container to a network?
> docker network connect aemdockercompose_webnet aem

### Network Debugging
Is one container able to talk to another?  E.g. can aem container reach the elatic one over http on port 9200?
> wget http://aemdockercompose_elastic_1:9200/cms -d --header='authorization: Basic ZWxhc3RpYzpjaGFuZ2VtZQ=='

### Debug java process running inside Docker
Dockerfile - amend run command to expose remote debugger:
```
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app.jar"]
```
Add an additional mapped docker container port, do that in the docker run command, or add to docker compose, e.g.
```
... # for the service/ docker container with the java code we'd like to debug...
services:
  inventory-fluent-integration:
    ports:
      - '9080:8080'
      - '5005:5005'  # debug
```
...then run remote debug session via Intellij, via port 5005.
