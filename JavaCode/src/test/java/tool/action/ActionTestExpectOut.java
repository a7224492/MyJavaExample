package tool.action;

import com.google.protobuf.GeneratedMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试的期望输出
 *
 * @author jiangzhen
 * @date 2019/4/11 18:04
 */
public class ActionTestExpectOut {
    /**
     * 输出消息
     */
    private List<GeneratedMessage> expectMessageList = new ArrayList<>();

    /**
     * 持久化数据期望输出
     */
    private Runnable dbComparator;

    public ActionTestExpectOut(List<GeneratedMessage> expectMessageList, Runnable dbComparator) {
        this.expectMessageList = expectMessageList;
        this.dbComparator = dbComparator;
    }

    public List<GeneratedMessage> getExpectMessageList() {
        return expectMessageList;
    }

    public Runnable getDbComparator() {
        return dbComparator;
    }

    public ActionTestExpectOut() {
    }

    public void setExpectMessageList(List<GeneratedMessage> expectMessageList) {
        this.expectMessageList = expectMessageList;
    }

    public void setDbComparator(Runnable dbComparator) {
        this.dbComparator = dbComparator;
    }

    @Override
    public String toString() {
        return "ActionTestExpectOut{" +
                "expectMessageList=" + expectMessageList +
                ", dbComparator=" + dbComparator +
                '}';
    }
}
