/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package client;

import client.EchoClient;
import com.google.protobuf.GeneratedMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import proto.snake.Snake;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Handler implementation for the echo client.  It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final GeneratedMessage firstMessage;

    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler() {
        Snake.MatchResult.Builder builder = Snake.MatchResult.newBuilder();
        builder.setId(ThreadLocalRandom.current().nextInt(0, 1000));
        builder.setScore(ThreadLocalRandom.current().nextInt(0, 100));

        firstMessage = builder.build();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        byte[] data = firstMessage.toByteArray();
        ByteBuf buffer = ctx.alloc().buffer(data.length);
        buffer.writeBytes(data);

        ChannelFuture future = null;
        try {
            future = ctx.writeAndFlush(buffer).sync();
        } catch (InterruptedException e) {
        }

//        if (future != null && future.isSuccess()) {
//            System.out.println("---------Success");
//        } else {
//            System.err.println("---------Fail");
//        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
