package io.tchepannou.k.travel.service;

import io.tchepannou.k.travel.client.request.CreateScheduleRequest;
import io.tchepannou.k.travel.client.request.SearchScheduleRequest;
import io.tchepannou.k.travel.client.request.UpdateScheduledRequest;
import io.tchepannou.k.travel.client.response.CreateScheduleResponse;
import io.tchepannou.k.travel.client.response.GetScheduleReponse;
import io.tchepannou.k.travel.client.response.ScheduleDto;
import io.tchepannou.k.travel.client.response.SearchScheduleResponse;
import io.tchepannou.k.travel.client.response.UpdateScheduleResponse;
import io.tchepannou.k.travel.dao.AssetDao;
import io.tchepannou.k.travel.dao.ProductDao;
import io.tchepannou.k.travel.dao.ScheduleDao;
import io.tchepannou.k.travel.dao.ScheduleRowMapper;
import io.tchepannou.k.travel.dao.SearchScheduleQueryBuilder;
import io.tchepannou.k.travel.domain.Asset;
import io.tchepannou.k.travel.domain.Product;
import io.tchepannou.k.travel.domain.Schedule;
import io.tchepannou.k.travel.exception.BusinessErrors;
import io.tchepannou.k.travel.exception.InvalidRequestException;
import io.tchepannou.k.travel.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleService {
    @Autowired
    private ScheduleDao scheduleDao;
    
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private AssetDao assetDao;

    @Autowired
    private SearchScheduleQueryBuilder queryBuilder;

    @Autowired
    private DataSource dataSource;


    @Transactional
    public CreateScheduleResponse create(CreateScheduleRequest request){
        final Product product = productDao.findOne(request.getProductId());
        if (product == null){
            throw new InvalidRequestException(BusinessErrors.PRODUCT_NOT_FOUND);    
        }
        
        final Asset asset = assetDao.findOne(request.getAssetId());
        if (asset == null){
            throw new InvalidRequestException(BusinessErrors.ASSET_NOT_FOUND);
        }
        
        final Schedule schedule = new Schedule();
        schedule.setArrivalDateTime(toTimestamp(request.getArrivalDateTime()));
        schedule.setAssetId(request.getAssetId());
        schedule.setCapacity(request.getCapacity());
        schedule.setDepartureDateTime(toTimestamp(request.getDepartureDateTime()));
        schedule.setProductId(request.getProductId());
        scheduleDao.save(schedule);
        
        final CreateScheduleResponse response = new CreateScheduleResponse();
        response.setId(schedule.getId());
        return null;
    }

    @Transactional
    public UpdateScheduleResponse update(Integer id, UpdateScheduledRequest request){
        final Schedule schedule = scheduleDao.findOne(id);
        if (schedule == null){
            throw new InvalidRequestException(BusinessErrors.SCHEDULE_NOT_FOUND);
        }

        schedule.setArrivalDateTime(toTimestamp(request.getArrivalDateTime()));
        schedule.setCapacity(request.getCapacity());
        schedule.setDepartureDateTime(toTimestamp(request.getDepartureDateTime()));
        scheduleDao.save(schedule);

        final CreateScheduleResponse response = new CreateScheduleResponse();
        response.setId(schedule.getId());
        return null;
    }

    public GetScheduleReponse findById (Integer id){
        Schedule schedule = scheduleDao.findOne(id);
        if (schedule == null){
            throw new NotFoundException(BusinessErrors.SCHEDULE_NOT_FOUND);
        }

        final GetScheduleReponse response = new GetScheduleReponse();
        response.setSchedule(toDto(schedule));
        return response;
    }

    public SearchScheduleResponse search(SearchScheduleRequest request){
        final String sql = queryBuilder.toSql(request);
        final Object[] args = queryBuilder.toArgs(request);
        final JdbcTemplate jdbc = new JdbcTemplate(dataSource);

        final List<Schedule> schedules = jdbc.query(sql, args, new ScheduleRowMapper());

        final SearchScheduleResponse response = new SearchScheduleResponse();
        response.setSchedules(
                schedules.stream()
                        .map(schedule -> toDto(schedule))
                        .collect(Collectors.toList()));
        return response;

    }


    private ScheduleDto toDto (final Schedule schedule){
        ScheduleDto dto = new ScheduleDto();
        dto.setArrivalDateTime(schedule.getArrivalDateTime());
        dto.setAssetId(schedule.getAssetId());
        dto.setCapacity(schedule.getCapacity());
        dto.setDepartureDateTime(schedule.getDepartureDateTime());
        dto.setId(schedule.getId());
        dto.setProductId(schedule.getProductId());
        return dto;
    }

    private Timestamp toTimestamp(Date date){
        return date == null ? null : new Timestamp(date.getTime());
    }
}
