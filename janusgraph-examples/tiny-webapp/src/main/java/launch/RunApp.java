package launch;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class RunApp
{

    private static final Logger logger = LoggerFactory.getLogger(RunApp.class);

    public static void main(String[] args) throws Exception
    {
        logger.info("Working directory: {}", new File("./").getAbsolutePath().toString());
        Server server = new Server(9090);

        WebAppContext context = new WebAppContext();
        context.setDescriptor("/WEB-INF/web.xml");
        context.setResourceBase("tiny-webapp/src/main/webapp");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);

        context.addServlet(DefaultServlet.class, "/*");
        server.setHandler(context);

        server.start();
        server.join();

    }

}
