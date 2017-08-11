package io.tchepannou.k.travel.dao;

import com.google.common.base.Joiner;
import io.tchepannou.k.travel.client.request.SearchPriceRequest;
import io.tchepannou.k.travel.util.SqlArguments;
import io.tchepannou.k.travel.util.SqlPredicates;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchPriceQueryBuilder {
    public String toSql(SearchPriceRequest request){
        final List<String> predicates = new ArrayList<>();

        predicates.add(SqlPredicates.in("travel_product_fk", request.getProductIds()));
        predicates.add(SqlPredicates.in("UCASE(T.name)", request.getPriceTypeNames()));
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
        args.addAll(SqlArguments.in(request.getProductIds()));

        args.addAll(SqlArguments.in(ucase(request.getPriceTypeNames())));

        final Date departureDate = request.getDepartureDate();
        if (departureDate != null) {
            args.addAll(Arrays.asList(departureDate, departureDate));
        }

        return args.toArray();
    }

    private List ucase(List<String> values){
        return values.stream().map(n -> n.toUpperCase()).collect(Collectors.toList());
    }
}
