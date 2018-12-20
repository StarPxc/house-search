package yuqiao.housesearch.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 浦希成
 * 2018-12-18
 */
public class DateUtil {
    private DateUtil(){

    }
    public static String getCurrentDateString() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }
}
