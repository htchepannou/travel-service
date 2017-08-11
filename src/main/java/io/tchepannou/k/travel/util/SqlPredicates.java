package io.tchepannou.k.travel.util;

import com.google.common.base.Joiner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SqlPredicates {
    public static String in(String column, List values){
        if (CollectionUtils.isEmpty(values)){
            return null;
        }
        return values.size() == 1
            ? String.format("%s=?", column)
            : String.format("%s IN (%s)", column, toParams(values));
    }

    public static String between(String column, Object from, Object to){
        if (from == null || to == null){
            return null;
        }
        if (from == null){
            return String.format("%s >= ?", column);
        } else if (to == null){
            return String.format("%s <= ?", column);
        } else {
            return String.format("%s BETWREN ? AND ?");
        }
    }


    private static String toParams(List values){
        return Joiner.on(",").join(
                (List)values.stream().map(i -> "?").collect(Collectors.toList())
        );
    }
}
