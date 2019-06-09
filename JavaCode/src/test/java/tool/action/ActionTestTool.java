package tool.action;

/**
 * @author jiangzhen
 * @date 2019/4/11 19:09
 */
public class ActionTestTool {
//    private static final Logger logger = LoggerFactory.getLogger(ActionTestTool.class);
//
//    private ProtobufMessageHandler handler;
//
//    public ActionTestTool(ProtobufMessageHandler handler) {
//        this.handler = handler;
//    }
//
//    public void addTestTask(ActionTestInput input, ActionTestExpectOut expectOut) {
//        String testMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
//
//        logger.debug("Add action test task, testMethod={}", testMethodName);
//        Service.addRunAfterEngineStartTask(()->{
//            logger.debug("Start action test task, testMethod={}, input={}, expectedOut={}",
//                    testMethodName,
//                    input,
//                    expectOut);
//
//            Procedure.call(()->{
//                if (input.getDbInput() != null) {
//                    input.getDbInput().run();
//                }
//
//                MyTestConnection connection = input.getConnection();
//                PublicService publicService = input.getPublicService();
//                GeneratedMessage message = input.getMessage();
//                handler.handleMessage(connection, publicService, message, 0);
//
//                if (input.getClearDbInput() != null) {
//                    input.getClearDbInput().run();
//                }
//
//                return true;
//            });
//
//            // 判断输出消息
//            List<GeneratedMessage> actualOutMessageList = input.getConnection().getOutMessageList();
//            assertEquals(expectOut.getExpectMessageList(), actualOutMessageList);
//
//            if (expectOut.getDbComparator() != null) {
//                expectOut.getDbComparator().run();
//            }
//
//            logger.debug("Finish action test task, testMethod={}, actualMessageOutput={}",
//                    testMethodName,
//                    actualOutMessageList);
//        });
//    }
}
