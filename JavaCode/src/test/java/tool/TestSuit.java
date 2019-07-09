package tool;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tool.zdb.ZdbTool;

/**
 * @author jiangzhen
 * @date 2019/4/12 9:46
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ZdbTool.class
})
public class TestSuit {

}
