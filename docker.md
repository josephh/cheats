# Docker cheat sheet -  

## Debugging
> The exit code from docker run gives information about why the container failed to run or why it exited. When docker run exits with a non-zero code, the exit codes follow the chroot standard, see https://docs.docker.com/edge/engine/reference/run/#exit-status
* docker info
* docker logs aem

### Start a container with tty emulation (overriding CMD instruction to invoke bash)
docker run --name aem -p 4502:4502 -p 9200:9200 -p 8081:8081 -it  aemtest /bin/bash
### Start a container with tty emulation as root (`-u 0`) (overriding CMD instruction to invoke bash)
docker run --name aem -u 0 -p 4502:4502 -p 9200:9200 -p 8081:8081 -it  aemtest /bin/bash

## Run
• `-P` will publish all the exposed container ports to random ports on the Docker host.  Mext you can see the ports by running the `docker port ~container~` command.
