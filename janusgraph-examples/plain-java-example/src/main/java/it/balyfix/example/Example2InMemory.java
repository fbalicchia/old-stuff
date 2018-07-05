package it.balyfix.example;

import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.Multiplicity;
import org.janusgraph.core.schema.JanusGraphManagement;


public class Example2InMemory
{

    public static void main(String[] args)
    {
        JanusGraph graph = JanusGraphFactory.open("inmemory");
        JanusGraphManagement mgmt = graph.openManagement();
        mgmt.makeEdgeLabel("route").multiplicity(Multiplicity.MULTI).make();
        mgmt.makeEdgeLabel("contains").multiplicity(Multiplicity.SIMPLE).make();
        mgmt.commit();

    }

}
