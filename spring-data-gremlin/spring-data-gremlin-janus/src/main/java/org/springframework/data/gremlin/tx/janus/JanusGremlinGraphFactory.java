package org.springframework.data.gremlin.tx.janus;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.gremlin.tx.AbstractGremlinGraphFactory;



public class JanusGremlinGraphFactory extends AbstractGremlinGraphFactory<JanusGraph>
{

    private static final Logger LOGGER = LoggerFactory.getLogger(JanusGremlinGraphFactory.class);

    private JanusGraph graph = null;

    @Setter
    @Getter
    private Configuration configuration;

    @Setter
    @Getter
    private String path;

    @Override
    protected void createPool()
    {
        if (configuration != null)
        {
            graph = JanusGraphFactory.open(configuration);
        }
        else if (StringUtils.isNotEmpty(url))
        {
            graph = JanusGraphFactory.open(url);
        }
        else
        {
            graph = JanusGraphFactory.open(path);
        }
    }

    @Override
    public boolean isActive(JanusGraph graph)
    {
        return graph.isOpen();
    }

    @Override
    public boolean isClosed(JanusGraph graph)
    {
        return graph.isClosed();
    }

    @Override
    public void beginTx(JanusGraph graph)
    {
        graph.newTransaction();
    }

    @Override
    public void commitTx(JanusGraph graph)
    {
        graph.tx().commit();
    }

    @Override
    public void rollbackTx(JanusGraph graph)
    {
        graph.tx().rollback();
    }

    @Override
    public JanusGraph openGraph()
    {
        if (graph == null || graph.isClosed())
        {
            createPool();
        }
        return graph;
    }

    @Override
    protected void createGraph()
    {
    }

}
