package com.javacode.testclient.net;

import com.kodgames.corgi.core.net.handler.AbstractMessageInitializer;
import com.kodgames.corgi.core.net.handler.netty.WebSocketHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class ClientWSDispatchNettyInitializer extends ChannelInitializer<Channel>
{
	private static AtomicInteger handlerId = new AtomicInteger(0);
	private MessageProcessor messageProcessor;

	public ClientWSDispatchNettyInitializer(AbstractMessageInitializer messageInitializer)
	{
		messageProcessor = new MessageProcessor(messageInitializer);
	}

	@Override
	protected void initChannel(Channel ch)
		throws Exception
	{
		ChannelPipeline p = ch.pipeline();
		String nameId = "-" + handlerId.incrementAndGet();

		// length excludes the lenghFieldlength and the data decoded exclude the length field
		p.addLast("http-codec" + nameId, new HttpServerCodec());
		p.addLast("http-chunked" + nameId, new ChunkedWriteHandler());
		p.addLast("aggregator" + nameId, new HttpObjectAggregator(65536));

		p.addLast("wsprotocol" + nameId, new WebSocketServerProtocolHandler(""));

		p.addLast("websocketHandler" + nameId, new WebSocketHandler());
		p.addLast("MessageProcessor" + nameId, messageProcessor);
	}
}
