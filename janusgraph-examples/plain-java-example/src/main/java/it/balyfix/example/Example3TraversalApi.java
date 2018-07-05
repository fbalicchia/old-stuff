package it.balyfix.example;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.attribute.Geo;
import org.janusgraph.core.attribute.Geoshape;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.example.GraphOfTheGodsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class Example3TraversalApi
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Example3TraversalApi.class);

    public static void main(String[] args)
    {
        new Example3TraversalApi().run();
    }


    public void run()
    {
        JanusGraph graph = JanusGraphFactory.open("plain-java-example/conf/janusgraph-cassandra-lucene.properties");
        JanusGraphManagement mgmt = graph.openManagement();
        String searchBackend = mgmt.get("index.search.backend");

        LOGGER.info("backend {}", searchBackend);
        GraphTraversalSource g = graph.traversal();
        if (g.V().count().next() == 0)
        {
            GraphOfTheGodsFactory.load(graph);
        }

        findGrandFather(g,"hercules");
        //query1(g);
        //query2(g);
        System.exit(0);
    }


    private void query1(GraphTraversalSource g)
    {
        Map<Object, Object> saturnProps = g.V().has("name", "saturn").valueMap(true).next();
        LOGGER.info(saturnProps.toString());
        List<Edge> places = g.E().has("place", Geo.geoWithin(Geoshape.circle(37.97, 23.72, 50))).toList();
        LOGGER.info(places.toString());
        LOGGER.info("Finish");

    }


    private String findGrandFather(GraphTraversalSource g, String name)
    {
       return  g.V().has("name", name).out("father").out("father").next().label();
    }


    private void query2(GraphTraversalSource g)
    {
        List<Vertex> vertices = g.V().has("name", "pluto").outE("brother").inV().out().filter(it -> it.get().property("name").value().equals("neptune")).toList();
        LOGGER.info("numero di vertici {}", vertices.size());
        for (Vertex vertx : vertices)
        {

            LOGGER.info("nome vertice {}", vertx.property("name").value());
        }


    }

}
