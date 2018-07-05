package it.balyfix.webexample

import org.apache.tinkerpop.gremlin.groovy.loaders.GremlinLoader
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class GroovyGraphOp {
  // This enables the Gremlin magic.
  static {
    GremlinLoader.load()
  }

  private Graph graph
  private GraphTraversalSource g

  @Autowired
  public void setGraph(JanusLocalGraphFactory gf) {
    this.graph = gf.getGraph()
    this.g = this.graph.traversal()
  }

  public String getPlutosBrothers() {
    def plutosBrothers
    // sweet gremlin all up in my java project. aw yeah...
    plutosBrothers = g.V().has("name", "pluto").both("brother").dedup().values("name").toList()
    return plutosBrothers.toString()
  }
}
