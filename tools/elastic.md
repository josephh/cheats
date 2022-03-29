# Elastic Search

## Health check
(cluster) `http://localhost:9200/_cat/health?v`

## List nodes
`http://localhost:9200/_cat/nodes?v`

## Indices
`http://localhost:9200/_cat/indices?v`
> health status index    uuid                   pri rep docs.count docs.deleted store.size pri.store.size
yellow open   cms     5b27yaJsQi6WKWIvAgswpQ   5   1          0            0       324b           324b

...response tells us we have 5 primary shards and 1 replica (defaults) with a  document count of zero. Health shows as yellow because, until a 'cluster' has more than one node, a replica cannot be allocated to another node, to provide high availability (and a green status).

### Create an index
```
PUT /customer?pretty
// GET /_cat/indices?v
```

### Index and query
```
PUT /customer/external/1?pretty
{
  "name": "John Doe"
}
// GET /customer/external/1?pretty
```

### Delete an index
```
DELETE /customer
```

### Modify an index
```
PUT /customer/external/1?pretty
{
  "name": "John Smith"
}
```
Alternatively, update a doc, e.g.
```
POST /customer/external/1/_update?pretty
{
  "doc": { "name": "Jane Doe" }
  // or script it e.g. "script" : "ctx._source.age += 5"
}
```
> Note though that Elasticsearch does not actually do in-place updates under the hood. Whenever we do an update, Elasticsearch deletes the old document and then indexes a new document with the update applied to it in one shot.
