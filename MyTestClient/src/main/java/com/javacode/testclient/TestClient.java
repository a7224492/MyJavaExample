package com.javacode.testclient;

import com.javacode.testclient.net.NetInitializer;
import com.kodgames.core.event.EventManager;
import com.kodgames.corgi.core.net.handler.message.MessageDispatcher;

/**
 * @author jiangzhen
 * @date 2019/4/18 15:59
 */
public class TestClient {
    public static void main(String[] args) throws Exception {
        MessageDispatcher.getInstance().setOpenZDBProcedure(false);
        EventManager.getInstance().setOpenZDBProcedure(false);

        NetInitializer.getInstance().init();
    }
}
