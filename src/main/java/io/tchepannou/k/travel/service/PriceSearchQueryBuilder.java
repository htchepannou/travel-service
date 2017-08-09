package io.tchepannou.k.travel.service;

import com.google.common.base.Joiner;
import io.tchepannou.k.travel.client.request.SearchPriceRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceSearchQueryBuilder {
    public String toSql(SearchPriceRequest request){
        final List<String> predicates = new ArrayList<>();

        predicates.add(predicate("travel_product_fk", request.getProductIds()));
        predicates.add(predicate("price_type_name", request.getPriceTypeNames()));
        if (request.getDepartureDate() != null) {
            predicates.add("(from_date IS NULL OR from_date<=?) AND (to_date IS NULL OR to_date>=?)");
        }

        final List<String> where = predicates.stream()
                .filter(p -> p != null)
                .collect(Collectors.toList());

        final String sql = "SELECT P.*, T.name AS price_type_name"
                + " FROM T_PRICE P JOIN T_PRICE_TYPE T ON P.t_price_type_fk=T.id";

        return where.isEmpty() ? sql : sql + " WHERE " + Joiner.on(" AND ").join(where);
    }

    public Object[] toArgs(SearchPriceRequest request){
        final List args = new ArrayList();
        addAll(request.getProductIds(), args);
        addAll(request.getProductIds(), args);

        final String departureDate = request.getDepartureDate();
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
