package com.pp100.seal.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import com.pp100.utils.Util;

public class DateUtil {
    private static final Logger logger = Logger.getLogger(DateUtil.class);


    /**
     * 获取当前时间的字符串 格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String dateFormatter(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeString = dataFormat.format(date);
        return timeString;
    }

    public static String dateToStr(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        String timeString = dataFormat.format(date);
        return timeString;
    }

    public static Date strToDate(String date, String format) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat(format);
        try {
            return dataFormat.parse(date);
        } catch (ParseException e) {
            logger.error("时间格式转换！", e);
        }
        return null;
    }

    /**
     * 取月差（不足月不计算）
     *
     * @return
     * @author hejun
     * @created 2015-10-27 下午02:05:12
     */
    public static int getMonthSpace(Date baseDate, Date followDate) {
        if (null == baseDate || null == followDate) {
            logger.error("计算剩余月份，传入参数为空！");
            return 0;
        }

        if (baseDate.getTime() > followDate.getTime()) {
            return 0;
        }

        int diffMoths = 0;

        try {
            Calendar baseCalendar = Calendar.getInstance();
            baseCalendar.setTime(baseDate);
            int baseYear = baseCalendar.get(Calendar.YEAR);
            int baseMoth = baseCalendar.get(Calendar.MONTH);
            int baseDay = baseCalendar.get(Calendar.DATE);

            Calendar finalCalendar = Calendar.getInstance();
            finalCalendar.setTime(followDate);
            int finalRepayYear = finalCalendar.get(Calendar.YEAR);
            int finalRepayMoth = finalCalendar.get(Calendar.MONTH);
            int finalRepayDay = finalCalendar.get(Calendar.DATE);

            diffMoths = (finalRepayYear - baseYear) * 12 + finalRepayMoth - baseMoth + (finalRepayDay - baseDay >= 0 ? 0 : -1);
        } catch (Exception e) {
            logger.error("计算剩余月份异常！", e);
        }

        return diffMoths;
    }

    /**
     * 取相差天数（忽略月）
     *
     * @return
     * @author hejun
     * @created 2015-10-27 下午03:30:17
     */
    public static int getDaySpace(Date baseDate, Date followDate) {
        if (null == baseDate || null == followDate) {
            logger.error("计算剩余天数，传入参数为空！");
            return 0;
        }

        if (baseDate.getTime() > followDate.getTime()) {
            return 0;
        }

        int diffDays = 0;

        try {
            Calendar baseCalendar = Calendar.getInstance();
            baseCalendar.setTime(baseDate);
            int baseDay = baseCalendar.get(Calendar.DATE);

            Calendar finalCalendar = Calendar.getInstance();
            finalCalendar.setTime(followDate);
            int finalRepayDay = finalCalendar.get(Calendar.DATE);

            if (finalRepayDay < baseDay) {
                finalCalendar.add(Calendar.MONTH, -1);
                Date preMonth = finalCalendar.getTime();
                long mDays = (followDate.getTime() - preMonth.getTime()) / 86400000;
                diffDays = (int) (mDays + finalRepayDay - baseDay);
            } else {
                diffDays = finalRepayDay - baseDay;
            }
        } catch (Exception e) {
            logger.error("计算剩余天数异常！", e);
        }

        return diffDays;
    }

    /**
     * 获取当前时间的字符串 格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeString = sdf.format(date);
        return timeString;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            long between_days = (bdate.getTime() - smdate.getTime()) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            logger.error("计算两个日期之间相差的天数！", e);
        }
        return 0;
    }

    // java.time.LocalDateTime --> java.util.Date
    public static Date LocalDateTimeToUdate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 计算两个时间之间相差的天数，不满一天按一天算
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffDays(Date startDate, Date endDate) {
        long start = startDate.getTime();
        long end = endDate.getTime();
        double days = Math.ceil((end - start) / 86400000d);    // 一天的毫秒数1000 * 60 * 60 * 24=86400000
        return Long.parseLong(String.format("%.0f", days));
    }

    /**
     * /** 某一个月第一天和最后一天
     *
     * @param date
     * @return
     */
    public static Map<String, String> getFirstday_Lastday_Month(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }

    /**
     * 获取该月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.truncate(date, Calendar.DATE));
        calendar.add(Calendar.MONTH, 1);    //再减一天即为上个月最后一天
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        return calendar.getTime();
    }

    /**
     * 获取该日期 本周的最后一天
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.truncate(date, Calendar.DATE));
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekDay == Calendar.SUNDAY) {
            return calendar.getTime();
        } else {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.add(Calendar.DATE, 6);
        }
        return calendar.getTime();
    }

    /**
     * 获取最近90天全日期的记录
     *
     * @param date
     * @return
     */
    public static List<Date> getThreeMonthDays(Date date) {
        date = DateUtils.truncate(date, Calendar.DATE);
        List<Date> list = new ArrayList<Date>();
        for (int i = 1; i <= 90; i++) {
            Calendar riqi = Calendar.getInstance();
            riqi.setTime(date);
            riqi.add(Calendar.DATE, -i);
            Date dayDate = DateUtils.truncate(riqi.getTime(), Calendar.DATE);
            list.add(dayDate);
        }
        return list;
    }

    public static List<Date> getPastTwelveWeek(Date date) {
        List<Date> list = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTime(DateUtils.truncate(date, Calendar.DATE));
        for (int i = 0; i < 13; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            list.add(calendar.getTime());
            calendar.add(Calendar.DATE, -7);
        }
        return list;
    }

    /**
     * 获取当前date的当天起始时间点
     *
     * @param date
     * @return
     */
    public static Date getStartTimePointOfCurrentDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar cal = new Calendar.Builder().setDate(year, month, day)
                .setTimeOfDay(0, 0, 0, 0)
                .build();
        return cal.getTime();
    }

    /**
     * 获取当前date的当天最末时间点
     *
     * @param date
     * @return
     */
    public static Date getEndTimePointOfCurrentDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar cal = new Calendar.Builder().setDate(year, month, day)
                .setTimeOfDay(23, 59, 59, 999)
                .build();
        return cal.getTime();
    }

    /**
     * 距离当天differDays天的结束时间
     *
     * @param date       时间
     * @param differDays 距离date的天数
     * @return 计算后的时间
     */
    public static Date endDate(Date date, int differDays) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        long diffSeconds = (24L * 60 * 60 * 1000) * (differDays + 1);
        Date date2 = new Date(date.getTime() - gc.get(gc.HOUR_OF_DAY) * 60 * 60 * 1000 - gc.get(gc.MINUTE) * 60 * 1000 - gc.get(gc.SECOND) * 1000 - 1000 + diffSeconds - gc.get(gc.MILLISECOND));
        return date2;
    }

    /**
     * 时间+value（MONTH,DATE,HOUR..）之后的值
     *
     * @param date  被加的时间
     * @param type  时间类型 MONTH,DATE,HOUR..
     * @param value
     * @return
     */
    public static Date add(Date date, int type, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }

    /**
     * 星期几
     *
     * @param dt
     * @return
     * @author xiaoby
     * @version 1.0
     * @created 2015年8月9日 下午12:45:48
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    /**
     * 获取当前时间的字符串 格式：yyyyMMddHHmmss
     *
     * @return
     */
    public static String getDateyyyyMMddHHmmss() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = Util.now();
        return dataFormat.format(date);
    }

    /**
     * 获取当前时间的字符串 格式：yyyyMMdd
     *
     * @return
     */
    public static String getDateyyyyMMdd() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = Util.now();
        return dataFormat.format(date);
    }

    /**
     * 根据日期获取时间字符串 格式：yyyyMMddHHmmss
     *
     * @return
     */
    public static String getDateyyyyMMddHHmmss(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeString = dataFormat.format(date);
        return timeString;
    }

    /**
     * 根据日期获取时间字符串 格式：yyyyMMdd
     *
     * @return
     */
    public static String getDateyyyyMMdd(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
        String timeString = dataFormat.format(date);
        return timeString;
    }

    /**
     * 获取某个日期第二天的14:00
     *
     * @param date
     * @return
     */
    public static Date getDateOfTwoOClock(Date date) {
        date = DateUtils.truncate(date, Calendar.HOUR_OF_DAY);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.add(Calendar.DATE, 1);
        Date dayDate = DateUtils.truncate(calendar.getTime(), Calendar.HOUR_OF_DAY);
        return dayDate;
    }

    /**
     * 获取某个日期前一天的21:00
     *
     * @param date
     * @return
     */
    public static Date getDateOfNineOClock(Date date) {
        date = DateUtils.truncate(date, Calendar.HOUR_OF_DAY);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.add(Calendar.DATE, -1);
        Date dayDate = DateUtils.truncate(calendar.getTime(), Calendar.HOUR_OF_DAY);
        return dayDate;
    }
    
    /**
     * 判断日期是否在startDate和endDate之间
     * @param args
     */
    public static boolean isDateSection(Date date, String startDate, String endDate) {
        Date start = strToDate(startDate, "yyyy-MM-dd HH:mm:ss");
        Date end = strToDate(endDate, "yyyy-MM-dd HH:mm:ss");
        return date.getTime() >= start.getTime() && date.getTime() < end.getTime();
    }
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月
     * @return
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     * @return
     */
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取日
     * @return
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String simple(Date date) {
        if(null == date)
            return "";

        return new SimpleDateFormat("yyyyMMdd").format(date);
    }
    
    /**
     * 毫秒数转时间
     */
    public static Date millSecToDate(Long millSec) {
        return new Date(millSec);
    }
    
    /**
     * 格式转换（将字符串的时间转换为YYYY年MM月DD日）
     * @param dateString
     * @return
     */
    public static String dateStringFormart(String dateString) {
        String newDateString = "";
        if (StringUtils.isBlank(dateString)) {
            return newDateString;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(dateString);// 有异常要捕获
            format = new SimpleDateFormat("yyyy年MM月dd日");
            newDateString = format.format(date);
        } catch (Exception e) {
            logger.error("格式转换（将字符串的时间转换为YYYY年MM月DD日）！", e);
        }
        return newDateString;
    }
}
