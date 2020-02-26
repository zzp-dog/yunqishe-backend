// DateUtil
package com.zx.yunqishe.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    private static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 获取时间字符串
     * @return
     */
    public static String dataTime2str(LocalDateTime ldt, String pattern) {
        if (null == pattern) pattern = DATE_TIME;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return ldt.format(df);
    }

    /**
     * localDateTime转时间戳
     * @return
     */
    public static Long timeStamp(LocalDateTime ldt) {
        if (null == ldt) ldt = LocalDateTime.now();
        return ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 时间戳转LocalDateTime
     * @param l 时间戳
     * @return
     */
    public static LocalDateTime dateTime(Long l) {
        if (null == l) return LocalDateTime.now();
        return LocalDateTime.ofEpochSecond(l/1000,0,ZoneOffset.ofHours(8));
    }


    /**
     * 字符串变日期时间
     * @param str
     * @param pattern
     * @return
     */
    public static LocalDateTime str2dataTime(String str, String pattern) {
        if (null == pattern) pattern = DATE_TIME;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_TIME);
        return LocalDateTime.parse(str, df);
    }

    /**
     * 返回时间距离当前时间的毫秒数
     * @param dt
     * @return
     */
    public static Long diffNow(Object dt) {
       Long idt = 0L;
       Long now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
       LocalDateTime ldt = null;
       if (dt instanceof String) {
            ldt = str2dataTime((String)dt, null);
       } else if (dt instanceof LocalDateTime){
            ldt = (LocalDateTime)dt;
       } else if (dt instanceof Date) {
            ldt = date2localDateTime((Date)dt);
       }
       if (null != ldt)
           idt = ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
       return now - idt;
    }

    /**
     * java.util.date => java.time.LocalDateTime
     * @param dt
     * @return
     */
    public static LocalDateTime date2localDateTime(Date dt) {
        return dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * java.time.LocalDateTime => java.util.Date
     * @param ldt
     * @return
     */
    public static Date date2localDateTime(LocalDateTime ldt) {
        return Date.from(ldt.atZone( ZoneId.systemDefault()).toInstant());
    }
}
