package io.tchepannou.k.travel.dao;

import io.tchepannou.k.travel.domain.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleDao extends CrudRepository<Schedule, Integer>{
}
