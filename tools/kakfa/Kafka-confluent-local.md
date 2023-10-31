# Confluent local setup
See the following web page where these instructions cribbed from,
https://docs.confluent.io/platform/current/platform-quickstart.html#quick-start-for-cp

### install
installed under user home (i.e. downloading and extracting confluent-7.2.0.tar.gz)
### set Java v 11
Needs `Java 8` or `Java 11`, so... use a combination of `/usr/libexec/java_home` and `JAVA_HOME` environment variable. i.e.
> export JAVA_HOME=`/usr/libexec/java_home -v 11` -------// echo $JAVA_HOME  should show Java v11 now  
### set confluent home
Needs `CONFLUENT_HOME` environment variable (and append to path's `/bin`), i.e.
> export CONFLUENT_HOME=~/confluent-7.2.0  
> export PATH=$PATH:$CONFLUENT_HOME/bin
### start  confluent local
Then from home directory
> joseph.jobbings@JosephJobbingss-MacBook-Pro ~ % confluent local services start         

:information: note output shows where current confluent data is getting stored
e.g.
> Using CONFLUENT_CURRENT: /var/folders/p1/99852nrd7xn0fjqkp8gfk6rh0000gn/T
### stop confluent local
> confluent local services stop
### destroy confluent local
> confluent local destroy
### view output logs
> bin/confluent local services connect log  
> bin/confluent local services connect log -f  # --------tail
Confirm log levels: curl -Ss http://localhost:8083/admin/loggers | jq


### Control centre
http://localhost:9021 - in local install.  use the control centre to add a topic (i.e. a grouping of related events).  n.b. topic names ARE case sensitive.  in the getting started guide, later on you create data generators that produce data to these topics.
connect topics to datagen source connector (for development illustrations)
