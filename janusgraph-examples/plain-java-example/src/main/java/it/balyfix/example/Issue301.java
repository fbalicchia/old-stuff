//package it.balyfix.example;
//
//
//import org.apache.tinkerpop.gremlin.process.traversal.Order;
//import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
//import org.apache.tinkerpop.gremlin.structure.Direction;
//import org.apache.tinkerpop.gremlin.structure.Edge;
//import org.apache.tinkerpop.gremlin.structure.Vertex;
//import org.janusgraph.core.*;
//import org.janusgraph.core.schema.ConsistencyModifier;
//import org.janusgraph.core.schema.JanusGraphIndex;
//import org.janusgraph.core.schema.JanusGraphManagement;
//
//import java.util.Random;
//
//import static com.sun.tools.doclets.internal.toolkit.util.DocPath.parent;
//import static org.janusgraph.util.datastructures.RandomRemovalList.random;
//
//
//public class Issue301
//{
//
//    private static int numThreads 10;
//
//
//    public static void main(String[] args)
//    {
//        Thread[] threads = new Thread[numThreads];
//        for (int i = 0; i < numThreads; i++) {
//            threads[i] = new Thread(new EdgeUpdator(graph, parent, child, hostid));
//        }
//        for (int i = 0; i < numThreads; i++) {
//            threads[i].start();
//        }
//        for (int i = 0; i < numThreads; i++) {
//            threads[i].join();
//        }
//    }
//
//
//
//    private void createPropertiesAndIndexes() {
//
//        JanusGraphManagement mgmt = graph.openManagement();
//        PropertyKey key = null;
//        JanusGraphIndex index = null;
//        EdgeLabel label = null;
//
//        //Vertex
//        key = mgmt.makePropertyKey(MSID).dataType(Integer.class).cardinality(Cardinality.SINGLE).make();
//        index = mgmt.buildIndex("by"+MSID, Vertex.class).addKey(key).unique().buildCompositeIndex();
//        mgmt.setConsistency(index, ConsistencyModifier.LOCK);
//
//        key = mgmt.makePropertyKey(TYPE).dataType(Short.class).cardinality(Cardinality.SINGLE).make();
//        key = mgmt.makePropertyKey(CREATEDAT).dataType(Long.class).cardinality(Cardinality.SINGLE).make();
//        key = mgmt.makePropertyKey(CMSTYPE).dataType(Integer.class).cardinality(Cardinality.SINGLE).make();
//        key = mgmt.makePropertyKey(MSNAME).dataType(String.class).cardinality(Cardinality.SINGLE).make();
//
//        //Edge
//        label = mgmt.makeEdgeLabel(HIE_CHILD).multiplicity(Multiplicity.MULTI).make();
//
//        key = mgmt.makePropertyKey(HOSTID_E).dataType(Integer.class).cardinality(Cardinality.SINGLE).make();
//        key = mgmt.makePropertyKey(CREATEAT_E).dataType(Long.class).cardinality(Cardinality.SINGLE).make();
//        key = mgmt.makePropertyKey(CMSTYPE_E).dataType(Integer.class).cardinality(Cardinality.SINGLE).make();
//        key = mgmt.makePropertyKey(STATUS_E).dataType(Short.class).cardinality(Cardinality.SINGLE).make();
//        key = mgmt.makePropertyKey(HNAME).dataType(String.class).cardinality(Cardinality.SINGLE).make();
//        key = mgmt.makePropertyKey(HRANK).dataType(Float.class).cardinality(Cardinality.SINGLE).make();
//
//        mgmt.buildEdgeIndex(label, "HSCCERDecr", Direction.BOTH, Order.decr,
//            mgmt.getPropertyKey(HOSTID_E),
//            mgmt.getPropertyKey(STATUS_E),
//            mgmt.getPropertyKey(CMSTYPE_E),
//            mgmt.getPropertyKey(HRANK));
//
//        mgmt.commit();
//    }
//
//
//
//    private void createNodesAndEdges() {
//        System.out.println("BEGIN createNodesAndEdges");
//        JanusGraphTransaction trxn = graph.newTransaction();
//        try {
//            //create msid=0;
//            Vertex root = createMasterVertex(0, 1);
//
//            //create level-1;
//            Vertex l1V, l2V;
//            for(int i =1; i <= NUM_L1; i++) {
//                int l1msid = i*MULTIPLIER;
//                l1V = createMasterVertex(l1msid, 2);
//                Edge edge1 = createEdge(l1V, root, i);
//                edge1.property(CMSTYPE_E, 2);
//
//                //create level-2;
//                for(int j =1; j <= NUM_L2; j++) {
//                    int l2msid = l1msid + j;
//                    l2V = createMasterVertex(l2msid, 1001);
//                    Edge edge2= createEdge(l2V, l1V, j);
//                    edge2.property(CMSTYPE_E, 1001);
//                }
//            }
//            System.out.println("END createNodesAndEdges");
//        } finally {
//            trxn.commit();
//        }
//    }
//
//
//
//    private Vertex createMasterVertex(int msid, int cmstype) {
//        Vertex masterV = graph.addVertex();
//        masterV.property(MSID, msid);
//        masterV.property(TYPE, 0);
//        masterV.property(CREATEDAT, Math.abs(random.nextLong()));
//        masterV.property(CMSTYPE, cmstype);
//        masterV.property(MSNAME, "Master Vertex " + msid + " Of Type " + cmstype);
//        System.out.println("Created MasterVertex: msid: " + msid + "; cmstype: " + cmstype);
//        return masterV;
//    }
//
//    private Edge createEdge(Vertex childV, Vertex parentV, int rank) {
//        Edge edge = parentV.addEdge(HIE_CHILD, childV);
//
//        edge.property(HOSTID_E, 83);
//            edge.property(CREATEAT_E, Math.abs(random.nextLong()));
//        edge.property(STATUS_E, 2);
//        edge.property(HNAME, childV.id() + " Child of Parent " + parentV.id() + ", rank: " + rank);
//        edge.property(HRANK);
//
//        return edge;
//    }
//
//
//
//    private class EdgeUpdator implements Runnable
//    {
//
//        @Override
//        public void run()
//        {
//            JanusGraphTransaction trxn = graph.newTransaction();
//            GraphTraversalSource g = trxn.traversal();
//            Edge edge = (Edge) g.V().has("msid", parent).outE("hie_child").as("e")
//                .has("hostid_e", hostid)
//                .inV().has("msid", child)
//                .select("e").next();
//            Random random = new Random(System.nanoTime());
//            Long updatedAt = random.nextLong();
//
//            edge.property("hrank", random.nextInt());
//            edge.property("updatedAt_e", updatedAt);
//
//            System.out.println("updatedAt: {}" + updatedAt);
//            trxn.commit();
//            System.out.println("updatedAt: {}" + updatedAt);
//        }
//    }
//
//
//}
