package com.vinesmario.common.kit;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期处理相关工具类
 */
public class DateKit {
    /**
     * 日期格式枚举类，根据需要添加其他格式
     **/
    public enum DatePattern {
        DATE_FULL("yyyy-MM-dd'T'HH:mm:ss,SSS", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2},\\d{3}$"),
        ISO_SECOND("yyyy-MM-dd'T'HH:mm:ss", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$"),
        ISO_MINUTE("yyyy-MM-dd'T'HH:mm", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$"),
        ISO_HOUR("yyyy-MM-dd'T'HH", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}$"),
        DATE_TIME_FULL("yyyy-MM-dd HH:mm:ss,SSS", "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}$"),
        DATE_TIME("yyyy-MM-dd HH:mm:ss", "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$"),
        DATE_ONLY("yyyy-MM-dd", "^\\d{4}-\\d{2}-\\d{2}$"),
        TIME_ONLY("HH:mm:ss", "^\\d{2}:\\d{2}:\\d{2}$"),
        YEAR_MONTH("yyyy-MM", "^\\d{4}-\\d{2}$"),
        MONTH_DAY("MM-dd", "^\\d{2}-\\d{2}$"),
        HOUR_MINUTE("HH:mm", "^\\d{2}:\\d{2}$"),
        DATE_TIME_YMDHMS("yyyyMMddHHmmss", "^\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}\\d{2}$"),
        DATE_TIME_YMD("yyyyMMdd", "^\\d{4}\\d{2}\\d{2}$");

        DatePattern(String pattern, String regex) {
            this.pattern = pattern;
            this.regex = regex;
        }

        public String getPattern() {
            return pattern;
        }

        public String getRegex() {
            return regex;
        }

        private String pattern;
        private String regex;

        /**
         * 根据日期字符串，判断该日期的格式类型。
         *
         * @param dateStr 日期字符串
         * @return 日期的格式类型，比如getPatternByDateStr("2016-04-27 10:15:08")返回："yyyy-MM-dd HH:mm:ss"
         */
        public static DatePattern getPatternByDateStr(String dateStr) {
            for (DatePattern df : DatePattern.values()) {
                if (RegexKit.matches(dateStr, df.getRegex())) {
                    return df;
                }
            }
            return null;
        }
    }

    /**
     * 获取系统当前时间字符串（"yyyy-MM-dd HH:mm:ss"）
     *
     * @return
     */
    public static String nowToString() {
        return nowToString(DatePattern.DATE_TIME);
    }

    /**
     * 获取系统当前时间，指定格式
     *
     * @return
     */
    public static String nowToString(DatePattern pattern) {
        return localDateTimeToString(LocalDateTime.now(), pattern);
    }

    /**
     * 获取系统当前日期 字符串（"yyyy-MM-dd"）
     *
     * @return
     */
    public static String todayToString() {
        return todayToString(DatePattern.DATE_ONLY);
    }

    /**
     * 获取系统当前日期，指定格式
     *
     * @return
     */
    public static String todayToString(DatePattern pattern) {
        return localDateToString(LocalDate.now(), pattern);
    }

    /**
     * 获取昨日 字符串（"yyyy-MM-dd"）
     *
     * @return
     */
    public static String yesterdayToString() {
        return yesterdayToString(DatePattern.DATE_ONLY);
    }

    /**
     * 获取昨日，指定格式
     *
     * @return
     */
    public static String yesterdayToString(DatePattern pattern) {
        return localDateToString(LocalDate.now().minusDays(1), pattern);
    }

    // TODO JDK8 time

    /**
     * 把字符串转换为LocalDateTime类型
     *
     * @param dateStr 格式必须为：“yyyy-MM-dd HH:mm:ss”
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String dateStr) {
        return stringToLocalDateTime(dateStr, DatePattern.getPatternByDateStr(dateStr));
    }

    /**
     * 把字符串转换为LocalDateTime类型
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String dateStr, DatePattern pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getPattern());
        return LocalDateTime.parse(dateStr, df);
    }

    /**
     * 转换long类型的timestamp为LocalDateTime类型
     *
     * @param timestamp
     * @return LocalDateTime
     */
    public static LocalDateTime timestampToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
    }

    /**
     * 把字符串转换为YearMonth类型
     *
     * @param dateStr 格式必须为：“yyyy-MM”
     * @return
     */
    public static YearMonth stringToYearMonth(String dateStr) {
        return stringToYearMonth(dateStr, DatePattern.getPatternByDateStr(dateStr));
    }

    /**
     * 把字符串转换为YearMonth类型
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static YearMonth stringToYearMonth(String dateStr, DatePattern pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getPattern());
        return YearMonth.parse(dateStr, df);
    }

    /**
     * 把字符串转换为MonthDay类型
     *
     * @param dateStr 格式必须为：“MM-dd”
     * @return
     */
    public static MonthDay stringToMonthDay(String dateStr) {
        return stringToMonthDay(dateStr, DatePattern.getPatternByDateStr(dateStr));
    }

    /**
     * 把字符串转换为YearMonth类型
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static MonthDay stringToMonthDay(String dateStr, DatePattern pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getPattern());
        return MonthDay.parse(dateStr, df);
    }

    /**
     * 把字符串转换为LocalDate类型
     *
     * @param dateStr 格式必须为：“yyyy-MM-dd”
     * @return
     */
    public static LocalDate stringToLocalDate(String dateStr) {
        return stringToLocalDate(dateStr, DatePattern.getPatternByDateStr(dateStr));
    }

    /**
     * 把字符串转换为LocalDate类型
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static LocalDate stringToLocalDate(String dateStr, DatePattern pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getPattern());
        return LocalDate.parse(dateStr, df);
    }

    /**
     * 转换long类型的timestamp为LocalDate类型
     *
     * @param timestamp
     * @return LocalDate
     */
    public static LocalDate timestampToLocalDate(long timestamp) {
        return timestampToLocalDateTime(timestamp).toLocalDate();
    }

    /**
     * 把字符串转换为LocalDateTime类型
     *
     * @param dateStr 格式必须为：“HH:mm:ss”
     * @return
     */
    public static LocalTime stringToLocalTime(String dateStr) {
        return stringToLocalTime(dateStr, DatePattern.getPatternByDateStr(dateStr));
    }

    /**
     * 把字符串转换为LocalDateTime类型
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static LocalTime stringToLocalTime(String dateStr, DatePattern pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getPattern());
        return LocalTime.parse(dateStr, df);
    }


    /**
     * 把字符串转换为Instant类型
     *
     * @param dateStr 格式必须为：“yyyy-MM-dd HH:mm:ss”
     * @return
     */
    public static Instant stringToInstant(String dateStr) {
        return stringToInstant(dateStr, DatePattern.getPatternByDateStr(dateStr));
    }

    /**
     * 把字符串转换为Instant类型
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Instant stringToInstant(String dateStr, DatePattern pattern) {
        LocalDateTime localDateTime = stringToLocalDateTime(dateStr, pattern);
        if (null == localDateTime) {
            return null;
        }
        return localDateTime.atZone(Clock.systemDefaultZone().getZone()).toInstant();
    }

    /**
     * 把为LocalDateTime类型转换为字符串
     *
     * @param localDateTime
     * @return
     */
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTimeToString(localDateTime, DatePattern.DATE_TIME);
    }

    /**
     * 把为LocalDateTime类型转换为字符串
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, DatePattern pattern) {
        if (null == localDateTime) {
            return null;
        }
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getPattern());
        return localDateTime.format(df);
    }

    /**
     * 把LocalDate类型转换为字符串
     *
     * @param localDate 格式必须为：“2016-05-31”
     * @return
     */
    public static String localDateToString(LocalDate localDate) {
        return localDateToString(localDate, DatePattern.DATE_ONLY);
    }

    /**
     * 把为LocalDate类型转换为字符串
     *
     * @param localDate
     * @param pattern
     * @return
     */
    public static String localDateToString(LocalDate localDate, DatePattern pattern) {
        if (null == localDate) {
            return null;
        }
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getPattern());
        return localDate.format(df);
    }

    /**
     * 把为LocalDateTime类型转换为字符串
     *
     * @param localTime
     * @return
     */
    public static String localTimeToString(LocalTime localTime) {
        return localTimeToString(localTime, DatePattern.TIME_ONLY);
    }

    /**
     * 把为LocalDateTime类型转换为字符串
     *
     * @param localTime
     * @param pattern
     * @return
     */
    public static String localTimeToString(LocalTime localTime, DatePattern pattern) {
        if (localTime == null) {
            return null;
        }
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getPattern());
        return localTime.format(df);
    }

    /**
     * 把为Instant类型转换为字符串
     *
     * @param instant
     * @return
     */
    public static String instantToString(Instant instant) {
        return instantToString(instant, DatePattern.DATE_TIME);
    }

    /**
     * 把为Instant类型转换为字符串
     *
     * @param instant
     * @param pattern
     * @return
     */
    public static String instantToString(Instant instant, DatePattern pattern) {
        if (instant == null) {
            return null;
        }
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getPattern());
        return instant.atZone(ZoneId.systemDefault()).format(df);
    }

    // TODO Date

    /**
     * 字符串转换为Date对象，自动匹配日期格式
     *
     * @param dateStr 日期字符串
     * @return Date
     */
    public static Date stringToDate(String dateStr) {
        return stringToDate(dateStr, DatePattern.getPatternByDateStr(dateStr));
    }

    /**
     * 把字符串转换为Date类型
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date stringToDate(String strDate, DatePattern pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern.getPattern());
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将指定的日期转换成long时间戳
     *
     * @param dateStr dateStr 需要转换的日期，自动匹配日期格式
     * @return long 时间戳，单位：秒
     */
    public static long stringToTimestamp(String dateStr) {
        return stringToTimestamp(dateStr, DatePattern.getPatternByDateStr(dateStr));
    }

    /**
     * 将指定的日期转换成long时间戳
     *
     * @param dateStr date 需要转换的日期
     * @param pattern dateFormat 需要转换的日期格式
     * @return long 时间戳，单位：秒
     */
    public static long stringToTimestamp(String dateStr, DatePattern pattern) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(pattern.getPattern()).parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp / 1000;
    }

    /**
     * 日期转String
     *
     * @return
     */
    public static String dateToString(Date date, DatePattern pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern.getPattern());
        return df.format(date);
    }

    /**
     * 将long时间戳转换成日期
     *
     * @param timestamp timestamp 时间戳，单位：秒
     * @return String 日期字符串
     */
    public static Date timestampToDate(long timestamp) {
        SimpleDateFormat sd = new SimpleDateFormat(DatePattern.DATE_TIME.getPattern());
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return new Date(timestamp * 1000);
    }

    /**
     * 将long时间戳转换pattern格式的String
     *
     * @param timestamp
     * @param pattern   格式
     * @return 格式化后的时间
     */
    public static String timestampToString(long timestamp, DatePattern pattern) {
        Date date = timestampToDate(timestamp);
        SimpleDateFormat df = new SimpleDateFormat(pattern.getPattern());
        return df.format(date);
    }

    /**
     * 将当前日期转换成long时间戳
     *
     * @return long 时间戳，单位：秒
     */
    public static long nowToTimestamp() {
        long timestamp = Instant.now().toEpochMilli();
        return timestamp / 1000;
    }

    /**
     * 和当前时间比较（Date格式）
     *
     * @param date1
     * @return 负数：之前；0：当前；正数：之后
     */
    public static int compareDateWithNow(Date date1) {
        Date date2 = new Date();
        int rnum = date1.compareTo(date2);
        return rnum;
    }

    /**
     * 和当前时间比较(时间戳比较)
     *
     * @param date1
     * @return
     */
    public static int compareDateWithNow(long date1) {
        long date2 = nowToTimestamp();
        if (date1 > date2) {
            return 1;
        } else if (date1 < date2) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 获取几天前00:00:00的时间戳
     *
     * @param d 距离现在d天前
     * @return 时间戳，以秒为单位
     */
    public static long getMinusDay(int d) {
        LocalDate now = LocalDate.now();
        return now.minusDays(d).atStartOfDay().toEpochSecond(ZoneOffset.ofHours(8));
    }

    /**
     * 获取距离现在m个月前00:00:00的时间戳
     *
     * @param m 距离现在m个月前
     * @return 时间戳，以秒为单位
     */
    public static long getMinusMonth(int m) {
        LocalDate now = LocalDate.now();
        return now.minusMonths(m).atStartOfDay().toEpochSecond(ZoneOffset.ofHours(8));
    }

    public static String getDayOfWeek(LocalDate d) {
        DayOfWeek dayOfWeek = d.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.SIMPLIFIED_CHINESE);
    }

    /**
     * 判断是否闰年
     *
     * @param year
     * @return
     */
    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * 是否为润年
     *
     * @param date
     * @return
     */
    public boolean isLeapYear(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        return isLeapYear(year);
    }

//    public static void main(String[] args) {
//        System.out.println(stringToTimestamp("2016-05-05T15:22:09"));
//        System.out.println(stringToTimestamp("2016-05-05 15:22:09"));
//        System.out.println(stringToTimestamp("2016-05-05"));
//        System.out.println(getMinusMonth(1));
//        System.out.println(LocalDateTime.ofEpochSecond(getMinusMonth(2), 0, ZoneOffset.ofHours(8)));
//
//        System.out.println(LocalDateTime.ofEpochSecond(getMinusDay(0), 0, ZoneOffset.ofHours(8)));
//        System.out.println(LocalDateTime.ofEpochSecond(getMinusDay(1), 0, ZoneOffset.ofHours(8)));
//
//        LocalDate now = LocalDate.now();
//        System.out.println(getDayOfWeek(now));
//
//        LocalDate ld = stringToLocalDate("2015-05-29", DatePattern.DATE_ONLY);
//        System.out.println(ld);
//
//        System.out.println("defult zone Id: " + TimeZone.getDefault().toZoneId());
//        System.out.println("timestamp to LocalDate: " + timestampToLocalDate(1462432929));
//        System.out.println("timestamp to LocalDateTime: " + timestampToLocalDateTime(1462432929));
//    }

}
