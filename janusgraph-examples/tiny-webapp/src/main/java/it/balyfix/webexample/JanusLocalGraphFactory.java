package it.balyfix.webexample;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class JanusLocalGraphFactory
{
    private static final Logger logger = LoggerFactory.getLogger(JanusLocalGraphFactory.class);

    public static final String PROPS_PATH = "janus-web-example/config/janus-cassandra-lucene.properties";

    private JanusGraph g;

    @PostConstruct
    public void init()
    {
        try
        {
            logger.info("Janus graph Properties Path: {}", PROPS_PATH);
            Configuration conf = new PropertiesConfiguration(PROPS_PATH);
            g = JanusGraphFactory.open(conf);
            logger.info("Janus graph loaded successfully.");
        }
        catch (ConfigurationException e)
        {
            throw new IllegalStateException(e);
        }
    }

    public JanusGraph getGraph()
    {
        return g;
    }
}
