package tool.threadpool;

import com.mycode.common.constants.Constants;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangzhen
 * @date 2019/5/7 11:36
 */
public class R1 {
    private static final int TASK_NUM = 10000;

    @Test
    public void test1() throws InterruptedException {
        // 701
        test(Executors.newFixedThreadPool(8));
    }

    @Test
    public void test1_1() throws InterruptedException {
        // 701
        test(Executors.newFixedThreadPool(35));
    }

    @Test
    public void test1_2() throws InterruptedException {
        // 701
        test(Executors.newFixedThreadPool(55));
    }

    @Test
    public void test2() throws InterruptedException {
        // 1170
        test(Executors.newFixedThreadPool(70));
    }

    @Test
    public void test3() throws InterruptedException {
        // 1196
        test(Executors.newFixedThreadPool(100));
    }

    private void test(ExecutorService threadPool) throws InterruptedException {
        long before = System.currentTimeMillis();
        for (int i = 0; i < TASK_NUM; ++i) {
            threadPool.execute(new Constants.LongTimeTask());
//            Thread.sleep(1);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        long cost = System.currentTimeMillis() - before;
        float thought = 1000 * TASK_NUM / (float)cost;

        System.out.println(thought);
    }
}
