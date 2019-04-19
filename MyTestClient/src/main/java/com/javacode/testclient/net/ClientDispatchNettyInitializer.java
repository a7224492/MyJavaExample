package com.javacode.testclient.net;

import com.kodgames.corgi.core.net.handler.AbstractMessageInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.concurrent.atomic.AtomicInteger;

public class ClientDispatchNettyInitializer extends ChannelInitializer<Channel>
{
	private static AtomicInteger handlerId = new AtomicInteger(0);
	private MessageProcessor messageProcessor;

	public ClientDispatchNettyInitializer(AbstractMessageInitializer messageInitializer)
	{
		messageProcessor = new MessageProcessor(messageInitializer);
	}

	// socket
	@Override
	protected void initChannel(Channel ch)
		throws Exception
	{
		ChannelPipeline p = ch.pipeline();
		String nameId = "-" + handlerId.incrementAndGet();

		// length excludes the lenghFieldlength and the data decoded exclude the length field
		p.addLast("FrameDecoder" + nameId, new LengthFieldBasedFrameDecoder(204800, 0, 4, 0, 4));
		p.addLast("LengthFieldPrepender" + nameId, new LengthFieldPrepender(4, false));
		p.addLast("MessageProcessor" + nameId, messageProcessor);
	}
}
