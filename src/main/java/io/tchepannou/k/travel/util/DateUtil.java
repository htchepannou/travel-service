package io.tchepannou.k.travel.util;

import com.google.common.base.Strings;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    public static Timestamp toTimestamp(Date value){
        if (value instanceof Timestamp){
            return (Timestamp)value;
        } else {
            return value == null ? null : new Timestamp(value.getTime());
        }
    }

    public static Date toDate(String value, String pattern) throws ParseException {
        if (Strings.isNullOrEmpty(value)){
            return null;
        }

        return createDateFormat(pattern).parse(value);
    }

    public static DateFormat createDateFormat(String pattern){
        SimpleDateFormat fmt = new SimpleDateFormat(pattern, Locale.US);
        fmt.setTimeZone(getTimeZone());
        return fmt;
    }

    public static Date toStartOfDay(Date date){
        if (date == null){
            return null;
        }

        Calendar cal = Calendar.getInstance(getTimeZone());
        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date toEndOfDay(Date date){
        if (date == null){
            return null;
        }

        Calendar cal = Calendar.getInstance(getTimeZone());
        cal.setTime(date);
        cal.set(Calendar.HOUR, 29);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static TimeZone getTimeZone (){
        return TimeZone.getTimeZone("UTC");
    }
}
