package launch;


import it.balyfix.webexample.JanusLocalGraphFactory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.tinkerpop.gremlin.structure.T;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.core.JanusGraphVertex;
import org.janusgraph.example.GraphOfTheGodsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PopulateDB
{
    private static final Logger logger = LoggerFactory.getLogger(PopulateDB.class);


    public static void main(String[] args) throws Exception
    {
        PopulateDB populateDB = new PopulateDB();
        JanusGraph janusGraph = populateDB.openGraph();
        populateDB.doLoad(janusGraph);
        System.exit(0);

    }


    public JanusGraph openGraph() throws ConfigurationException
    {
        Configuration conf = new PropertiesConfiguration(JanusLocalGraphFactory.PROPS_PATH);
        return JanusGraphFactory.open(conf);
    }



    public void doLoad(JanusGraph g) throws ConfigurationException
    {
        GraphOfTheGodsFactory.load(g);
        g.close();
        logger.info("Success.");
    }



    private void testAdd(JanusGraph g)
    {
        //GraphFactory.load(g);
    }

    private void addGianna(JanusGraph g)
    {
        JanusGraphTransaction tx = g.newTransaction();
        logger.info("is open {} ", tx.isOpen());
        JanusGraphVertex giannaVertex = tx.addVertex(new Object[]{ T.label, "human", "name", "Gianna", "age", Integer.valueOf(20)});
        giannaVertex.property("alias","superGianna2");
        tx.commit();
        tx.close();
    }

}
