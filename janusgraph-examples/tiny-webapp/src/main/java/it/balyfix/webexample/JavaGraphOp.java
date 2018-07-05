package it.balyfix.webexample;

import com.google.common.collect.Lists;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;


@Component
public class JavaGraphOp
{
    private Graph g;

    @Autowired
    public void setGraph(JanusLocalGraphFactory gf)
    {
        this.g = gf.getGraph();
    }

    public String listVertices()
    {
        List<String> gods = Lists.newArrayList();
        Iterator<Vertex> itty = g.vertices();
        Vertex v;
        while (itty.hasNext())
        {
            v = itty.next();
            gods.add((String) v.property("name").value());
        }
        return gods.toString();
    }


    public String findGrandFather(String name)
    {
        return  g.traversal().V().has("name", name).out("father").out("father").next().label();
    }

}
