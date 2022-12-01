package ca.ulaval.glo4002.cafe;

import ca.ulaval.glo4002.cafe.infrastructure.rest.DI.ApplicationBinder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Coffee Server API.
 */
public class CafeServer implements Runnable {
    private static final int PORT = 8181;

    public static void main(String[] args) {
        new CafeServer().run();
    }

    /**
     * Run the server.
     */
    public void run() {
        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        ResourceConfig packageConfig = new ResourceConfig().packages("ca.ulaval.glo4002.cafe");

        packageConfig.register(new ApplicationBinder());

        ServletContainer container = new ServletContainer(packageConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addServlet(servletHolder, "/*");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (server.isRunning()) {
                server.destroy();
            }
        }
    }
}
