package tool.zdb;

import limax.xmlconfig.Service;

/**
 * @author jiangzhen
 * @date 2019/4/12 11:59
 */
public class Test {
    @org.junit.Test
    public void testZdbUnit() {
        Service.addRunAfterEngineStartTask(Service::stop);
        Service.asyncRun(Object.class.getResource("/zdb_config.xml").getPath());
    }
}
