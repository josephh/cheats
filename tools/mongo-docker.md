
1. start up mongo container `docker run --name mongodb -d -v /Users/jjobbings/mongo-data:/data/db -p 27017:27017 mongo`
1. connect to docker mongo container > `docker exec -it mongodb bash`
1. connect to mongo without access control from within the docker mongo container  > `mongosh --port 27017` (outputs:  The server generated these startup warnings when booting: "Access control is not enabled for the database. Read and write access to data and configuration is unrestricted")
1. Create admin user: switch to admin database `use admin` and create a power user,
```
db.createUser(
  {
    user: "admin",
    pwd: "admin",
    roles: [
      { role: "userAdminAnyDatabase", db: "admin" },
      { role: "readWriteAnyDatabase", db: "admin" }
    ]
  }
)
```
1. shutdown mongo, this will exit docker container as well `db.adminCommand( { shutdown: 1 } )`
1. restart mongo in docker with access control enabled `docker run --name mongodb -d -v /Users/jjobbings/mongo-data:/data/db -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin mongo`.  The presence of the command line mongo arguments MONGO_INITDB_ROOT_USERNAME and MONGO_INITDB_ROOT_PASSWORD cause Mongod to include the `--auth` switch, to turn on access control.
1. Test the presence of the admin user created above and the login details via,
```
use admin
db.auth("admin", passwordPrompt())  # supply 'admin' password
```
1. authenticate as admin (using above command) to run commands requiring elevated privileges.
1. create a user for connecting with,
```
use newbiz
db.createUser(
  {
    user: "myTester",
    pwd:  "myTester",
    roles: [ { role: "readWrite", db: "newbiz" },
             { role: "read", db: "newbiz" } ]
  }
)
1. to connect from node.js, first install the mongo driver: `npm install mongodb` (running `npm list mongodb` will show the version of the library just added to the package.json dependencies)
