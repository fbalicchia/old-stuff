package it.balyfix.webexample;

import com.syncleus.ferma.DelegatingFramedGraph;
import com.syncleus.ferma.FramedGraph;
import it.balyfix.webexample.it.balyfix.bo.Knows;
import it.balyfix.webexample.it.balyfix.bo.Person;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphTransaction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by fbalicchia on 17/02/2018.
 */
public class OrmGraphFactory
{

    public static void load(JanusGraph graph) {
        load(graph, "search", true);
    }


    public static void load(JanusGraph graph, String mixedIndexName, boolean uniqueNameCompositeIndex) {

        Set<Class<?>> types = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{
            Person.class,
            Knows.class}));


//        JanusGraphManagement management = graph.openManagement();
//        PropertyKey name = management.makePropertyKey("name").dataType(String.class).make();
//        JanusGraphManagement.IndexBuilder nameIndexBuilder = management.buildIndex("name", Vertex.class).addKey(name);
//        if(uniqueNameCompositeIndex) {
//            nameIndexBuilder.unique();
//        }
//
//        JanusGraphIndex nameIndex = nameIndexBuilder.buildCompositeIndex();
//        management.setConsistency(nameIndex, ConsistencyModifier.LOCK);
//        PropertyKey age = management.makePropertyKey("age").dataType(Integer.class).make();
//        if(null != mixedIndexName) {
//            management.buildIndex("vertices", Vertex.class).addKey(age).buildMixedIndex(mixedIndexName);
//        }
//
//        PropertyKey time = management.makePropertyKey("time").dataType(Integer.class).make();
//        PropertyKey reason = management.makePropertyKey("reason").dataType(String.class).make();
//        PropertyKey place = management.makePropertyKey("place").dataType(Geoshape.class).make();
//        if(null != mixedIndexName) {
//            management.buildIndex("edges", Edge.class).addKey(reason).addKey(place).buildMixedIndex(mixedIndexName);
//        }

//        management.makeEdgeLabel("father").multiplicity(Multiplicity.MANY2ONE).make();
//        management.makeEdgeLabel("mother").multiplicity(Multiplicity.MANY2ONE).make();
//        EdgeLabel battled = management.makeEdgeLabel("battled").signature(new PropertyKey[]{time}).make();
//        management.buildEdgeIndex(battled, "battlesByTime", Direction.BOTH, Order.decr, new PropertyKey[]{time});
//        management.makeEdgeLabel("lives").signature(new PropertyKey[]{reason}).make();
//        management.makeEdgeLabel("pet").make();
//        management.makeEdgeLabel("brother").make();
//        management.makeVertexLabel("titan").make();
//        management.makeVertexLabel("location").make();
//        management.makeVertexLabel("god").make();
//        management.makeVertexLabel("demigod").make();
//        management.makeVertexLabel("human").make();
//        management.makeVertexLabel("monster").make();
//        management.commit();
        JanusGraphTransaction tx = graph.newTransaction();

        FramedGraph fg = new DelegatingFramedGraph(graph, true, types);

        Person laura = fg.addFramedVertex(Person.class);
        laura.setName("Laura");
        laura.setProperty("alias","Laux");
        Person filippo = fg.addFramedVertex(Person.class);
        filippo.setName("Filippo");
        filippo.setProperty("alias","balyf!x");
        filippo.addKnows(laura);
        tx.commit();
    }



}
