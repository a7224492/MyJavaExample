package com.javacode.testclient.net;

import com.kodgames.core.threadPool.OrderedThreadPoolExecutor;
import com.kodgames.corgi.core.net.ServerMessageInitializer;
import com.kodgames.corgi.core.net.common.ActionAnnotation;
import com.kodgames.corgi.core.net.handler.AbstractMessageInitializer;
import com.kodgames.corgi.core.net.handler.message.AbstractProtobufMessageHandler;
import com.kodgames.corgi.core.net.handler.message.BaseMessageHandler;
import com.kodgames.corgi.core.util.PackageScaner;
import com.kodgames.message.generaor.ProtocolConfigAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SimpleMessageInitializer extends AbstractMessageInitializer
{
	private static Logger logger = LoggerFactory.getLogger(ServerMessageInitializer.class);

	private List<String> actionPackageList;
	@SuppressWarnings("rawtypes")
	private List<Class> protocolClassList;

	@SuppressWarnings("rawtypes")
	public SimpleMessageInitializer(List<String> actionPackageList, List<Class> protocolClassList)
	{
		setBeforeExecutor(new OrderedThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS, "ServerMessageInitializer"));
		this.actionPackageList = actionPackageList;
		this.protocolClassList = protocolClassList;
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected void initMessages()
		throws Exception
	{
		for (Class c : protocolClassList)
		{
			bindProtocolIdAndMessageClass(c);
		}

		for (String p : actionPackageList)
		{
			bindActionServiceHandler(p);
		}
	}

	/**
	 * 设置协议ID和协议的对应关系
	 */
	@SuppressWarnings("rawtypes")
	private void bindProtocolIdAndMessageClass(Class protocolClass)
		throws Exception
	{
		Field[] fields = protocolClass.getDeclaredFields();
		try
		{
			for (Field field : fields)
			{
				int protocolID = field.getInt(null);
				String protocolName = field.getName();
				ProtocolConfigAnnotation protocolAnnotation = field.getAnnotation(ProtocolConfigAnnotation.class);
				if (protocolAnnotation != null)
					setMessageIDClass(protocolID, protocolAnnotation.protocolClass(), protocolAnnotation.needLogin());
				else if (protocolName.startsWith("P_"))
					logger.warn("Don't set corresponding protocol class for the protocol:{} in the PrococolDesc.xml", protocolName);
			}
		}
		catch (Exception e)
		{
			logger.error("Failed to register the protocol ID and protocol Class");
			throw e;
		}
	}

	/**
	 * 设置Action与Service与Handler之间关系
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private void bindActionServiceHandler(String packageName)
		throws Exception
	{
		try
		{
			if (actionPackageList == null)
			{
				logger.warn("Please set action package name for Project4MessageInitializer");
				return;
			}
			List<Class> actionClasses = PackageScaner.getClasses(packageName, ".class", true);
			for (Class actionClass : actionClasses)
			{
				Annotation annotation = actionClass.getAnnotation(ActionAnnotation.class);
				if (annotation != null)
				{
					ActionAnnotation actionAnnotation = (ActionAnnotation)annotation;
					setHandler(actionAnnotation.messageClass(), actionAnnotation.serviceClass(), (BaseMessageHandler<?>)actionClass.newInstance());
				}
				else if (AbstractProtobufMessageHandler.class.isAssignableFrom(actionClass))
					logger.warn("Please add ActionAnnotation for the Action:{}", actionClass.getSimpleName());
			}
		}
		catch (Exception e)
		{
			logger.error("Failed to register service Class and action Class");
			throw e;
		}
	}
}
