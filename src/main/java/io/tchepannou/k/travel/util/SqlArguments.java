package io.tchepannou.k.travel.util;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SqlArguments {
    public static List in(List values){
        if (CollectionUtils.isEmpty(values)){
            return Collections.emptyList();
        }
        return values.size() == 1
            ? Collections.singletonList(values.get(0))
            : values;
    }

    public static List between(Object from, Object to){
        if (from == null || to == null){
            return Collections.emptyList();
        }
        if (from == null){
            return Collections.singletonList(to);
        } else if (to == null){
            return Collections.singletonList(from);
        } else {
            return Arrays.asList(from, to);
        }
    }

}
