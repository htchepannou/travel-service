package io.tchepannou.k.travel.service;

import com.google.common.base.Joiner;
import io.tchepannou.k.travel.client.request.SearchPriceRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceSearchQueryBuilder {
    public String toSql(SearchPriceRequest request){
        final List<String> predicates = new ArrayList<>();

        predicates.add(predicate("travel_product_fk", request.getProductIds()));
        predicates.add(predicate("UCASE(T.name)", request.getPriceTypeNames()));
        if (request.getDepartureDate() != null) {
            predicates.add("(from_datetime IS NULL OR from_datetime<=?) AND (to_datetime IS NULL OR to_datetime>=?)");
        }

        final List<String> where = predicates.stream()
                .filter(p -> p != null)
                .collect(Collectors.toList());

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT P.*, T.name FROM T_PRICE P JOIN T_PRICE_TYPE T ON P.price_type_fk=T.id");
        if (!where.isEmpty()){
            sql
                    .append(" WHERE ")
                    .append(Joiner.on(" AND ").join(where));
        }
        sql.append(" ORDER BY P.id, P.price_type_fk");
        return sql.toString();
    }

    public Object[] toArgs(SearchPriceRequest request){
        final List args = new ArrayList();
        addAll(request.getProductIds(), args);
        addAll(request.getPriceTypeNames().stream().map(n -> n.toUpperCase()).collect(Collectors.toList()), args);

        final Date departureDate = request.getDepartureDate();
        if (departureDate != null) {
            addAll(Arrays.asList(departureDate, departureDate), args);
        }

        return args.toArray();
    }

    //-- Private
    private String predicate(String column, List values){
        if (CollectionUtils.isEmpty(values)){
            return null;
        }
        return String.format("%s IN (%s)", column, toParams(values));
    }

    private String toParams(List values){
        return Joiner.on(",").join(
                (List)values.stream().map(i -> "?").collect(Collectors.toList())
        );
    }
    private void addAll(List values, List result){
        if (!CollectionUtils.isEmpty(values)){
            result.addAll(values);
        }
    }
}
