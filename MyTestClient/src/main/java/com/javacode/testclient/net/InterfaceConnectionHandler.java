package com.javacode.testclient.net;

import com.javacode.testclient.common.Role;
import com.javacode.testclient.common.RoleService;
import com.kodgames.corgi.core.net.Connection;
import com.kodgames.corgi.core.net.handler.connection.AbstractConnectionHandler;
import com.kodgames.corgi.core.service.ServiceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Process Connection In/Active
 */
public class InterfaceConnectionHandler extends AbstractConnectionHandler
{
	private static final Logger logger = LoggerFactory.getLogger(InterfaceConnectionHandler.class);

	@Override
	public void handleConnectionActive(Connection connection)
	{
		connection.setConnectionType(Connection.CONNECTION_TYPE_CLIENT);
		logger.info("connection active, connection id {} type {}", connection.getConnectionID(), connection.getConnectionType());

		Role role = new Role(connection);
		role.init();

		RoleService roleService = ServiceContainer.getInstance().getPublicService(RoleService.class);
		roleService.addRole(role);

		// 保存Role
		connection.getRemoteNode().getChannel().attr(Role.ROLEINFO).set(role);

		role.getSequenceTask().start();
	}

	@Override
	public void handleConnectionInactive(Connection connection)
	{
		logger.info("connection inactive, connection id {} type {}", connection.getConnectionID(), connection.getConnectionType());
		RoleService roleService = ServiceContainer.getInstance().getPublicService(RoleService.class);
		roleService.removeRole(connection.getConnectionID());
	}
}
