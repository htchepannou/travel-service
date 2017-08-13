package io.tchepannou.k.travel.dao;

import io.tchepannou.k.travel.domain.Schedule;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleRowMapper implements RowMapper<Schedule> {
    @Override
    public Schedule mapRow(final ResultSet resultSet, final int i) throws SQLException {
        return null;
    }
}
