package it.balyfix.webexample;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import org.janusgraph.core.JanusGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RequestWrapper implements ContainerResponseFilter, ContainerRequestFilter
{

    // Autowired via setter.
    private JanusGraph g;

    @Autowired
    public void setGraph(JanusLocalGraphFactory gf)
    {
        this.g = gf.getGraph();
    }

    @Override
    public ContainerRequest filter(ContainerRequest containerRequest)
    {
        g.tx().rollback();
        return containerRequest;
    }

    @Override
    public ContainerResponse filter(ContainerRequest containerRequest, ContainerResponse containerResponse)
    {
        g.tx().rollback(); 
        return containerResponse;
    }
}
