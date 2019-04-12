package tool.action;

import com.google.protobuf.GeneratedMessage;
import com.kodgames.corgi.core.service.PublicService;
import tool.connection.MyTestConnection;

/**
 * @author jiangzhen
 * @date 2019/4/11 17:58
 */
public class ActionTestInput {
    private MyTestConnection connection;
    private PublicService publicService;
    private GeneratedMessage message;
    private Runnable dbInput;
    private Runnable clearDbInput;

    public ActionTestInput() {
    }

    public ActionTestInput(MyTestConnection connection, PublicService publicService, GeneratedMessage message) {
        this.connection = connection;
        this.publicService = publicService;
        this.message = message;
    }

    public void setConnection(MyTestConnection connection) {
        this.connection = connection;
    }

    public void setPublicService(PublicService publicService) {
        this.publicService = publicService;
    }

    public void setMessage(GeneratedMessage message) {
        this.message = message;
    }

    public MyTestConnection getConnection() {
        return connection;
    }

    public PublicService getPublicService() {
        return publicService;
    }

    public GeneratedMessage getMessage() {
        return message;
    }

    public Runnable getDbInput() {
        return dbInput;
    }

    public void setDbInput(Runnable dbInput) {
        this.dbInput = dbInput;
    }

    public Runnable getClearDbInput() {
        return clearDbInput;
    }

    public void setClearDbInput(Runnable clearDbInput) {
        this.clearDbInput = clearDbInput;
    }

    @Override
    public String toString() {
        return "ActionTestInput{" +
                "connection=" + connection +
                ", publicService=" + publicService +
                ", message=" + message +
                ", dbInput=" + dbInput +
                ", clearDbInput=" + clearDbInput +
                '}';
    }
}
