package io.tchepannou.k.travel.dao;

import io.tchepannou.k.travel.domain.Price;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceRowMapper implements RowMapper<Price> {
    @Override
    public Price mapRow(final ResultSet rs, final int i) throws SQLException {
        final Price price = new Price();

        price.setId(rs.getInt("id"));
        price.setProductId(rs.getInt("travel_product_fk"));
        price.setPriceTypeId(rs.getInt("price_type_fk"));
        price.setToDateTime(rs.getTimestamp("to_datetime"));
        price.setFromDateTime(rs.getTimestamp("from_datetime"));
        price.setCurrencyCode(rs.getString("currency_code"));
        price.setAmount(rs.getDouble("amount"));

        return price;
    }
}
