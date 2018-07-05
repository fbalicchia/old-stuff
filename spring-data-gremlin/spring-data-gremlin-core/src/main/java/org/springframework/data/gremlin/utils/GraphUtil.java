package org.springframework.data.gremlin.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Graph;


public class GraphUtil {

    public static String queryToString(Graph graph, GraphTraversal traversal) {
        if (traversal == null) {
            return StringUtils.EMPTY;
        }
        return org.apache.tinkerpop.gremlin.groovy.jsr223.GroovyTranslator.of(graph.toString()).translate((traversal.asAdmin().clone()).getBytecode());
    }
}
