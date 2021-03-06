package io.tchepannou.k.travel.dao;

import com.google.common.base.Joiner;
import io.tchepannou.k.travel.client.request.SearchScheduleRequest;
import io.tchepannou.k.travel.util.SqlArguments;
import io.tchepannou.k.travel.util.SqlPredicates;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static io.tchepannou.k.travel.util.DateUtil.toEndOfDay;
import static io.tchepannou.k.travel.util.DateUtil.toStartOfDay;

@Component
public class SearchScheduleQueryBuilder {
    public String toSql(SearchScheduleRequest request){
        final List<String> predicates = new ArrayList<>();
        final Date fromDate = toStartOfDay(request.getDepartureDateTime());
        final Date toDate = toEndOfDay(request.getDepartureDateTime());
        final String assetTypeName = request.getAssetTypeName();

        predicates.add(SqlPredicates.eq("P.origin_city_fk", request.getOriginCityId()));
        predicates.add(SqlPredicates.eq("P.destination_city_fk", request.getOriginCityId()));
        predicates.add(SqlPredicates.eq("P.travel_provider_fk", request.getTravelProviderId()));
        predicates.add(SqlPredicates.between("S.departure_datetime", fromDate, toDate));
        predicates.add(SqlPredicates.eq("UCASE(AT.name)", assetTypeName == null ? null : assetTypeName.toUpperCase()));

        final List<String> where = predicates.stream()
                .filter(p -> p != null)
                .collect(Collectors.toList());


        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT S.* "
                + " FROM T_SCHEDULED_TRANSPORTATION S "
                + " JOIN T_TRAVEL_PRODUCT P ON S.travel_product_fk=P.id"
                + " JOIN T_TRAVEL_ASSET A ON S.travel_asset_fk=A.id"
                + " JOIN T_TRAVEL_ASSET_TYPE AT ON A.travel_asset_type_fk=AT.id"
        );
        if (!where.isEmpty()){
            sql
                    .append(" WHERE ")
                    .append(Joiner.on(" AND ").join(where));
        }
        sql.append(" ORDER BY S.departure_datetime");
        return sql.toString();
    }

    public Object[] toArgs (SearchScheduleRequest request){
        final List args = new ArrayList();
        final Date fromDate = toStartOfDay(request.getDepartureDateTime());
        final Date toDate = toEndOfDay(request.getDepartureDateTime());
        final String assetTypeName = request.getAssetTypeName();

        args.addAll(SqlArguments.eq(request.getOriginCityId()));
        args.addAll(SqlArguments.eq(request.getDestinationCityId()));
        args.addAll(SqlArguments.eq(request.getTravelProviderId()));
        args.addAll(SqlArguments.between(fromDate, toDate));
        args.addAll(SqlArguments.eq(assetTypeName == null ? null : assetTypeName.toUpperCase()));

        return args.toArray();
    }


}
