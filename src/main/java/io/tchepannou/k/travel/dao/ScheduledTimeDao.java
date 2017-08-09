package io.tchepannou.k.travel.dao;

import io.tchepannou.k.travel.domain.ScheduledTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface ScheduledTimeDao extends CrudRepository<ScheduledTime, Integer>{
    List<ScheduledTime> findByProductId(Integer productId, Pageable pageable);
    ScheduledTime findByProductIdAndDayOfWeekAndDepartureTimeAndArrivalTime(Integer productId, Integer dayOfWeek, Time departureTime, Time arrivalTime);
}
