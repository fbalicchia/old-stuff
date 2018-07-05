# JanusGraph: enviroment

```
docker-compose up --build
```

connect to the local Gremlin shell using

```
docker exec -it janusgraphdocker_janus_1 ./bin/gremlin.sh
```

## Shell use og crew graph

```
graph = TinkerFactory.createTheCrew()
g = graph.traversal()

```

##Query Example 
```
g.V().hasLabel('person').valueMap()
g.V().hasLabel('person').as('person').
               properties('location').as('location').
               select('person','location').by('name').by(valueMap())

```

