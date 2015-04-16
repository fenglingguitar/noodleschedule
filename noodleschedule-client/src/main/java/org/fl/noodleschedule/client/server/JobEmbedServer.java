package org.fl.noodleschedule.client.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;

public class JobEmbedServer {
	
	private final static Log logger = LogFactory.getLog(JobEmbedServer.class);
	
	private Server server;
	
	private int port = 8001;
	
	private Handler servletHandler;

	public void start() throws Exception {
		
		server = new Server();
		SelectChannelConnector conn = new SelectChannelConnector();
		conn.setPort(port);
		server.setConnectors(new Connector[]{conn});
		server.setHandler(servletHandler);
		server.start();
		if (logger.isDebugEnabled()) {
			logger.debug("Start a JettyNetConnectServer -> Port: " + port);
		}
	}

	public void destroy() throws Exception {
		
		server.stop();
		//server.join();
		if (logger.isDebugEnabled()) {
			logger.debug("Close a JettyNetConnectServer -> Port: " + port);
		}
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setServletHandler(Handler servletHandler) {
		this.servletHandler = servletHandler;
	}
	
}
