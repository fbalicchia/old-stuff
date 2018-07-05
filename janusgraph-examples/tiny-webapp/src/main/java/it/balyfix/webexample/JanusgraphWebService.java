package it.balyfix.webexample;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;



@Path("/")
@Component
public class JanusgraphWebService
{
    private static final Logger logger = LoggerFactory.getLogger(JanusgraphWebService.class);

    @Autowired
    GroovyGraphOp groovyOp;

    @Autowired
    JavaGraphOp javaOp;

    @PostConstruct
    private void init()
    {
        logger.info("Initialized Janus Web Example Service");
    }

    @GET
    @Path("/listVertices")
    @Produces(MediaType.TEXT_PLAIN)
    public String listVertices(@Context UriInfo info) throws JSONException
    {
        String res = javaOp.listVertices();

        return "\"" + res.substring(1,res.length() -1) + "\"";
    }

    @GET
    @Path("/plutosBrothers")
    @Produces(MediaType.TEXT_PLAIN)
    public String pipeline(@Context UriInfo info) throws JSONException
    {
        String res = groovyOp.getPlutosBrothers();
        return "\"" + res + "\"";
    }

    @GET
    @Path("/r/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String grandFather(@Context UriInfo info,@PathParam("name") String name) throws JSONException
    {
        String res = javaOp.findGrandFather(name);
        return "\"" + res + "\"";
    }

}
