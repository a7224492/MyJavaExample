package com.mycode.zdb;

/**
 * @author jiangzhen
 * @date 2019/5/10 13:46
 */
public class Test {
    public static void main(String[] args) throws Exception {
    	int x = 10;
    	if (x < 10)
    		if (x < 5)
    			System.out.println(x);
    		else
    			System.out.println(11);
//        Service.addRunAfterEngineStartTask(() -> {
//            try
//            {
//                new Thread(()->{
//                    Procedure.call(()->{
//                        Role_info.update(10000);
//
//                        Thread.sleep(2000);
//
//                        Role_info.update(10001);
//
//                        return true;
//                    });
//                }).start();
//
//                Procedure.call(()->{
//                    Role_info.update(10001);
//
//                    Thread.sleep(2000);
//
//                    Role_info.update(10000);
//
//                    return true;
//                });
//            }
//            catch (Exception e)
//            {
//            }
//        });
//
//        Service.run(Object.class.getResource("/zdb_config.xml").getPath());
    }
}
