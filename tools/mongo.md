# Mongo database
`BSON` is the storage unit in Mongo - i.e. binary JSON
## Querying
### find all field names in a collection
query for all field names in the Collection. note, in this example `application` is the name of the collection to query.
`mr = db.runCommand({
  "mapreduce" : "application", "map" : function() {
    for (var key in this) {
      emit(key, null); } }, "reduce" : function(key, stuff) { return null; }, "out": "activities" + "_keys" })`

db.activities_keys.distinct("_id")`
### update all (i.e. no query filter)
`db.getCollection('newbiz').updateMany(
    // query
     { },
    // update
    { $set : {        "status" : "processing"  }  }
);`
### delete all (i.e. no query filter)
`db.getCollection('newbiz').deleteMany();`
### sort all by date asc
`db.getCollection('newbiz').find().sort( {created: 1} );`
### sort all by date desc
`db.getCollection('newbiz').find().sort( {created: -1} );`
