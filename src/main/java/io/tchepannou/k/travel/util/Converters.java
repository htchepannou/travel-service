package io.tchepannou.k.travel.util;

import com.google.common.base.Strings;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converters {
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

        SimpleDateFormat fmt = new SimpleDateFormat(pattern, Locale.US);
//        fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        return fmt.parse(value);
    }
}
