package com.javacode.testclient.net;

import com.google.protobuf.GeneratedMessage;
import com.javacode.testclient.common.Role;
import com.kodgames.corgi.core.net.Connection;
import com.kodgames.corgi.core.net.common.NettyNode;
import com.kodgames.corgi.core.net.handler.AbstractMessageInitializer;
import com.kodgames.corgi.core.net.handler.message.*;
import com.kodgames.corgi.core.net.message.AbstractCustomizeMessage;
import com.kodgames.corgi.core.net.message.InternalMessage;
import com.kodgames.corgi.core.session.ConnectionManager;
import com.kodgames.corgi.core.util.IPUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

@Sharable
public class MessageProcessor extends ChannelDuplexHandler {
    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    public static final byte MESSAGE_TYPE_CLIENT = 0x01;
    public static final byte MESSAGE_TYPE_HEARTBEAT = 0x05;
    private AbstractMessageInitializer messageInitializer;

    public MessageProcessor(AbstractMessageInitializer messageInitializer) {
        this.messageInitializer = messageInitializer;
    }

    private byte[] ByteBuf2ByteArray(ByteBuf buf) {
        byte[] array;
        final int length = buf.readableBytes();
        array = new byte[length];
        buf.getBytes(buf.readerIndex(), array, 0, length);
        return array;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (!ByteBuf.class.isAssignableFrom(msg.getClass())) {
            ctx.fireChannelRead(msg);
            return;
        }

        try {
            ByteBuf buf = (ByteBuf) msg;
            int len = buf.readableBytes();

            // 心跳包
            if (len == 2) {
                ByteBuf outBuf = ctx.alloc().buffer(2);
                outBuf.writeByte(MessageProcessor.MESSAGE_TYPE_HEARTBEAT);
                outBuf.writeByte(0x4a);
                ctx.writeAndFlush(outBuf);
                return;
            }

            // messageType+protocolID+callback
            if (len < 1 + 4 + 4) {
                logger.error("MessageProcessor.channelRead found error msg len {}", len);
                return;
            }

            Connection connection = ctx.channel().attr(Connection.CONNECTIONINFO).get();
            NettyNode remoteNode = connection.getNettyNode();

            byte messageType = buf.readByte();
            buf.readByte();

            InternalMessage corgiMsg = new InternalMessage();
            corgiMsg.setConnection(connection);
            corgiMsg.setMessageType(messageType);

            int protocolID = buf.readInt();

            int callback = buf.readInt();
            corgiMsg.setProtocolID(protocolID);
            corgiMsg.setCallback(callback);

            if ((remoteNode != null))
                remoteNode.incRecvAmount();

            Class<?> msgClass = messageInitializer.getMessageClass(protocolID);
            if (msgClass != null) {
                Method method = msgClass.getDeclaredMethod("parseFrom", byte[].class);
                if (method != null) {
                    Object obj = method.invoke(null, ByteBuf2ByteArray(buf));
                    corgiMsg.setMessage(obj);
                } else {
                    throw new Exception("Illegal protocolID:" + protocolID + ", found class but Can't find parseFrom method.");
                }
            } else {
                throw new Exception("Illegal protocolID:" + protocolID + ", Can't find corresponding Protobuf class.");
            }

            logger.debug("read: protocol id is {}:{}, ip address {}, connectionid {} len {} recvAmount {}",
                    protocolID,
                    Integer.toHexString(protocolID),
                    remoteNode.getAddress(),
                    corgiMsg.getConnection().getConnectionID(),
                    len,
                    remoteNode.getRecvAmount());

            MyMessageDispatcher.getInstance().dispatchMessage(connection, corgiMsg);
        } catch (Throwable e) {
            logger.error("channel " + ctx.channel().remoteAddress() + " read error:", e);
            throw e;
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
            throws Exception {

        if (msg instanceof InternalMessage) {
            byte[] buf = null;
            InternalMessage internalMsg = (InternalMessage) msg;
            int protocolID = internalMsg.getProtocolID();
            int callback = internalMsg.getCallback();
            if (internalMsg.getMessage() instanceof AbstractCustomizeMessage) {
                AbstractCustomizeMessage message = (AbstractCustomizeMessage) internalMsg.getMessage();
                buf = message.encode();
            } else if (internalMsg.getMessage() instanceof GeneratedMessage) {
                GeneratedMessage protobufMsg = (GeneratedMessage) internalMsg.getMessage();
                buf = protobufMsg.toByteArray();
            } else if (internalMsg.getMessage() instanceof byte[]) {
                buf = (byte[]) internalMsg.getMessage();
            }

            int length = buf == null ? 9 : buf.length + 9; // messageType(1)+protocolID(4)+callback(4)
            ByteBuf outBuffer = null;// = ctx.alloc().buffer(length);

            try {
                // C2I MESSAGE_TYPE_CLIENT
                // messageType(byte)+secretKey(byte)+temp(byte)+dstServerID(int)+protocolID(int)+callback(int)+data

                outBuffer = ctx.alloc().buffer(length);
                outBuffer.writeByte(MESSAGE_TYPE_CLIENT);
                outBuffer.writeByte(0x4a);
                outBuffer.writeByte(0);

                Role role = ctx.channel().attr(Role.ROLEINFO).get();
                outBuffer.writeInt(role.getDestServerId(internalMsg.getMessage().getClass()));

                Connection connection = internalMsg.getConnection();
                NettyNode node = connection.getNettyNode();
                node.incSendAmount();

                outBuffer.writeInt(protocolID);
                outBuffer.writeInt(callback);
                if (buf != null) {
                    outBuffer.writeBytes(buf);
                }
                ctx.write(outBuffer, promise);

                logger.debug("write: protocol id is {}:{}, ip address {}, connectionid {} connection {} len {} sendAmount {}",
                        protocolID,
                        Integer.toHexString(protocolID),
                        node.getAddress(),
                        connection.getConnectionID(),
                        connection.hashCode(),
                        buf.length,
                        node.getSendAmount());
            } catch (Exception e) {
                if (outBuffer != null)
                    ReferenceCountUtil.release(outBuffer);
                logger.error("channel write error: " + e.toString());
                throw e;
            }
        } else {
            ctx.write(msg, promise);
        }
    }

    /**
     * Interface上，Client连接成功后，可以Active Game和Battle上由Interface通知Client的Active和Inactive
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        NettyNode remoteNode = new NettyNode(ctx.channel());
        Connection connection = new Connection(ConnectionManager.getInstance().generateConntionID(), remoteNode, IPUtils.ipToInt(remoteAddress.getHostString()));
        ConnectionManager.getInstance().addConnection(connection);
        ctx.channel().attr(Connection.CONNECTIONINFO).set(connection);
        MessageDispatcher.getInstance().connectionStatusDispatch(messageInitializer, connection, true);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)
            throws Exception {
        Connection connection = ctx.channel().attr(Connection.CONNECTIONINFO).get();
        ConnectionManager.getInstance().removeConnection(connection);
        MessageDispatcher.getInstance().connectionStatusDispatch(messageInitializer, connection, false);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        if (IOException.class.isAssignableFrom(cause.getClass())) {
            // 猜想是远程连接关
            logger.debug("exceptionCaught--Remote Peer:{} {}", ctx.channel().remoteAddress(), cause.toString());
        } else {
            logger.error("exceptionCaught {} --Exeption: {}", ctx.channel().remoteAddress(), cause.toString());
        }
    }

}
