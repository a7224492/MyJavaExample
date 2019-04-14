package tool.zdb;

import limax.xmlconfig.Service;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author jiangzhen
 * @date 2019/4/12 10:11
 */
public class ZdbTool {
    private static final Logger logger = LoggerFactory.getLogger(ZdbTool.class);
    private CountDownLatch latch = new CountDownLatch(1);

    @Test
    public void startZdb() throws InterruptedException {
        Service.addRunAfterEngineStartTask(() -> {
            latch.countDown();
        });
        Service.asyncRun(ClassLoader.getSystemResource("zdb_config.xml").getPath());
        latch.await();
    }
}
