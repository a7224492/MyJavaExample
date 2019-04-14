package tool.connection;

import com.google.protobuf.GeneratedMessage;
import com.kodgames.corgi.core.net.Connection;
import com.kodgames.corgi.core.net.common.NettyNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来作单元测试的connection
 *
 * @author jiangzhen
 * @date 2019/4/11 18:00
 */
public class MyTestConnection extends Connection {
    private List<GeneratedMessage> outMessageList = new ArrayList<>();

    public static MyTestConnection defaultTestConnection() {
        return new MyTestConnection(0, null, 0);
    }

    public MyTestConnection(int connectionId, NettyNode remote, int remotePeerIP) {
        super(connectionId, remote, remotePeerIP);
    }

    public List<GeneratedMessage> getOutMessageList() {
        return outMessageList;
    }

    @Override
    public void write(int callback, GeneratedMessage msg) {
        outMessageList.add(msg);
    }
}
