package com.mycode.research.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author jiangzhen
 * @date 2019/5/9 10:10
 */
public class ClientConnect {
    public static void client(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = null;
        try
        {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));
            if(socketChannel.finishConnect())
            {
                    String info = "I'm jiangzhen";
                    buffer.clear();
                    buffer.put(info.getBytes());
                    buffer.flip();
                    socketChannel.write(buffer);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(socketChannel!=null){
                    socketChannel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        client();
    }
}
