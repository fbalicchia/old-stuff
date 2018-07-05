package it.balyfix.example;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.both;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.is;


public class Example1SimpleGraph
{

    private static final Logger LOGGER = LoggerFactory.getLogger(Example1SimpleGraph.class);

    public static void main(String[] args)
    {
        JanusGraph graph = JanusGraphFactory.open("plain-java-example/conf/janusgraph-cassandra-lucene.properties");

        final Vertex marko = graph.addVertex("name", "marko", "age", 29);
        final Vertex vadas = graph.addVertex("name", "vadas", "age", 27);
        final Vertex lop = graph.addVertex("name", "lop", "lang", "java");
        final Vertex josh = graph.addVertex("name", "josh", "age", 32);
        final Vertex ripple = graph.addVertex("name", "ripple", "lang", "java");
        final Vertex peter = graph.addVertex("name", "peter", "age", 35);
        marko.addEdge("knows", vadas, "weight", 0.5f);
        marko.addEdge("knows", josh, "weight", 1.0f);
        marko.addEdge("created", lop, "weight", 0.4f);
        josh.addEdge("created", ripple, "weight", 1.0f);
        josh.addEdge("created", lop, "weight", 0.4f);
        peter.addEdge("created", lop, "weight", 0.2f);

        GraphTraversalSource g = graph.traversal();

        LOGGER.info("{}", g.toString());

        Vertex fromNode = g.V().has("name", "marko").next();
        Vertex toNode = g.V().has("name", "peter").next();
        ArrayList list = new ArrayList();
        g.V(fromNode).repeat(both().simplePath()).until(is(toNode)).limit(1).path().fill(list);

        for (int i = 0; i < list.size(); i++)
        {
            LOGGER.info("value {} " + list.get(i).getClass());
        }

        LOGGER.info(list.toString());
        System.exit(0);


    }

}
