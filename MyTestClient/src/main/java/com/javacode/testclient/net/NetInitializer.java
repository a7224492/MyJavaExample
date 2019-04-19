package com.javacode.testclient.net;

import com.javacode.testclient.common.Role;
import com.kodgames.corgi.core.net.ServerMessageInitializer;
import com.kodgames.corgi.core.net.server.SimpleClient;
import com.kodgames.corgi.core.session.ConnectionManager;
import com.kodgames.message.protocol.PlatformProtocolsConfig;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import static com.javacode.testclient.constant.Constants.INTERFACE_IP;
import static com.javacode.testclient.constant.Constants.INTERFACE_PORT;
import static com.javacode.testclient.constant.Constants.PLAYER_NUM;

/**
 * 网络初始化
 */
public class NetInitializer
{
	private static NetInitializer instance = new NetInitializer();
	private SimpleClient interfaceClient;

	private NetInitializer()
	{
	}

	public static NetInitializer getInstance()
	{
		return instance;
	}

	@SuppressWarnings("rawtypes")
	public void init()
		throws Exception
	{
		// 协议处理Action
		List<String> packageNameList = new ArrayList<>();

		// 协议码映射
		List<Class> protocolClassList = new ArrayList<>();
		protocolClassList.add(PlatformProtocolsConfig.class);

		// 初始化客户端
		SimpleMessageInitializer interfaceMsgInitializer = new SimpleMessageInitializer(packageNameList, protocolClassList);
		interfaceMsgInitializer.setConnectionHandler(new InterfaceConnectionHandler());
		interfaceMsgInitializer.initialize();
		ConnectionManager.getInstance().init(interfaceMsgInitializer);

		interfaceClient = new SimpleClient();
		interfaceClient.initialize(new ClientDispatchNettyInitializer(interfaceMsgInitializer), interfaceMsgInitializer);

		// 根据配置文件的连接数量，连接Interface服务器
		SocketAddress address = new InetSocketAddress(INTERFACE_IP, INTERFACE_PORT);
		for (int i = 0; i < PLAYER_NUM; i++) {
			connectToInterface(address);
		}
	}

	/**
	 * 客户端连接服务器
	 * 
	 * @param address 连接地址
	 */
	public void connectToInterface(SocketAddress address)
	{
		interfaceClient.connectTo(address, 1);
	}
}
