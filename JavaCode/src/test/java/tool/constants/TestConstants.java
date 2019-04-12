package tool.constants;

import org.slf4j.Marker;
import org.slf4j.helpers.BasicMarkerFactory;

/**
 * @author jiangzhen
 * @date 2019/4/12 11:19
 */
public class TestConstants {
    private static final BasicMarkerFactory markerFactory = new BasicMarkerFactory();
    public static final Marker ZDB_ADD_RUN_TASK = markerFactory.getMarker("Zdb add run task");
}
