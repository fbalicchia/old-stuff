package it.balyfix.example;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.schema.JanusGraphIndex;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.core.schema.SchemaStatus;
import org.janusgraph.graphdb.database.management.ManagementSystem;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


/**
 * Created by fbalicchia on 29/01/2018.
 */
public class IndexerCompositeKey
{

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(IndexerCompositeKey.class);


    public static void main(String[] args) throws Throwable
    {
        new IndexerCompositeKey().run();
    }


    public void run() throws IOException, InterruptedException, ExecutionException
    {

        JanusGraph graph = JanusGraphFactory.open("plain-java-example/conf/janusgraph-cassandra-lucene.properties");

        graph.tx().rollback();

        ManagementSystem mgmt = (ManagementSystem)graph.openManagement();

        JanusGraphManagement.IndexBuilder idx = mgmt.buildIndex("airportIndex", Vertex.class);

        JanusGraphIndex airportIndex = mgmt.getGraphIndex("airportIndex");


        //ScanMetrics scanMetrics = mgmt.updateIndex(airportIndex, SchemaAction.REINDEX).get();

        //PropertyKey code = mgmt.getPropertyKey("code");

        //JanusGraphIndex janusGraphIndex = idx.addKey(code).buildCompositeIndex();

        mgmt.commit();

        mgmt.awaitGraphIndexStatus(graph, "airportIndex").
            status(SchemaStatus.REGISTERED).call();

        //graph.io(IoCore.graphml()).readGraph("plain-java-example/conf/air-routes.graphml");
        //        GraphTraversalSource g = graph.traversal();
        //
        //        GraphTraversal<Vertex, Vertex> vertex = g.V().has("city", "London").has("country", "CA");
        //        LOG.info("value {} ", vertex.value().toString());

        System.exit(0);

    }


}
