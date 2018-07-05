package org.springframework.data.gremlin.repository;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.gremlin.schema.GremlinEdgeSchema;
import org.springframework.data.gremlin.schema.GremlinSchema;
import org.springframework.data.gremlin.schema.property.GremlinAdjacentProperty;
import org.springframework.data.gremlin.tx.GremlinGraphFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of the {@link org.springframework.data.repository.PagingAndSortingRepository} interface for Gremlin.
 *
 * @param <T> the type of the entity to handle
 * @author Gman
 */
@Repository
@Transactional(readOnly = true)
public class SimpleGremlinRepository<T> implements GremlinRepository<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleGremlinRepository.class);

    protected GremlinGraphFactory dbf;

    protected GremlinSchema<T> schema;

    protected GremlinGraphAdapter graphAdapter;

    public SimpleGremlinRepository(GremlinGraphFactory dbf, GremlinGraphAdapter graphAdapter, GremlinSchema<T> schema) {
        this.dbf = dbf;
        this.graphAdapter = graphAdapter;
        this.schema = schema;
    }


    @Transactional
    protected Element create(Graph graph, final T object, Object... noCascade) {
        Element element;
        if (schema.isVertexSchema()) {
            element = graphAdapter.createVertex(graph, schema.getClassName());
            schema.copyToGraph(graphAdapter, element, object, noCascade);
        } else if (schema.isEdgeSchema()) {
            GremlinEdgeSchema edgeSchema = (GremlinEdgeSchema) schema;
            GremlinAdjacentProperty adjacentOutProperty = edgeSchema.getOutProperty();

            Vertex outVertex = null;
            Vertex inVertex = null;

            Object outObject = adjacentOutProperty.getAccessor().get(object);
            if (outObject != null) {
                String outId = adjacentOutProperty.getRelatedSchema().getObjectId(outObject);
                outVertex = graphAdapter.findOrCreateVertex(outId, adjacentOutProperty.getRelatedSchema().getClassName());
            }

            GremlinAdjacentProperty adjacentInProperty = edgeSchema.getInProperty();
            Object inObject = adjacentInProperty.getAccessor().get(object);
            if (inObject != null) {
                String inId = adjacentInProperty.getRelatedSchema().getObjectId(inObject);
                inVertex = graphAdapter.findOrCreateVertex(inId, adjacentInProperty.getRelatedSchema().getClassName());
            }

            element = graphAdapter.addEdge(null, outVertex, inVertex, schema.getClassName());

            schema.copyToGraph(graphAdapter, element, object, noCascade);
        } else {
            throw new IllegalStateException("Schema is neither EDGE nor VERTEX!");
        }
        return element;
    }

    @Transactional
    public T save(Graph graph, T object, Object... noCascade) {

        String id = schema.getObjectId(object);
        if (StringUtils.isEmpty(id)) {
            create(graph, object);
        } else {
            Element element;
            if (schema.isVertexSchema()) {
                element = graphAdapter.getVertex(schema.decodeId(id));
            } else if (schema.isEdgeSchema()) {
                element = graphAdapter.getEdge(schema.decodeId(id));
            } else {
                throw new IllegalStateException("Schema is neither EDGE nor VERTEX!");
            }
            if (element == null) {
                throw new IllegalStateException(String.format("Could not save %s with id %s, as it does not exist.", object, id));
            }
            schema.copyToGraph(graphAdapter, element, object, noCascade);
        }
        return object;
    }

    @Override
    public <S extends T> S save(S entity)
    {
        Objects.requireNonNull(entity);
        return save(entity, new Object[0]);
    }

    @Transactional
    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities)
    {
        Objects.requireNonNull(entities);
        for (S s : entities) {
            save(s);
        }
        return entities;
    }

    @Override
    public Optional<T> findById(String id)
    {
        Objects.requireNonNull(id);
        T object = null;
        Element element;
        if (schema.isVertexSchema()) {
            element = graphAdapter.findVertexById(id);
        } else if (schema.isEdgeSchema()) {
            element = graphAdapter.findEdgeById(id);
        } else {
            throw new IllegalStateException("Schema is neither VERTEX nor EDGE!");
        }

        if (element != null) {
            object = schema.loadFromGraph(graphAdapter, element);
        }

        return Optional.ofNullable(object);
    }

    @Override
    public boolean existsById(String id)
    {
        Objects.requireNonNull(id);
        return false;
    }

    @Transactional
    @Override
    public <S extends T> S save(final S s, final Object... noCascade) {

        Graph graph = dbf.graph();

        String id = schema.getObjectId(s);
        if (graphAdapter.isValidId(id)) {
            save(graph, s, noCascade);
        } else {
            create(graph, s, noCascade);

            if (TransactionSynchronizationManager.isSynchronizationActive()) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        if (!graphAdapter.isValidId(schema.getObjectId(s))) {
                            //                            throw dbf.getForceRetryException();
                        }
                    }
                });
            }
        }
        return s;

    }

    @Override
    public Iterable<T> findAll() {
        throw new NotImplementedException("Finding all vertices in Graph databases does not really make sense. So, it hasn't been implemented.");
    }

    @Override
    public Iterable<T> findAllById(Iterable<String> iterable)
    {
        Set<T> objects = new HashSet<>();
        for (String id : iterable) {
            objects.add(findById(id).orElse(null));
        }
        return objects;
    }


    @Override
    public long count() {
        throw new NotImplementedException("Counting all vertices in Gremlin has not been implemented.");
    }

    @Transactional
    @Override
    public void deleteById(String id)
    {
        Objects.requireNonNull(id);
        if (schema.isVertexSchema()) {
            Vertex v = graphAdapter.findVertexById(id);
            v.remove();
        } else if (schema.isEdgeSchema()) {
            Edge v = graphAdapter.findEdgeById(id);
            v.remove();
        }

    }

    @Transactional
    @Override
    public void delete(T t) {
        schema.getObjectId(t);
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends T> entities)
    {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException("Deleting all vertices in Gremlin has not been implemented.");
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        throw new NotImplementedException("Sorting all vertices in Gremlin has not been implemented.");
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        throw new NotImplementedException("Deleting all vertices in Gremlin has not been implemented.");
    }
}
