package io.tchepannou.k.travel.dao;

import io.tchepannou.k.travel.domain.Schedule;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleRowMapper implements RowMapper<Schedule> {
    @Override
    public Schedule mapRow(final ResultSet rs, final int i) throws SQLException {
        Schedule schedule = new Schedule();
        schedule.setProductId(rs.getInt("travel_product_fk"));
        schedule.setCapacity(rs.getInt("capacity"));
        schedule.setAssetId(rs.getInt("travel_asset_fk"));
        schedule.setArrivalDateTime(rs.getTimestamp("arrival_datetime"));
        schedule.setDepartureDateTime(rs.getTimestamp("departure_datetime"));
        schedule.setId(rs.getInt("id"));
        return schedule;
    }
}
