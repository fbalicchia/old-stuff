package org.springframework.data.gremlin.repository.janus;

import org.janusgraph.core.JanusGraph;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.gremlin.repository.GremlinGraphAdapter;
import org.springframework.data.gremlin.repository.SimpleGremlinRepository;
import org.springframework.data.gremlin.schema.GremlinSchema;
import org.springframework.data.gremlin.tx.GremlinGraphFactory;
import org.springframework.data.gremlin.tx.janus.JanusGremlinGraphFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Janus specific extension of the {@link SimpleGremlinRepository} providing custom implementations of {@code count()}, {@code deleteAll()},
 * {@code findAll(Pageable)} and {@code findAll()}.
 *
 * @author mmichail (zifnab87)
 *
 * credit to: gman's work for supplying initial code for TitanDB.
 */
public class JanusGremlinRepository<T> extends SimpleGremlinRepository<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JanusGremlinRepository.class);

    private JanusGremlinGraphFactory graphFactory;

    public JanusGremlinRepository(GremlinGraphFactory dbf, GremlinGraphAdapter graphAdapter, GremlinSchema<T> mapper) {
        super(dbf, graphAdapter, mapper);
        this.graphFactory = (JanusGremlinGraphFactory) dbf;
    }

    @Transactional
    protected Vertex createVertex(Graph graph) {
        return ((JanusGraph) graph).addVertex(schema.getClassName());
    }

    @Override
    @Transactional
    public long count() {
        long count = 0;
        try {
            for (Element el : findAllElementsForSchema()) {
                count++;
            }
        } catch (Exception e) {
            return 0;
        }
        return count;
    }

    @Transactional
    @Override
    public void deleteAll() {
        for (Element element : findAllElementsForSchema()) {
            element.remove();
        }
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        List<T> result = new ArrayList<T>();
        int total = 0;
        long prevOffset = pageable.getOffset();
        long offset = pageable.getOffset() + pageable.getPageSize();
        for (Element element : findAllElementsForSchema()) {
            if (total >= prevOffset && total < offset) {
                result.add(schema.loadFromGraph(graphAdapter, element));
            }
            total++;
        }
        return new PageImpl<T>(result, pageable, total);
    }

    @Override
    public Iterable<T> findAll() {
        List<T> result = new ArrayList<T>();
        for (Element vertex : findAllElementsForSchema()) {
            result.add(schema.loadFromGraph(graphAdapter, vertex));
        }
        return result;
    }

    public Iterable<Element> findAllElementsForSchema() {
        if (schema.isVertexSchema()) {
            return findAllVerticiesForSchema();
        } else if (schema.isEdgeSchema()) {
            return findAllEdgesForSchema();
        } else {
            throw new IllegalStateException("GremlinSchema is neither VERTEX or EDGE!!");
        }
    }

    public Iterable<Element> findAllVerticiesForSchema() {
        return new ArrayList<>(graphFactory.graph().traversal().V().hasLabel(schema.getClassName()).toList());
    }

    public Iterable<Element> findAllEdgesForSchema() {
        return new ArrayList<>(graphFactory.graph().traversal().E().hasLabel(schema.getClassName()).toList());
    }
}
